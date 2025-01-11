package com.gtmoremultis.gtmm.api.machine.multiblock;

import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;

public class APartAbility extends PartAbility {
    public static final PartAbility INPUT_WIRELESS_ENERGY = new PartAbility("input_wireless_energy");
    public static final PartAbility OUTPUT_WIRELESS_ENERGY = new PartAbility("output_wireless_energy");

    public APartAbility(String name) {
        super(name);
    }
}
