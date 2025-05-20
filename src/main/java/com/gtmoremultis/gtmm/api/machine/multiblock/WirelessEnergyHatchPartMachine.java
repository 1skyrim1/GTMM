package com.gtmoremultis.gtmm.api.machine.multiblock;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.fancy.ConfiguratorPanel;
import com.gregtechceu.gtceu.api.gui.fancy.IFancyConfigurator;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.multiblock.part.TieredIOPartMachine;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gtmoremultis.gtmm.api.machine.trait.NotifiableWirelessEnergyContainer;
import com.gtmoremultis.gtmm.saveddata.WirelessSubstationSavedData;
import com.lowdragmc.lowdraglib.gui.texture.IGuiTexture;
import com.lowdragmc.lowdraglib.gui.texture.ItemStackTexture;
import com.lowdragmc.lowdraglib.gui.widget.TextFieldWidget;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import lombok.Getter;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;
import java.util.List;

public class WirelessEnergyHatchPartMachine extends TieredIOPartMachine {
    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(WirelessEnergyHatchPartMachine.class, TieredIOPartMachine.MANAGED_FIELD_HOLDER);

    @Persisted
    @DescSynced
    public final NotifiableWirelessEnergyContainer energyContainer;
    @Getter
    protected int amperage;

    private List<IMultiPart> part = new ArrayList<>();

    public WirelessEnergyHatchPartMachine(IMachineBlockEntity holder, int tier, IO io, int amperage, Object... args) {
        super(holder, tier, io);
        this.amperage = amperage;
        this.energyContainer = createEnergyContainer(args);
        this.part.add(this);
        addWirelessEnergy();
    }


    protected NotifiableWirelessEnergyContainer createEnergyContainer(Object... args) {
        NotifiableWirelessEnergyContainer container;
        if (this.io == IO.OUT) {
            container = NotifiableWirelessEnergyContainer.emitterContainer(this, GTValues.V[tier] * 64L * amperage, GTValues.V[tier], amperage);
            container.setCapabilityValidator(s -> s == null || s == this.getFrontFacing());
        } else {
            container = NotifiableWirelessEnergyContainer.receiverContainer(this, GTValues.V[tier] * 16L * amperage, GTValues.V[tier], amperage);
            container.setCapabilityValidator(s -> s == null || s == this.getFrontFacing());
        }
        return container;
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onUnload() {
        super.onUnload();
    }

    @Override
    public boolean shouldOpenUI(Player player, InteractionHand hand, BlockHitResult hit) {
        return true;
    }

    private void removeWirelessEnergy() {
        if (getLevel() instanceof ServerLevel serverLevel) {
            WirelessSubstationSavedData savedData = WirelessSubstationSavedData.getOrCreate(serverLevel.getServer().overworld());
            if (this.io == IO.IN) {
                savedData.removeEnergyOutputs(energyContainer.getFrequency(), this.part);
            } else {
                savedData.removeEnergyInputs(energyContainer.getFrequency(), this.part);
            }
            savedData.removeEnergyInputs(energyContainer.getFrequency(), this.part);
            savedData.removeEnergyOutputs(energyContainer.getFrequency(), this.part);
            savedData.saveDataToCache();
        }
    }

    private void addWirelessEnergy() {
        if (getLevel() instanceof ServerLevel serverLevel) {
            WirelessSubstationSavedData savedData = WirelessSubstationSavedData.getOrCreate(serverLevel.getServer().overworld());
            if (this.io == IO.IN) {
                savedData.addEnergyOutputs(energyContainer.getFrequency(), this.part);
            } else {
                savedData.addEnergyInputs(energyContainer.getFrequency(), this.part);
            }
            savedData.saveDataToCache();
        }
    }

    public void setFrequencyFromString(String str) {
        removeWirelessEnergy();
        energyContainer.setFrequency(Integer.parseInt(str));
        addWirelessEnergy();
    }

    public String getFrequencyString() {
        return Integer.valueOf(energyContainer.getFrequency()).toString();
    }

    @Override
    public void attachConfigurators(ConfiguratorPanel configuratorPanel) {
        super.attachConfigurators(configuratorPanel);
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
                                .setTextResponder(WirelessEnergyHatchPartMachine.this::setFrequencyFromString)
                                .setTextSupplier(WirelessEnergyHatchPartMachine.this::getFrequencyString));
            }
        });
    }

    @Override
    public int tintColor(int index) {
        if (index == 2) {
            return GTValues.VC[getTier()];
        }
        return super.tintColor(index);
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }
}
