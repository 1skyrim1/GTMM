package com.gtmoremultis.gtmm.data;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.registry.registrate.MachineBuilder;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.gtmoremultis.gtmm.api.machine.multiblock.WirelessEnergyHatchPartMachine;
import com.gtmoremultis.gtmm.config.ConfigHandler;
import net.minecraft.network.chat.Component;

import java.util.Locale;
import java.util.function.BiFunction;

import static com.gregtechceu.gtceu.common.data.machines.GTMachineUtils.*;
import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gtmoremultis.gtmm.GTMMRegistries.REGISTRATE;

public class GTMMMachineUtils {

    public static MachineDefinition[] registerWirelessHatch(IO io, int amperage, PartAbility ability) {
        String name = io == IO.IN ? "input" : "output";
        return registerTieredMachines(amperage + "a_wireless_energy_" + name + "_hatch",
                (holder, tier) -> new WirelessEnergyHatchPartMachine(holder, io, tier, amperage),
                (tier, builder) -> builder
                        .langValue(VNF[tier] + "§r " + FormattingUtil.formatNumbers(amperage) + " §eA§r Wireless " + FormattingUtil.toEnglishName(name) + " Hatch")
                        .rotationState(RotationState.ALL)
                        .tooltips(Component.translatable("gtmm.machine.wireless_energy_hatch.both.tooltip"),
                                Component.translatable("gtmm.machine.wireless_energy_hatch." + name + ".tooltip"),
                                Component.translatable("gtceu.universal.tooltip.voltage_" + (io == IO.IN ? "in" : "out"),
                                        FormattingUtil.formatNumbers(V[tier]), VNF[tier]),
                                Component.translatable("gtceu.universal.tooltip.amperage_in", amperage),
                                Component.translatable("gtceu.universal.tooltip.energy_storage_capacity",
                                        FormattingUtil.formatNumbers(WirelessEnergyHatchPartMachine.getHatchEnergyCapacity(tier, amperage))),
                                Component.translatable("gtceu.universal.disabled"))
                        .abilities(ability)
                        .overlayTieredHullRenderer("wireless_energy_hatch." + name)
                        .register(),
                ConfigHandler.INSTANCE.machine.enable_low_tier_wireless ? ELECTRIC_TIERS : HIGH_TIERS);
    }

    public static MachineDefinition[] registerHighTierWirelessHatch(IO io, int amperage, PartAbility ability) {
        String name = io == IO.IN ? "energy" : "dynamo";
        return registerTieredMachines(amperage + "a_wireless_energy_" + name + "_hatch",
                (holder, tier) -> new WirelessEnergyHatchPartMachine(holder, io, tier, amperage),
                (tier, builder) -> builder
                        .langValue(VNF[tier] + "§r " + FormattingUtil.formatNumbers(amperage) + "§eA§r Wireless " + FormattingUtil.toEnglishName(name) + " Hatch")
                        .rotationState(RotationState.ALL)
                        .tooltips(Component.translatable("gtmm.machine.wireless_energy_hatch.both.tooltip"),
                                Component.translatable("gtmm.machine.wireless_energy_hatch." + name + ".tooltip"),
                                Component.translatable("gtceu.universal.tooltip.voltage_" + (io == IO.IN ? "in" : "out"),
                                        FormattingUtil.formatNumbers(V[tier]), VNF[tier]),
                                Component.translatable("gtceu.universal.tooltip.amperage_in", amperage),
                                Component.translatable("gtceu.universal.tooltip.energy_storage_capacity",
                                        FormattingUtil.formatNumbers(WirelessEnergyHatchPartMachine.getHatchEnergyCapacity(tier, amperage))),
                                Component.translatable("gtceu.universal.disabled"))
                        .abilities(ability)
                        .overlayTieredHullRenderer("wireless_energy_hatch")
                        .register(),
                OpV);
    }

    public static MachineDefinition[] registerTieredMachines(String name,
                                                             BiFunction<IMachineBlockEntity, Integer, MetaMachine> factory,
                                                             BiFunction<Integer, MachineBuilder<MachineDefinition>, MachineDefinition> builder,
                                                             int... tiers) {
        MachineDefinition[] definitions = new MachineDefinition[GTValues.TIER_COUNT];
        for (int tier: tiers) {
            var register = REGISTRATE
                    .machine(GTValues.VN[tier].toLowerCase(Locale.ROOT) + "_" + name,
                            holder -> factory.apply(holder, tier))
                    .tier(tier);
            definitions[tier] = builder.apply(tier, register);
        }
        return definitions;
    }
}
