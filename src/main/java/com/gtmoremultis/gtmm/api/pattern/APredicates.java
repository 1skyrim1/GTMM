package com.gtmoremultis.gtmm.api.pattern;

import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.pattern.TraceabilityPredicate;
import com.gregtechceu.gtceu.api.pattern.error.PatternStringError;
import com.gtmoremultis.gtmm.block.MachineCasingBlock;
import com.lowdragmc.lowdraglib.utils.BlockInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;

import java.util.Arrays;
import java.util.stream.Stream;

import static com.gregtechceu.gtceu.api.GTValues.*;

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

    public static TraceabilityPredicate ability(PartAbility ability) {
        int[] tiers = Stream.of(ULV, LV, MV, HV, EV, IV, LuV, ZPM, UV, UHV, UEV, UIV, UXV, OpV, MAX).mapToInt(i -> i).toArray();
        return Predicates.blocks((tiers.length == 0 ? ability.getAllBlocks() : ability.getBlocks(tiers)).toArray(Block[]::new));
    }
}
