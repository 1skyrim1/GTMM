package com.gtmoremultis.gtmm.api.pattern;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.machine.multiblock.IBatteryData;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.pattern.TraceabilityPredicate;
import com.gregtechceu.gtceu.api.pattern.error.PatternStringError;
import com.gregtechceu.gtceu.common.block.BatteryBlock;
import com.gregtechceu.gtceu.common.machine.multiblock.electric.PowerSubstationMachine;
import com.gtmoremultis.gtmm.common.block.MachineCasingBlock;
import com.gtmoremultis.gtmm.common.machine.multiblock.WPowerSubstationMachine;
import com.lowdragmc.lowdraglib.utils.BlockInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.common.machine.multiblock.electric.PowerSubstationMachine.PMC_BATTERY_HEADER;

@SuppressWarnings("unused")
public class APredicates {
    public static TraceabilityPredicate machineCasing() {
        return new TraceabilityPredicate(blockWorldState -> {
            var blockState = blockWorldState.getBlockState();
            for (var entry : MachineCasingBlock.MachineCasing.values()) {
                if (blockState.is(entry.getMachineCasing().get())) {
                    var stats = entry.machineCasingType();
                    Object currentMachineCasing = blockWorldState.getMatchContext().getOrPut("MachineCasing", stats);
                    if (!currentMachineCasing.equals(stats)) {
                        blockWorldState.setError(new PatternStringError("gtmm.multiblock.pattern.error.machine_casing"));
                        return false;
                    }
                    return true;
                }
            }
            return false;
        }, () -> Arrays.stream(MachineCasingBlock.MachineCasing.values()).map(machineCasing -> BlockInfo.fromBlockState(machineCasing.getMachineCasing().get().defaultBlockState())).toArray(BlockInfo[]::new))
                .addTooltips(Component.translatable("gtmm.multiblock.pattern.error.machine_casing"));
    }

    public static TraceabilityPredicate WirelessPowerSubstationBatteries() {
        return new TraceabilityPredicate(blockWorldState -> {
            BlockState state = blockWorldState.getBlockState();
            for (Map.Entry<IBatteryData, Supplier<BatteryBlock>> entry : GTCEuAPI.PSS_BATTERIES.entrySet()) {
                if (state.is(entry.getValue().get())) {
                    IBatteryData battery = entry.getKey();
                    // Allow unfilled batteries in the structure, but do not add them to match context.
                    // This lets you use empty batteries as "filler slots" for convenience if desired.
                    if (battery.getTier() != -1 && battery.getCapacity() > 0) {
                        String key = PMC_BATTERY_HEADER + battery.getBatteryName();
                        WPowerSubstationMachine.WBatteryMatchWrapper wrapper = blockWorldState.getMatchContext().get(key);
                        if (wrapper == null) wrapper = new WPowerSubstationMachine.WBatteryMatchWrapper(battery);
                        blockWorldState.getMatchContext().set(key, wrapper.increment());
                    }
                    return true;
                }
            }
            return false;
        }, () -> GTCEuAPI.PSS_BATTERIES.entrySet().stream()
                .sorted(Comparator.comparingInt(entry -> entry.getKey().getTier()))
                .map(entry -> new BlockInfo(entry.getValue().get().defaultBlockState(), null))
                .toArray(BlockInfo[]::new))
                .addTooltips(Component.translatable("gtceu.multiblock.pattern.error.batteries"));
    }

    public static TraceabilityPredicate ability(PartAbility ability) {
        int[] tiers = Stream.of(ULV, LV, MV, HV, EV, IV, LuV, ZPM, UV, UHV, UEV, UIV, UXV, OpV, MAX).mapToInt(i -> i).toArray();
        return Predicates.blocks((tiers.length == 0 ? ability.getAllBlocks() : ability.getBlocks(tiers)).toArray(Block[]::new));
    }
}
