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

        // tooltips
        provider.add("gtmm.multiblock.coal.tier", "Casing Tier: %s");
        provider.add("gtmm.multiblock.coal.parallel_level", "Max Parallels: %s");

        // errors
        provider.add("gtmm.multiblock.pattern.error.machine_casing", "All Machine Casings must be the same");
    }
}
