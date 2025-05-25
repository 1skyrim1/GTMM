package com.gtmoremultis.gtmm.common.lang;

import com.tterrag.registrate.providers.RegistrateLangProvider;

public class LangHandler extends com.gregtechceu.gtceu.data.lang.LangHandler {

    public static void init(RegistrateLangProvider provider) {
        // tooltips
        provider.add("gtmm.multiblock.coal.tier", "Casing Tier: %s");
        provider.add("gtmm.multiblock.coal.parallel_level", "Max Parallels: %s");
        provider.add("gtmm.gui.wpss.wireless_configurator.frequency_set", "Current Frequency: %s");
        provider.add("gtmm.gui.wpss.wireless_configurator.title", "Set Frequency");

        // Wireless Hatches
        provider.add("tooltips.wireless_energy_input_hatch", "Wireless Version of Energy Hatch.\nNeeds to have a Wireless Substation on the same frequency to work");
        provider.add("tooltips.wireless_energy_output_hatch", "Wireless Version of Dynamo Hatch.\nOutputs Energy to Wireless Substation on the same frequency");

        // Advanced Terminal
        provider.add("item.gtmm.advanced_terminal.setting.title", "Advanced Terminal Settings");
        provider.add("item.gtmm.advanced_terminal.setting.1", "Coil Tier");
        provider.add("item.gtmm.advanced_terminal.setting.1.tooltip", "Sets the Coil Tier to:");
        provider.add("item.gtmm.advanced_terminal.setting.2", "Repetitions (e.g. assline)");
        provider.add("item.gtmm.advanced_terminal.setting.2.tooltip", "Sets the amount of repetitions for multiblocks like the assline");
        provider.add("item.gtmm.advanced_terminal.setting.3", "Build Hatches?");
        provider.add("item.gtmm.advanced_terminal.setting.3.tooltip", "0: Build Hatches\n1: Don't build Hatches");
        provider.add("item.gtmm.advanced_terminal.setting.4", "Flip Multiblock?");
        provider.add("item.gtmm.advanced_terminal.setting.4.tooltip", "0: No Flip\n1: Flip");

        // Wireless Energy Binding Tool
        provider.add("item.gtmm.wireless_energy_binding_tool.setting.tooltip", "Changes the frequency of block");
        provider.add("item.gtmm.wireless_energy_binding_tool.setting.title", "Frequency");
        provider.add("item.gtmm.wireless_energy_binding_tool.setting.1", "Frequency:");
        provider.add("item.wireless_energy_binding_tool.chat_message.1", "Changed Frequency to %s");
        provider.add("item.wireless_energy_binding_tool.chat_message.2", "Changed Frequency on pss to %s");

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
