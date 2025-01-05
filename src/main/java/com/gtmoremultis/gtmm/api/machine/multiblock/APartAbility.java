package com.gtmoremultis.gtmm.api.machine.multiblock;

import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class APartAbility extends PartAbility {
    public static final PartAbility WIRELESS_ENERGY_HATCH = new PartAbility("wireless_energy_hatch");

    public APartAbility(String name) {
        super(name);
    }

    public static <T> T getOrDefault(@Nullable T value, Supplier<T> defaultSupplier) {
        if (value == null) return defaultSupplier.get();
        return value;
    }
}
