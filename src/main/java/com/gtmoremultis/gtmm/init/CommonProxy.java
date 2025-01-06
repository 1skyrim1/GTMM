package com.gtmoremultis.gtmm.init;

import com.gtmoremultis.gtmm.GTMMRegistries;
import com.gtmoremultis.gtmm.data.GTMMBlocks;
import com.gtmoremultis.gtmm.data.GTMMDataGen;
import com.gtmoremultis.gtmm.data.GTMMItems;

public class CommonProxy {
    public static void init() {
        GTMMBlocks.init();
        GTMMDataGen.init();
        GTMMRegistries.REGISTRATE.registerRegistrate();
    }
}
