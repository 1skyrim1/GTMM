package com.gtmoremultis.gtmm.common.data;

import static com.gtmoremultis.gtmm.GTMMRegistries.REGISTRATE;

@SuppressWarnings("unused")
public class GTMMBlocks {
    // To add Blocks

    static {
        REGISTRATE.creativeModeTab(() -> GTMMCreativeModeTabs.MAIN_TAB);
    }

    public static void init() {
    }
}
