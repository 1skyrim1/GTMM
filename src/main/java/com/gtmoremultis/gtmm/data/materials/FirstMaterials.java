package com.gtmoremultis.gtmm.data.materials;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.BlastProperty;
import com.gtmoremultis.gtmm.data.GTMMElement;

import static com.gtmoremultis.gtmm.data.GTMMMaterials.*;

public class FirstMaterials {
    public static void init() {
        Infinity = Builder("infinity")
                .ingot().fluid().ore().dust()
                .color(0xFFFFFF).iconSet(MaterialIcons.InfinityIcon)
                .element(GTMMElement.IF2)
                .blastTemp(9900, BlastProperty.GasTier.HIGH, GTValues.VA[GTValues.ZPM], 21825)
                .buildAndRegister();
    }
}
