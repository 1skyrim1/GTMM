package com.gtmoremultis.gtmm.api.machine.trait;

import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableEnergyContainer;
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.annotation.RequireRerender;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.Direction;

public class NotifiableWirelessEnergyContainer extends NotifiableEnergyContainer {

    public static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(NotifiableWirelessEnergyContainer.class, NotifiableEnergyContainer.MANAGED_FIELD_HOLDER);

    @Persisted
    @DescSynced
    @RequireRerender
    @Getter
    @Setter
    private int frequency = 0;

    public NotifiableWirelessEnergyContainer(MetaMachine machine, long maxCapacity, long maxInputVoltage, long maxInputAmperage, long maxOutputVoltage, long maxOutputAmperage) {
        super(machine, maxCapacity, maxInputVoltage, maxInputAmperage, maxOutputVoltage, maxOutputAmperage);
    }

    public static NotifiableWirelessEnergyContainer emitterContainer(MetaMachine machine, long maxCapacity, long maxOutputVoltage, long maxOutputAmperage) {
        return new NotifiableWirelessEnergyContainer(machine, maxCapacity, 0L, 0L, maxOutputVoltage, maxOutputAmperage);
    }

    public static NotifiableWirelessEnergyContainer receiverContainer(MetaMachine machine, long maxCapacity, long maxInputVoltage, long maxInputAmperage) {
        return new NotifiableWirelessEnergyContainer(machine, maxCapacity, maxInputVoltage, maxInputAmperage, 0L, 0L);
    }

    @Override
    public void updateTick() {
        super.updateTick();
        if (frequency == 0) return;
    }

    @Override
    public void checkOutputSubscription() {
        // We don't need to interact with wires
    }

    @Override
    public void serverTick() {
        // We don't need to interact with wires
    }

    @Override
    public boolean outputsEnergy(Direction side) {
        return false;
    }

    @Override
    public boolean inputsEnergy(Direction side) {
        return false;
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }
}
