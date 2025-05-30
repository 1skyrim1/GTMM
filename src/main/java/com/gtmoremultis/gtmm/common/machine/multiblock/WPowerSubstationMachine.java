package com.gtmoremultis.gtmm.common.machine.multiblock;

import com.gregtechceu.gtceu.api.capability.IEnergyContainer;
import com.gregtechceu.gtceu.api.capability.IEnergyInfoProvider;
import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.fancy.*;
import com.gregtechceu.gtceu.api.machine.ConditionalSubscriptionHandler;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.IFancyUIMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IDisplayUIMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMaintenanceMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.multiblock.IBatteryData;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.MachineTrait;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.misc.EnergyContainerList;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.machine.multiblock.electric.PowerSubstationMachine;
import com.gregtechceu.gtceu.config.ConfigHolder;
import com.gregtechceu.gtceu.utils.FormattingUtil;

import com.gtmoremultis.gtmm.saveddata.WPowerSubstationSavedData;
import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.gui.texture.IGuiTexture;
import com.lowdragmc.lowdraglib.gui.texture.ItemStackTexture;
import com.lowdragmc.lowdraglib.gui.widget.*;
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.annotation.RequireRerender;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;

import lombok.Setter;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

import com.google.common.annotations.VisibleForTesting;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class WPowerSubstationMachine extends WorkableMultiblockMachine
        implements IEnergyInfoProvider, IFancyUIMachine, IDisplayUIMachine {

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            com.gtmoremultis.gtmm.common.machine.multiblock.WPowerSubstationMachine.class, WorkableMultiblockMachine.MANAGED_FIELD_HOLDER);

    // Structure Constants
    public static final int MAX_BATTERY_LAYERS = 18;
    public static final int MIN_CASINGS = 14;

    // Passive Drain Constants
    // 1% capacity per 24 hours
    public static final long PASSIVE_DRAIN_DIVISOR = 20 * 60 * 60 * 24 * 100;
    // no more than 100kEU/t per storage block
    public static final long PASSIVE_DRAIN_MAX_PER_STORAGE = 100_000L;

    // Match Context Headers
    public static final String PMC_BATTERY_HEADER = "PSSBattery_";

    private static final BigInteger BIG_INTEGER_MAX_LONG = BigInteger.valueOf(Long.MAX_VALUE);

    private IMaintenanceMachine maintenance;

    private WPowerSubstationMachine.WPowerStationEnergyBank energyBank;
    private EnergyContainerList inputHatches;
    private EnergyContainerList outputHatches;
    // energy hatches for wireless
    private EnergyContainerList wirelessInputHatches;
    private EnergyContainerList wirelessOutputHatches;

    private long passiveDrain;

    // Stats tracked for UI display
    private long netInLastSec;
    private long averageInLastSec;
    private long netOutLastSec;
    private long averageOutLastSec;

    // wireless energy frequency
    @Persisted
    @DescSynced
    @RequireRerender
    @Getter
    @Setter
    private int frequency = 0;

    protected ConditionalSubscriptionHandler tickSubscription;

    public WPowerSubstationMachine(IMachineBlockEntity holder) {
        super(holder);
        this.tickSubscription = new ConditionalSubscriptionHandler(this, this::transferEnergyTick, this::isFormed);
        this.energyBank = new WPowerSubstationMachine.WPowerStationEnergyBank(this, List.of());
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        List<IEnergyContainer> inputs = new ArrayList<>();
        List<IEnergyContainer> outputs = new ArrayList<>();
        Map<Long, IO> ioMap = getMultiblockState().getMatchContext().getOrCreate("ioMap", Long2ObjectMaps::emptyMap);
        for (IMultiPart part : getParts()) {
            IO io = ioMap.getOrDefault(part.self().getPos().asLong(), IO.BOTH);
            if (io == IO.NONE) continue;
            if (part instanceof IMaintenanceMachine maintenanceMachine) {
                this.maintenance = maintenanceMachine;
            }
            for (var handler : part.getRecipeHandlers()) {
                var handlerIO = handler.getHandlerIO();
                // If IO not compatible
                if (io != IO.BOTH && handlerIO != IO.BOTH && io != handlerIO) continue;
                if (handler.getCapability() == EURecipeCapability.CAP &&
                        handler instanceof IEnergyContainer container) {
                    if (handlerIO == IO.IN) {
                        inputs.add(container);
                    } else if (handlerIO == IO.OUT) {
                        outputs.add(container);
                    }
                    traitSubscriptions.add(handler.addChangedListener(tickSubscription::updateSubscription));
                }
            }
        }
        this.inputHatches = new EnergyContainerList(inputs);
        this.outputHatches = new EnergyContainerList(outputs);

        List<IBatteryData> batteries = new ArrayList<>();
        for (Map.Entry<String, Object> battery : getMultiblockState().getMatchContext().entrySet()) {
            if (battery.getKey().startsWith(PMC_BATTERY_HEADER) &&
                    battery.getValue() instanceof WPowerSubstationMachine.WBatteryMatchWrapper wrapper) {
                for (int i = 0; i < wrapper.amount; i++) {
                    batteries.add(wrapper.partType);
                }
            }
        }
        if (batteries.isEmpty()) {
            // only empty batteries found in the structure
            onStructureInvalid();
            return;
        }
        if (this.energyBank == null) {
            this.energyBank = new WPowerSubstationMachine.WPowerStationEnergyBank(this, batteries);
        } else {
            this.energyBank = energyBank.rebuild(batteries);
        }
        this.passiveDrain = this.energyBank.getPassiveDrainPerTick();

        // add wireless energy hatches on same frequency to machine
        if (getLevel() instanceof ServerLevel serverLevel) {
            WPowerSubstationSavedData savedData = WPowerSubstationSavedData.getOrCreate(serverLevel.getServer().overworld());
            wirelessInputHatches = savedData.getWirelessEnergyInputs(getFrequency());
            wirelessOutputHatches = savedData.getWirelessEnergyOutputs(getFrequency());
            //System.out.println("wgtmm: loaded wireless energy hatches to wpss on frequency" + frequency);
        }
    }

    @Override
    public void onStructureInvalid() {
        // don't null out energyBank since it holds the stored energy, which
        // we need to hold on to across rebuilds to not void all energy if a
        // multiblock part or block other than the controller is broken.
        inputHatches = null;
        outputHatches = null;
        passiveDrain = 0;
        netInLastSec = 0;
        averageInLastSec = 0;
        netOutLastSec = 0;
        averageOutLastSec = 0;
        wirelessInputHatches = null;
        wirelessOutputHatches = null;
        super.onStructureInvalid();
    }

    protected void transferEnergyTick() {
        if (!getLevel().isClientSide) {
            if (getOffsetTimer() % 20 == 0) {
                // active here is just used for rendering
                getRecipeLogic()
                        .setStatus(energyBank.hasEnergy() ? RecipeLogic.Status.WORKING : RecipeLogic.Status.IDLE);
                averageInLastSec = netInLastSec / 20;
                averageOutLastSec = netOutLastSec / 20;
                netInLastSec = 0;
                netOutLastSec = 0;
            }

            if (getLevel() instanceof ServerLevel serverLevel && getOffsetTimer() % 100 == 0) {
                // update wireless hatches every 5 seconds
                WPowerSubstationSavedData savedData = WPowerSubstationSavedData.getOrCreate(serverLevel.getServer().overworld());
                wirelessInputHatches = savedData.getWirelessEnergyInputs(getFrequency());
                wirelessOutputHatches = savedData.getWirelessEnergyOutputs(getFrequency());
                //System.out.println("wgtmm: updated wireless energy hatches to wpss on frequency" + frequency);
            }

            if (isWorkingEnabled() && isFormed()) {
                // Bank from Energy Input Hatches
                long energyBanked = energyBank.fill(inputHatches.getEnergyStored());
                inputHatches.changeEnergy(-energyBanked);
                netInLastSec += energyBanked;

                // Bank from Wireless Energy Input Hatches
                long wEnergyBanked = energyBank.fill(wirelessInputHatches.getEnergyStored());
                wirelessInputHatches.changeEnergy(-wEnergyBanked);
                netInLastSec += wEnergyBanked;

                // Passive drain
                long energyPassiveDrained = energyBank.drain(getPassiveDrain());
                netOutLastSec += energyPassiveDrained;

                // Debank to Dynamo Hatches
                long energyDebanked = energyBank
                        .drain(outputHatches.getEnergyCapacity() - outputHatches.getEnergyStored());
                outputHatches.changeEnergy(energyDebanked);
                netOutLastSec += energyDebanked;

                // Debank to Wireless Energy Hatches
                long wEnergyDebanked = energyBank
                        .drain(wirelessOutputHatches.getEnergyCapacity() - wirelessOutputHatches.getEnergyStored());
                wirelessOutputHatches.changeEnergy(wEnergyDebanked);
                netOutLastSec += wEnergyDebanked;
            }
        }
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        IDisplayUIMachine.super.addDisplayText(textList);
        if (isFormed()) {
            if (!isWorkingEnabled()) {
                textList.add(Component.translatable("gtceu.multiblock.work_paused"));

            } else if (isActive()) {
                textList.add(Component.translatable("gtceu.multiblock.running"));
                int currentProgress = (int) (recipeLogic.getProgressPercent() * 100);
                double maxInSec = (float) recipeLogic.getDuration() / 20.0f;
                double currentInSec = (float) recipeLogic.getProgress() / 20.0f;
                textList.add(
                        Component.translatable("gtceu.multiblock.progress", String.format("%.2f", (float) currentInSec),
                                String.format("%.2f", (float) maxInSec), currentProgress));
            } else {
                textList.add(Component.translatable("gtceu.multiblock.idling"));
            }

            if (recipeLogic.isWaiting()) {
                textList.add(Component.translatable("gtceu.multiblock.waiting")
                        .setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
            }

            if (energyBank != null) {
                BigInteger energyStored = energyBank.getStored();
                BigInteger energyCapacity = energyBank.getCapacity();

                var STYLE_GOLD = Style.EMPTY.withColor(ChatFormatting.GOLD);
                var STYLE_DARK_RED = Style.EMPTY.withColor(ChatFormatting.DARK_RED);
                var STYLE_GREEN = Style.EMPTY.withColor(ChatFormatting.GREEN);
                var STYLE_RED = Style.EMPTY.withColor(ChatFormatting.RED);

                var storedComponent = Component.literal(FormattingUtil.formatNumbers(energyStored));
                textList.add(Component.translatable("gtceu.multiblock.power_substation.stored",
                        storedComponent.setStyle(STYLE_GOLD)));

                var capacityComponent = Component.literal(FormattingUtil.formatNumbers(energyCapacity));
                textList.add(Component.translatable("gtceu.multiblock.power_substation.capacity",
                        capacityComponent.setStyle(STYLE_GOLD)));

                var passiveDrainComponent = Component.literal(FormattingUtil.formatNumbers(getPassiveDrain()));
                textList.add(Component.translatable("gtceu.multiblock.power_substation.passive_drain",
                        passiveDrainComponent.setStyle(STYLE_DARK_RED)));

                var avgInComponent = Component.literal(FormattingUtil.formatNumbers(averageInLastSec));
                textList.add(Component
                        .translatable("gtceu.multiblock.power_substation.average_in",
                                avgInComponent.setStyle(STYLE_GREEN))
                        .withStyle(Style.EMPTY.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                Component.translatable("gtceu.multiblock.power_substation.average_in_hover")))));

                var avgOutComponent = Component.literal(FormattingUtil.formatNumbers(Math.abs(averageOutLastSec)));
                textList.add(Component
                        .translatable("gtceu.multiblock.power_substation.average_out",
                                avgOutComponent.setStyle(STYLE_RED))
                        .withStyle(Style.EMPTY.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                Component.translatable("gtceu.multiblock.power_substation.average_out_hover")))));

                if (averageInLastSec > averageOutLastSec) {
                    BigInteger timeToFillSeconds = energyCapacity.subtract(energyStored)
                            .divide(BigInteger.valueOf((averageInLastSec - averageOutLastSec) * 20));
                    textList.add(Component.translatable("gtceu.multiblock.power_substation.time_to_fill",
                            getTimeToFillDrainText(timeToFillSeconds).setStyle(STYLE_GREEN)));
                } else if (averageInLastSec < averageOutLastSec) {
                    BigInteger timeToDrainSeconds = energyStored
                            .divide(BigInteger.valueOf((averageOutLastSec - averageInLastSec) * 20));
                    textList.add(Component.translatable("gtceu.multiblock.power_substation.time_to_drain",
                            getTimeToFillDrainText(timeToDrainSeconds).setStyle(STYLE_RED)));
                }
            }
        }
        getDefinition().getAdditionalDisplay().accept(this, textList);
    }

    private static MutableComponent getTimeToFillDrainText(BigInteger timeToFillSeconds) {
        if (timeToFillSeconds.compareTo(BIG_INTEGER_MAX_LONG) > 0) {
            // too large to represent in a java Duration
            timeToFillSeconds = BIG_INTEGER_MAX_LONG;
        }

        Duration duration = Duration.ofSeconds(timeToFillSeconds.longValue());
        String key;
        long fillTime;
        if (duration.getSeconds() <= 180) {
            fillTime = duration.getSeconds();
            key = "gtceu.multiblock.power_substation.time_seconds";
        } else if (duration.toMinutes() <= 180) {
            fillTime = duration.toMinutes();
            key = "gtceu.multiblock.power_substation.time_minutes";
        } else if (duration.toHours() <= 72) {
            fillTime = duration.toHours();
            key = "gtceu.multiblock.power_substation.time_hours";
        } else if (duration.toDays() <= 730) { // 2 years
            fillTime = duration.toDays();
            key = "gtceu.multiblock.power_substation.time_days";
        } else if (duration.toDays() / 365 < 1_000_000) {
            fillTime = duration.toDays() / 365;
            key = "gtceu.multiblock.power_substation.time_years";
        } else {
            return Component.translatable("gtceu.multiblock.power_substation.time_forever");
        }

        return Component.translatable(key, FormattingUtil.formatNumbers(fillTime));
    }

    public long getPassiveDrain() {
        if (ConfigHolder.INSTANCE.machines.enableMaintenance) {
            if (maintenance == null) {
                for (IMultiPart part : getParts()) {
                    if (part instanceof IMaintenanceMachine maintenanceMachine) {
                        this.maintenance = maintenanceMachine;
                        break;
                    }
                }
            }
            int multiplier = 1 + maintenance.getNumMaintenanceProblems();
            double modifier = maintenance.getDurationMultiplier();
            return (long) (passiveDrain * multiplier * modifier);
        }
        return passiveDrain;
    }

    public String getStored() {
        if (energyBank == null) {
            return "0";
        }
        return FormattingUtil.formatNumbers(energyBank.getStored());
    }

    public String getCapacity() {
        if (energyBank == null) {
            return "0";
        }
        return FormattingUtil.formatNumbers(energyBank.getCapacity());
    }

    @Override
    public EnergyInfo getEnergyInfo() {
        return new EnergyInfo(energyBank.getCapacity(), energyBank.getStored());
    }

    @Override
    public boolean supportsBigIntEnergyValues() {
        return true;
    }

    @Override
    public @NotNull ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    @Override
    public Widget createUIWidget() {
        var group = new WidgetGroup(0, 0, 182 + 8, 117 + 8);
        group.addWidget(new DraggableScrollableWidgetGroup(4, 4, 182, 117).setBackground(getScreenTexture())
                .addWidget(new LabelWidget(4, 5, self().getBlockState().getBlock().getDescriptionId()))
                .addWidget(new ComponentPanelWidget(4, 17, this::addDisplayText)
                        .setMaxWidthLimit(150)
                        .clickHandler(this::handleDisplayClick)));
        group.setBackground(GuiTextures.BACKGROUND_INVERSE);
        return group;
    }

    @Override
    public ModularUI createUI(Player entityPlayer) {
        return new ModularUI(198, 208, this, entityPlayer).widget(new FancyMachineUIWidget(this, 198, 208));
    }

    public void setFrequencyFromString(String str) {
        setFrequency(Integer.parseInt(str));

        if (getLevel() instanceof ServerLevel serverLevel) {
            WPowerSubstationSavedData savedData = WPowerSubstationSavedData.getOrCreate(serverLevel.getServer().overworld());
            wirelessInputHatches = savedData.getWirelessEnergyInputs(getFrequency());
            wirelessOutputHatches = savedData.getWirelessEnergyOutputs(getFrequency());
            //System.out.println("wgtmm: reloaded wireless energy hatches to wpss on new frequency" + frequency);
        }
    }

    public String getFrequencyString() {
        return Integer.valueOf(getFrequency()).toString();
    }

    @Override
    public void attachConfigurators(ConfiguratorPanel configuratorPanel) {
        configuratorPanel.attachConfigurators(new IFancyConfigurator() {

            @Override
            public Component getTitle() {
                return Component.translatable("gtmm.gui.wpss.wireless_configurator.title");
            }

            @Override
            public IGuiTexture getIcon() {
                return new ItemStackTexture(GTItems.SENSOR_UV.asItem());
            }

            @Override
            public Widget createConfigurator() {
                return new WidgetGroup(0, 0, 130, 25)
                        .addWidget(new TextFieldWidget().setNumbersOnly(0, Integer.MAX_VALUE)
                                .setTextResponder(WPowerSubstationMachine.this::setFrequencyFromString)
                                .setTextSupplier(WPowerSubstationMachine.this::getFrequencyString));
            }
        });
    }

    @Override
    public List<IFancyUIProvider> getSubTabs() {
        return getParts().stream().filter(IFancyUIProvider.class::isInstance).map(IFancyUIProvider.class::cast)
                .toList();
    }

    @Override
    public void attachTooltips(TooltipsPanel tooltipsPanel) {
        for (IMultiPart part : getParts()) {
            part.attachFancyTooltipsToController(this, tooltipsPanel);
        }
    }

    @Override
    public void saveCustomPersistedData(@NotNull CompoundTag tag, boolean forDrop) {
        super.saveCustomPersistedData(tag, forDrop);
        CompoundTag bankTag = energyBank.writeToNBT(new CompoundTag());
        tag.put("wEnergyBank", bankTag);
    }

    @Override
    public void loadCustomPersistedData(@NotNull CompoundTag tag) {
        super.loadCustomPersistedData(tag);
        energyBank.readFromNBT(tag.getCompound("wEnergyBank"));
    }

    public static class WPowerStationEnergyBank extends MachineTrait {

        protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
                com.gtmoremultis.gtmm.common.machine.multiblock.WPowerSubstationMachine.WPowerStationEnergyBank.class);
        private static final String NBT_SIZE = "Size";
        private static final String NBT_STORED = "Stored";
        private static final String NBT_MAX = "Max";

        // @Persisted(key = NBT_STORED)
        private long[] storage;
        // @Persisted(key = NBT_MAX)
        private long[] maximums;
        @Getter
        private BigInteger capacity;
        private int index;

        public WPowerStationEnergyBank(MetaMachine machine, List<IBatteryData> batteries) {
            super(machine);
            storage = new long[batteries.size()];
            maximums = new long[batteries.size()];
            for (int i = 0; i < batteries.size(); i++) {
                maximums[i] = batteries.get(i).getCapacity();
            }
            capacity = summarize(maximums);
        }

        public void readFromNBT(CompoundTag storageTag) {
            int size = storageTag.getInt(NBT_SIZE);
            storage = new long[size];
            maximums = new long[size];
            for (int i = 0; i < size; i++) {
                CompoundTag subtag = storageTag.getCompound(String.valueOf(i));
                if (subtag.contains(NBT_STORED)) {
                    storage[i] = subtag.getLong(NBT_STORED);
                }
                maximums[i] = subtag.getLong(NBT_MAX);
            }
            capacity = summarize(maximums);
        }

        public CompoundTag writeToNBT(CompoundTag compound) {
            compound.putInt(NBT_SIZE, storage.length);
            for (int i = 0; i < storage.length; i++) {
                CompoundTag subtag = new CompoundTag();
                if (storage[i] > 0) {
                    subtag.putLong(NBT_STORED, storage[i]);
                }
                subtag.putLong(NBT_MAX, maximums[i]);
                compound.put(String.valueOf(i), subtag);
            }
            return compound;
        }

        /**
         * Rebuild the power storage with a new list of batteries.
         * Will use existing stored power and try to map it onto new batteries.
         * If there was more power before the rebuild operation, it will be lost.
         */
        public com.gtmoremultis.gtmm.common.machine.multiblock.WPowerSubstationMachine.WPowerStationEnergyBank rebuild(@NotNull List<IBatteryData> batteries) {
            if (batteries.isEmpty()) {
                throw new IllegalArgumentException("Cannot rebuild Power Substation power bank with no batteries!");
            }
            com.gtmoremultis.gtmm.common.machine.multiblock.WPowerSubstationMachine.WPowerStationEnergyBank newStorage = new com.gtmoremultis.gtmm.common.machine.multiblock.WPowerSubstationMachine.WPowerStationEnergyBank(this.machine, batteries);
            for (long stored : storage) {
                newStorage.fill(stored);
            }
            return newStorage;
        }

        /** @return Amount filled into storage */
        public long fill(long amount) {
            if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative!");

            // ensure index
            if (index != storage.length - 1 && storage[index] == maximums[index]) {
                index++;
            }

            long maxFill = Math.min(maximums[index] - storage[index], amount);

            // storage is completely full
            if (maxFill == 0 && index == storage.length - 1) {
                return 0;
            }

            // fill this "battery" as much as possible
            storage[index] += maxFill;
            amount -= maxFill;

            // try to fill other "batteries" if necessary
            if (amount > 0 && index != storage.length - 1) {
                return maxFill + fill(amount);
            }

            // other fill not necessary, either because the storage is now completely full,
            // or we were able to consume all the energy in this "battery"
            return maxFill;
        }

        /** @return Amount drained from storage */
        public long drain(long amount) {
            if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative!");

            // ensure index
            if (index != 0 && storage[index] == 0) {
                index--;
            }

            long maxDrain = Math.min(storage[index], amount);

            // storage is completely empty
            if (maxDrain == 0 && index == 0) {
                return 0;
            }

            // drain this "battery" as much as possible
            storage[index] -= maxDrain;
            amount -= maxDrain;

            // try to drain other "batteries" if necessary
            if (amount > 0 && index != 0) {
                index--;
                return maxDrain + drain(amount);
            }

            // other drain not necessary, either because the storage is now completely empty,
            // or we were able to drain all the energy from this "battery"
            return maxDrain;
        }

        public BigInteger getStored() {
            return summarize(storage);
        }

        public boolean hasEnergy() {
            for (long l : storage) {
                if (l > 0) return true;
            }
            return false;
        }

        private static BigInteger summarize(long[] values) {
            BigInteger retVal = BigInteger.ZERO;
            long currentSum = 0;
            for (long value : values) {
                if (currentSum != 0 && value > Long.MAX_VALUE - currentSum) {
                    // will overflow if added
                    retVal = retVal.add(BigInteger.valueOf(currentSum));
                    currentSum = 0;
                }
                currentSum += value;
            }
            if (currentSum != 0) {
                retVal = retVal.add(BigInteger.valueOf(currentSum));
            }
            return retVal;
        }

        @VisibleForTesting
        public long getPassiveDrainPerTick() {
            long[] maximumsExcl = new long[maximums.length];
            int index = 0;
            int numExcl = 0;
            for (long maximum : maximums) {
                if (maximum / PASSIVE_DRAIN_DIVISOR >= PASSIVE_DRAIN_MAX_PER_STORAGE) {
                    numExcl++;
                } else {
                    maximumsExcl[index++] = maximum;
                }
            }
            maximumsExcl = Arrays.copyOf(maximumsExcl, index);
            BigInteger capacityExcl = summarize(maximumsExcl);

            return capacityExcl.divide(BigInteger.valueOf(PASSIVE_DRAIN_DIVISOR))
                    .add(BigInteger.valueOf(PASSIVE_DRAIN_MAX_PER_STORAGE * numExcl))
                    .longValue();
        }

        @Override
        public ManagedFieldHolder getFieldHolder() {
            return MANAGED_FIELD_HOLDER;
        }
    }

    @Getter
    public static class WBatteryMatchWrapper {

        private final IBatteryData partType;
        private int amount;

        public WBatteryMatchWrapper(IBatteryData partType) {
            this.partType = partType;
        }

        public com.gtmoremultis.gtmm.common.machine.multiblock.WPowerSubstationMachine.WBatteryMatchWrapper increment() {
            amount++;
            return this;
        }
    }
}