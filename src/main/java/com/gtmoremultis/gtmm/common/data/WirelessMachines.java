package com.gtmoremultis.gtmm.common.data;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.registry.registrate.MachineBuilder;
import com.gtmoremultis.gtmm.api.machine.multiblock.WirelessEnergyHatchPartMachine;
import net.minecraft.network.chat.Component;

import java.util.Locale;
import java.util.function.BiFunction;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.common.data.machines.GTMachineUtils.ELECTRIC_TIERS;
import static com.gregtechceu.gtceu.common.data.machines.GTMachineUtils.MULTI_HATCH_TIERS;
import static com.gtmoremultis.gtmm.GTMMRegistries.REGISTRATE;

public class WirelessMachines {
    public static final MachineDefinition[] WIRELESS_ENERGY_INPUT_HATCH = registerSimpleWirelessEnergyHatches(2, ELECTRIC_TIERS);
    public static final MachineDefinition[] WIRELESS_ENERGY_INPUT_HATCH_4A = registerWirelessEnergyHatches(4, MULTI_HATCH_TIERS);
    public static final MachineDefinition[] WIRELESS_ENERGY_INPUT_HATCH_16A = registerWirelessEnergyHatches(16, MULTI_HATCH_TIERS);
    public static final MachineDefinition[] WIRELESS_ENERGY_INPUT_HATCH_64A = registerWirelessEnergyHatches(64, MULTI_HATCH_TIERS);
    public static final MachineDefinition[] WIRELESS_ENERGY_INPUT_HATCH_256A = registerWirelessEnergyHatches(256, MULTI_HATCH_TIERS);
    public static final MachineDefinition[] WIRELESS_ENERGY_INPUT_HATCH_1024A = registerWirelessEnergyHatches(1024, MULTI_HATCH_TIERS);
    public static final MachineDefinition[] WIRELESS_ENERGY_INPUT_HATCH_4096A = registerWirelessEnergyHatches(4096, MULTI_HATCH_TIERS);
    public static final MachineDefinition[] WIRELESS_ENERGY_INPUT_HATCH_16384A = registerWirelessEnergyHatches(16384, MULTI_HATCH_TIERS);
    public static final MachineDefinition[] WIRELESS_ENERGY_INPUT_HATCH_65536A = registerWirelessEnergyHatches(65536, MULTI_HATCH_TIERS);
    public static final MachineDefinition[] WIRELESS_ENERGY_INPUT_HATCH_262144A = registerWirelessEnergyHatches(262144, MULTI_HATCH_TIERS);
    public static final MachineDefinition[] WIRELESS_ENERGY_INPUT_HATCH_104857A = registerWirelessEnergyHatches(1048576, MULTI_HATCH_TIERS);

    public static final MachineDefinition WIRELESS_DYNAMO_HATCH = REGISTRATE.machine("wireless_dynamo_hatch", holder -> new WirelessEnergyHatchPartMachine(holder, MAX, IO.OUT, 1048576))
            .langValue("Wireless Dynamo Hatch")
            .rotationState(RotationState.ALL)
            .overlayTieredHullRenderer("wireless_dynamo_hatch")
            .abilities(PartAbility.OUTPUT_ENERGY)
            .tier(MAX)
            .register();

    public static void init() {}

    public static MachineDefinition[] registerSimpleWirelessEnergyHatches(int amperage, int... tiers) {
        return registerTieredWirelessEnergyMachines("wireless_energy_input_hatch_%sa".formatted(amperage),
                (holder, tier) -> new WirelessEnergyHatchPartMachine(holder, tier, IO.IN, amperage),
                (tier, builder) -> builder
                        .langValue("%s Wireless Energy Hatch".formatted(VNF[tier]))
                        .rotationState(RotationState.ALL)
                        .abilities(PartAbility.INPUT_ENERGY)
                        .overlayTieredHullRenderer("wireless_energy_hatch")
                        .tooltips(Component.translatable("tooltips.wireless_energy_input_hatch"))
                        .register(),
                tiers);
    }

    public static MachineDefinition[] registerWirelessEnergyHatches(int amperage, int... tiers) {
        return registerTieredWirelessEnergyMachines("wireless_energy_input_hatch_%sa".formatted(amperage),
                (holder, tier) -> new WirelessEnergyHatchPartMachine(holder, tier, IO.IN, amperage),
                (tier, builder) -> builder
                        .langValue("%s §r%sA Wireless Energy Hatch".formatted(VNF[tier], amperage))
                        .rotationState(RotationState.ALL)
                        .abilities(PartAbility.INPUT_ENERGY)
                        .overlayTieredHullRenderer("wireless_energy_hatch")
                        .tooltips(Component.translatable("tooltips.wireless_energy_input_hatch"))
                        .register(),
                tiers);
    }

    public static MachineDefinition[] registerTieredWirelessEnergyMachines(String name,
                                                             BiFunction<IMachineBlockEntity, Integer, MetaMachine> factory,
                                                             BiFunction<Integer, MachineBuilder<MachineDefinition>, MachineDefinition> builder,
                                                             int... tiers) {
        MachineDefinition[] definitions = new MachineDefinition[TIER_COUNT];
        for (int tier : tiers) {
            var register = REGISTRATE
                    .machine(VN[tier].toLowerCase(Locale.ROOT) + "_" + name,
                            holder -> factory.apply(holder, tier))
                    .tier(tier);
            definitions[tier] = builder.apply(tier, register);
        }
        return definitions;
    }
}
