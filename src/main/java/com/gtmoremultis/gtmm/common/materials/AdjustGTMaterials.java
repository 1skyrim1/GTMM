package com.gtmoremultis.gtmm.common.materials;

import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags;

import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

public class AdjustGTMaterials {
    public static void init() {
        Iridium.addFlags(MaterialFlags.GENERATE_DENSE);
        IronMagnetic.addFlags(MaterialFlags.GENERATE_LONG_ROD);
    }
}
