package com.gtmoremultis.gtmm.data.lang;

import com.gtmoremultis.gtmm.data.GTMMMaterials;
import com.tterrag.registrate.providers.RegistrateLangProvider;

public class LangHandler extends com.gregtechceu.gtceu.data.lang.LangHandler {

    public static void init(RegistrateLangProvider provider) {
        // materials
        replace(provider, GTMMMaterials.Infinity.getUnlocalizedName(), "Infinity");
        replace(provider, GTMMMaterials.BlackMatter.getUnlocalizedName(), "Black Matter");

        // blocks
        replace(provider, "block.gtmm.iridium_machine_casing", "Iridium Machine Casing");
    }
}
