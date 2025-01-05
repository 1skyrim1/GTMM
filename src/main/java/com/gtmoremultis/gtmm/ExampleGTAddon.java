package com.gtmoremultis.gtmm;

import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

@SuppressWarnings("unused")
@GTAddon
public class ExampleGTAddon implements IGTAddon {
    @Override
    public GTRegistrate getRegistrate() {
        return GTMM.GTMM_REGISTRATE;
    }

    @Override
    public void initializeAddon() {

    }

    @Override
    public String addonModId() {
        return GTMM.MOD_ID;
    }

    @Override
    public void registerTagPrefixes() {
        //CustomTagPrefixes.init();
    }

    @Override
    public void addRecipes(Consumer<FinishedRecipe> provider) {
        //CustomRecipes.init(provider);
    }
}
