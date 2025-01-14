package com.gtmoremultis.gtmm.data;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.DustProperty;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.FluidProperty;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.OreProperty;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gtceu.api.fluids.FluidBuilder;
import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys;
import com.gtmoremultis.gtmm.data.materials.AdjustGTMaterials;
import com.gtmoremultis.gtmm.data.materials.FirstMaterials;
import com.gtmoremultis.gtmm.data.materials.SecondMaterials;

import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet.SHINY;

public class GTMMMaterials {
    public static Material Infinity;
    public static Material BlackMatter;

    public static void init() {
        AdjustGTMaterials.init();
        FirstMaterials.init();
        SecondMaterials.init();
    }

    public static void addDust(Material material) {
        material.setProperty(PropertyKey.DUST, new DustProperty());
    }

    public static void addFluid(Material material) {
        material.setProperty(PropertyKey.FLUID, new FluidProperty());
        material.getProperty(PropertyKey.FLUID).getStorage().enqueueRegistration(FluidStorageKeys.LIQUID, new FluidBuilder());
    }

    public static void addGas(Material material) {
        material.setProperty(PropertyKey.FLUID, new FluidProperty());
        material.getProperty(PropertyKey.FLUID).getStorage().enqueueRegistration(FluidStorageKeys.GAS, new FluidBuilder());
    }

    public static void addOre(Material material) {
        material.setProperty(PropertyKey.ORE, new OreProperty());
    }

    public static Material.Builder Builder(String id) {
        return new Material.Builder(GTCEu.id(id));
    }

    public static class MaterialIcons {
        public static MaterialIconSet InfinityIcon;

        public MaterialIcons() {
            InfinityIcon = new MaterialIconSet("infinity", SHINY);
        }
    }
}
