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
        provider.add("item.gtmm.advanced_terminal.setting.1.tooltip", "1.tooltip");     // Hier fehlt was!!
        provider.add("item.gtmm.advanced_terminal.setting.2", "Repeat Count???");       // Hier auch
        provider.add("item.gtmm.advanced_terminal.setting.2.tooltip", "2.tooltip");     // Hier auch
        provider.add("item.gtmm.advanced_terminal.setting.3", "Build Hatches? (0: Yes|1: No");
        provider.add("item.gtmm.advanced_terminal.setting.3.tooltip", "3.tooltip");     // Hier aber auch noch

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
