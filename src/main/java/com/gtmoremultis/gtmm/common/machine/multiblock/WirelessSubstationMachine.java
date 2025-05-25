package com.gtmoremultis.gtmm.common.machine.multiblock;

import com.gregtechceu.gtceu.api.gui.fancy.ConfiguratorPanel;
import com.gregtechceu.gtceu.api.gui.fancy.IFancyConfigurator;
import com.gregtechceu.gtceu.api.machine.ConditionalSubscriptionHandler;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine;
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
import com.lowdragmc.lowdraglib.syncdata.annotation.RequireRerender;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;

import java.util.List;

public class WirelessSubstationMachine extends PowerSubstationMachine {

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            WirelessSubstationMachine.class, WorkableMultiblockMachine.MANAGED_FIELD_HOLDER);

    @Persisted
    @DescSynced
    @RequireRerender
    @Getter
    @Setter
    private int frequency = 0;

    protected ConditionalSubscriptionHandler converterSubscription;

    public WirelessSubstationMachine(IMachineBlockEntity holder) {
        super(holder);
        this.converterSubscription = new ConditionalSubscriptionHandler(this, this::convertEnergyTick, this::isFormed);
    }

    public void convertEnergyTick() {
        if (isWorkingEnabled() && isFormed()) {
            if (getLevel() instanceof ServerLevel serverLevel) {
                WirelessSubstationSavedData savedData = WirelessSubstationSavedData.getOrCreate(serverLevel.getServer().overworld());

                //EnergyContainerList powerInput = savedData.getWirelessEnergyInputs(getFrequency()); // power inputs -> energy to network
                EnergyContainerList powerOutput = savedData.getWirelessEnergyOutputs(getFrequency()); // power outputs -> energy from network

                // add energy to station
                //long canDrain = powerInput.getEnergyStored();
                //long actuallyDrained = energyBank.fill(canDrain);
                //powerInput.removeEnergy(actuallyDrained);

                // check if we can drain energy from the substation
                //long energyNeeded = powerOutput.getEnergyCanBeInserted();
                //long totalDrained = this.energyBank.drain(energyNeeded);
                //powerOutput.changeEnergy(totalDrained);
            }
        }
        converterSubscription.updateSubscription();
    }


    public void setFrequencyFromString(String str) {
        setFrequency(Integer.parseInt(str));
    }

    public String getFrequencyString() {
        return Integer.valueOf(getFrequency()).toString();
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
}
