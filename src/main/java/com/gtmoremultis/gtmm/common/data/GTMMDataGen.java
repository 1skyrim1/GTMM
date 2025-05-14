package com.gtmoremultis.gtmm.common.data;

import com.gtmoremultis.gtmm.common.lang.LangHandler;
import com.tterrag.registrate.providers.ProviderType;

import static com.gtmoremultis.gtmm.GTMMRegistries.REGISTRATE;

public class GTMMDataGen {
    public static void init() {
        REGISTRATE.addDataGenerator(ProviderType.LANG, LangHandler::init);
    }
}
