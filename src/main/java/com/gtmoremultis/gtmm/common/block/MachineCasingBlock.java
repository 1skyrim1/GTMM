package com.gtmoremultis.gtmm.common.block;

import com.gtmoremultis.gtmm.api.block.ITier;
import com.gtmoremultis.gtmm.api.block.MachineCasingType;
import com.tterrag.registrate.util.entry.BlockEntry;
import lombok.Getter;
import net.minecraft.world.level.block.Block;

import static com.gregtechceu.gtceu.common.data.GTBlocks.*;
import static com.gtmoremultis.gtmm.common.block.BlockTier.*;

public class MachineCasingBlock extends Block {
    public MachineCasingBlock(Properties properties) {
        super(properties);
    }

    public enum MachineCasing implements MachineCasingType {
        LV(TIER0, MACHINE_CASING_LV, "§7LV§r"),
        MV(TIER1, MACHINE_CASING_MV, "§bMV§r"),
        HV(TIER2, MACHINE_CASING_HV, "§6HV§r"),
        EV(TIER3, MACHINE_CASING_EV, "§5EV§r"),
        IV(TIER4, MACHINE_CASING_IV, "§1IV§r"),
        LuV(TIER5, MACHINE_CASING_LuV, "§dLuV§r"),
        ZPM(TIER6, MACHINE_CASING_ZPM, "§cZPM§r"),
        UV(TIER7, MACHINE_CASING_UV, "§3UV§r"),
        UHV(TIER8, MACHINE_CASING_UHV, "§eUHV§r"),
        UEV(TIER9, MACHINE_CASING_UEV, "§aUEV§r"),
        UIV(TIER10, MACHINE_CASING_UIV, "§2UIV§r"),
        UXV(TIER11, MACHINE_CASING_UXV, "§9UMV§r"),
        OpV(TIER12, MACHINE_CASING_OpV, "§4OpV§r"),
        MAX(TIER13, MACHINE_CASING_MAX, "§4MAX§r");

        private final ITier tier;
        private final BlockEntry<Block> machineCasing;
        @Getter
        private final String energyHatchLevel;

        MachineCasing(ITier tier, BlockEntry<Block> machineCasing, String energyHatchLevel) {
            this.tier = tier;
            this.machineCasing = machineCasing;
            this.energyHatchLevel = energyHatchLevel;
        }

        public MachineCasingType machineCasingType() {
            return this;
        }

        @Override
        public int getTier() {
            return tier.tier();
        }

        @Override
        public BlockEntry<Block> getMachineCasing() {
            return machineCasing;
        }

        public BlockEntry<Block> getMachineCasing(int tier) {
            return MachineCasing.values()[tier].getMachineCasing();
        }
    }
}
