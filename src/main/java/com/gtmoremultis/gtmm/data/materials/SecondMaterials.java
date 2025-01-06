package com.gtmoremultis.gtmm.data.materials;

import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gtmoremultis.gtmm.data.GTMMMaterials.*;

public class SecondMaterials {
    public static void init() {
        BlackMatter = Builder("black_matter")
                .dust().ingot().fluid()
                .components(Lead, 3, Manganese, 5, Carbon, 12)
                .color(0x000000).iconSet(DULL)
                .appendFlags(EXT_METAL, GENERATE_FRAME)
                .buildAndRegister();
    }
}
