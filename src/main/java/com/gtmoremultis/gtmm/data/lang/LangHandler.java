package com.gtmoremultis.gtmm.data.lang;


import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gtmoremultis.gtmm.GTMM;
import com.tterrag.registrate.providers.RegistrateLangProvider;

public class LangHandler {
    public static final LangHandler INSTANCE = new LangHandler();
    private RegistrateLangProvider enLangProvider;

    public static void enInitialize(RegistrateLangProvider provider) {
        INSTANCE.enLangProvider = provider;
        init();
    }

    public static void tsl(String key, String en) {
        INSTANCE.translate(key, en);
    }

    public static void translateMaterial(Material material, String en) {
        try {
            INSTANCE.enLangProvider.add("material.gtceu." + material.getName(), en);
        } catch (NullPointerException e) {
            GTMM.LOGGER.error("Failed to translate material(EN)", e);
        }
    }


    public static void init() {
        MachineLang.init();
        MaterialLang.init();
        MiscLang.init();
    }

    public void translate(String key, String en) {
        if (enLangProvider != null) {
            enLangProvider.add(key, en);
        }
    }
}
