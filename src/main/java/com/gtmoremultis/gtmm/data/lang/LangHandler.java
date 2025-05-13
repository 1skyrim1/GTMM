package com.gtmoremultis.gtmm.data.lang;

import com.gtmoremultis.gtmm.data.GTMMMaterials;
import com.tterrag.registrate.providers.RegistrateLangProvider;

public class LangHandler extends com.gregtechceu.gtceu.data.lang.LangHandler {

    public static void init(RegistrateLangProvider provider) {
        // tooltips
        provider.add("gtmm.multiblock.coal.tier", "Casing Tier: %s");
        provider.add("gtmm.multiblock.coal.parallel_level", "Max Parallels: %s");

        // Advanced Terminal
        provider.add("item.gtmm.advanced_terminal.setting.title", "Advanced Terminal Setting");
        provider.add("item.gtmm.advanced_terminal.setting.1", "Coil Tier");
        provider.add("item.gtmm.advanced_terminal.setting.1.tooltip", "Sets the Coil Tier to:");
        provider.add("item.gtmm.advanced_terminal.setting.2", "Repetitions (e.g. assline)");
        provider.add("item.gtmm.advanced_terminal.setting.2.tooltip", "Sets the amount of repetitions for multiblocks like the assline");
        provider.add("item.gtmm.advanced_terminal.setting.3", "Build Hatches?");
        provider.add("item.gtmm.advanced_terminal.setting.3.tooltip", "0: Build Hatches\n1: Don't build Hatches");

        // errors
        provider.add("gtmm.multiblock.pattern.error.machine_casing", "All Machine Casings must be the same");

        // Configs
        provider.add("config.gtmm.option.machine", "Machine Configs");
        provider.add("config.screen.gtmm", "GT More Multis Config");
        provider.add("config.gtmm.option.parallelMultiplier", "Base for Parallel Logic (Base^(Casing Tier)) (Default: 4)");
        provider.add("config.gtmm.option.casingParallel", "IDK, this is (Base^(Casing Tier - this)) (Default: 0)");
        provider.add("config.gtmm.option.enable_low_tier_wireless", "Enable Wireless Energy Hatches for Low Tiers? (Default: false)");
    }
}
