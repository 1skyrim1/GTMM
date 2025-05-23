package com.gtmoremultis.gtmm.mixin;

import com.gregtechceu.gtceu.common.machine.multiblock.part.AutoMaintenanceHatchPartMachine;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = AutoMaintenanceHatchPartMachine.class, remap = false)
public class AutoMaintenanceHatchPartMachineMixin {

    public boolean canShared() {
        return true;
    }
}
