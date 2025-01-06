package com.gtmoremultis.gtmm.data;

import com.gtmoremultis.gtmm.data.lang.LangHandler;
import com.tterrag.registrate.providers.ProviderType;

import static com.gtmoremultis.gtmm.GTMMRegistries.REGISTRATE;

public class GTMMDataGen {
    public static void init() {
        REGISTRATE.addDataGenerator(ProviderType.LANG, LangHandler::enInitialize);
    }
}
