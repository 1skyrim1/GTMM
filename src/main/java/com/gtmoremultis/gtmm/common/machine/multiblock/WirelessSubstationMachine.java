package com.gtmoremultis.gtmm.common.machine.multiblock;

import com.gregtechceu.gtceu.api.capability.IControllable;
import com.gregtechceu.gtceu.api.gui.fancy.ConfiguratorPanel;
import com.gregtechceu.gtceu.api.gui.fancy.IFancyConfigurator;
import com.gregtechceu.gtceu.api.machine.ConditionalSubscriptionHandler;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.misc.EnergyContainerList;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.machine.multiblock.electric.PowerSubstationMachine;
import com.gtmoremultis.gtmm.saveddata.WirelessSubstationSavedData;
import com.lowdragmc.lowdraglib.gui.texture.IGuiTexture;
import com.lowdragmc.lowdraglib.gui.texture.ItemStackTexture;
import com.lowdragmc.lowdraglib.gui.widget.*;
import com.lowdragmc.lowdraglib.gui.widget.TextFieldWidget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import lombok.Getter;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;

import java.util.List;

public class WirelessSubstationMachine extends PowerSubstationMachine implements IControllable {

    private final PowerStationEnergyBank energyBank;

    @Persisted
    @DescSynced
    @Getter
    private int frequency;

    protected ConditionalSubscriptionHandler converterSubscription;

    public WirelessSubstationMachine(IMachineBlockEntity holder) {
        super(holder);
        this.tickSubscription = new ConditionalSubscriptionHandler(this, this::transferEnergyTick, this::isFormed);
        this.energyBank = new PowerStationEnergyBank(this, List.of());
        this.converterSubscription = new ConditionalSubscriptionHandler(this, this::convertEnergyTick, this::isFormed);
        this.frequency = 0;
    }

    public void convertEnergyTick() {
        /*
        if (frequency == 0) {
            getRecipeLogic().setStatus(RecipeLogic.Status.SUSPEND);
            return;
        }
        if (isWorkingEnabled()) {
            getRecipeLogic().setStatus(isFormed() ? RecipeLogic.Status.WORKING : RecipeLogic.Status.SUSPEND);
        }
        */
        if (isWorkingEnabled()) {
            // Distribute energy to all possible locations
            if (getLevel() instanceof ServerLevel serverLevel) {
                WirelessSubstationSavedData savedData = WirelessSubstationSavedData.getOrCreate(serverLevel.getServer().overworld());

                EnergyContainerList powerInput = savedData.getWirelessEnergyInputs(frequency); // power inputs -> energy to network
                EnergyContainerList powerOutput = savedData.getWirelessEnergyOutputs(frequency); // power outputs -> energy from network

                System.out.println(powerInput);
                System.out.println(powerOutput);

                // add energy to station
                long canDrain = powerInput.getEnergyStored();
                long actuallyDrained = this.energyBank.fill(canDrain);
                powerInput.removeEnergy(actuallyDrained);

                // check if we can drain energy from the substation
                long energyNeeded = powerOutput.getEnergyCanBeInserted();
                long totalDrained = this.energyBank.drain(energyNeeded);
                powerOutput.changeEnergy(totalDrained);
            }
        }
        converterSubscription.updateSubscription();
    }


    public void setFrequencyFromString(String str) {
        frequency = Integer.parseInt(str);
    }

    public String getFrequencyString() {
        return Integer.valueOf(frequency).toString();
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
                                .setTextResponder(WirelessSubstationMachine.this::setFrequencyFromString)
                                .setTextSupplier(WirelessSubstationMachine.this::getFrequencyString));
            }
        });
    }

    /*
    @Override
    public @NotNull Widget createUIWidget() {
        super.createUIWidget();
        var group = new WidgetGroup(0, 0, 182 + 8, 117 + 8);
        group.addWidget(new DraggableScrollableWidgetGroup(4, 4, 182, 117).setBackground(getScreenTexture())
                .addWidget(new LabelWidget(4, 5, self().getBlockState().getBlock().getDescriptionId()))
                .addWidget(new ComponentPanelWidget(4, 17, this::addDisplayText)
                        .setMaxWidthLimit(150)
                        .clickHandler(this::handleDisplayClick)));
        group.setBackground(GuiTextures.BACKGROUND_INVERSE);
        return group;
    }
    */
}
