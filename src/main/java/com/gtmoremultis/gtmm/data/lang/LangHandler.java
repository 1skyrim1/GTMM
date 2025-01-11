package com.gtmoremultis.gtmm.data.lang;

import com.gtmoremultis.gtmm.data.GTMMMaterials;
import com.tterrag.registrate.providers.RegistrateLangProvider;

public class LangHandler extends com.gregtechceu.gtceu.data.lang.LangHandler {

    public static void init(RegistrateLangProvider provider) {
        // materials
        replace(provider, GTMMMaterials.Infinity.getUnlocalizedName(), "Infinity");
        replace(provider, GTMMMaterials.BlackMatter.getUnlocalizedName(), "Black Matter");

        // blocks
        // replace(provider, "block.gtmm.iridium_machine_casing", "Iridium Machine Casing");
        // replace(provider, "block.gtmm.naquadah_machine_casing", "Naquadah Machine Casing");

        // Wireless Energy Hatches
        provider.add("gtmm.machine.wireless_energy_hatch.input.tooltip", "Does not connect to wires. This block withdraws EU from the network.");
        provider.add("gtmm.machine.wireless_energy_hatch.output.tooltip", "Does not connect to wires. This block accepts EU into the network.");
        provider.add("gtmm.machine.wireless_energy_hatch.both.tooltip", "Stores energy globally in a network, up to 2^(2^31) EU.");

        // tooltips
        provider.add("gtmm.multiblock.coal.tier", "Casing Tier: %s");
        provider.add("gtmm.multiblock.coal.parallel_level", "Max Parallels: %s");

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
