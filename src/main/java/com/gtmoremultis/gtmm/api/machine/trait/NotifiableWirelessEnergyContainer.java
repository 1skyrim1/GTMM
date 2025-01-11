package com.gtmoremultis.gtmm.api.machine.trait;

import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableEnergyContainer;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableRecipeHandlerTrait;
import com.gtmoremultis.gtmm.api.capability.IWirelessEnergyContainer;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;

public class NotifiableWirelessEnergyContainer extends NotifiableEnergyContainer implements IWirelessEnergyContainer {

    public static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(NotifiableEnergyContainer.class, NotifiableRecipeHandlerTrait.MANAGED_FIELD_HOLDER);

    public NotifiableWirelessEnergyContainer(MetaMachine machine, long maxCapacity, long maxInputVoltage, long maxInputAmperage,
                                             long maxOutputVoltage, long maxOutputAmperage) {
        super(machine, maxCapacity, maxInputVoltage, maxInputAmperage, maxOutputVoltage, maxOutputAmperage);
    }

    public static NotifiableWirelessEnergyContainer emitterContainer(MetaMachine machine, long maxCapacity,
                                                                     long maxOutputVoltage, long maxOutputAmperage) {
        return new NotifiableWirelessEnergyContainer(machine, maxCapacity, 0L, 0L, maxOutputVoltage, maxOutputAmperage);
    }

    public static NotifiableWirelessEnergyContainer receiverContainer(MetaMachine machine, long maxCapacity,
                                                                      long maxInputVoltage, long maxInputAmperage) {
        return new NotifiableWirelessEnergyContainer(machine, maxCapacity, maxInputVoltage, maxInputAmperage, 0L, 0L);
    }

    @Override
    public void serverTick() {
        amps = 0;
        if (getMachine().getLevel().isClientSide)
            return;
        if (getEnergyStored() < getOutputVoltage() || getOutputVoltage() <= 0 ||getOutputAmperage() <= 0)
            return;
        long outputVoltage = getOutputVoltage();
        long outputAmperes = Math.min(getEnergyStored() / outputVoltage, getOutputAmperage());
        if (outputAmperes == 0) return;
        long amperesUsed = 0;

        // send EU to network


    }
}
