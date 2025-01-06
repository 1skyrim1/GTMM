package com.gtmoremultis.gtmm.data;

import com.gregtechceu.gtceu.api.data.chemical.Element;
import com.gregtechceu.gtceu.common.data.GTElements;

@SuppressWarnings("unused")
public class GTMMElement {
    public static final Element IF2 = GTElements.createAndRegister(99999, 999999, -1, null, "Infinity", "If*", false);

    public static void init() {
    }
}
