package com.gtmoremultis.gtmm;

import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.addon.events.KJSRecipeKeyEvent;
import com.gregtechceu.gtceu.api.addon.events.MaterialCasingCollectionEvent;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import com.gtmoremultis.gtmm.data.GTMMCasingBlocks;
import com.gtmoremultis.gtmm.data.GTMMElement;
import com.gtmoremultis.gtmm.data.GTMMRecipes;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

@GTAddon
public class GTMMAddon implements IGTAddon {
    @Override
    public GTRegistrate getRegistrate() {
        return GTMMRegistries.REGISTRATE;
    }

    @Override
    public void initializeAddon() {
        GTMM.LOGGER.info("GTMM Loaded!");
    }

    @Override
    public String addonModId() {
        return GTMM.MOD_ID;
    }

    @Override
    public void registerElements() {
        GTMMElement.init();
    }

    @Override
    public void addRecipes(Consumer<FinishedRecipe> provider) {
        GTMMRecipes.init(provider);
    }

//    @Override
//    public void registerRecipeConditions() {
//    }

    @Override
    public void registerWorldgenLayers() {
        IGTAddon.super.registerWorldgenLayers();
    }

    @Override
    public void registerVeinGenerators() {
        IGTAddon.super.registerVeinGenerators();
    }

    @Override
    public void registerIndicatorGenerators() {
        IGTAddon.super.registerIndicatorGenerators();
    }

    @Override
    public void collectMaterialCasings(MaterialCasingCollectionEvent event) {
        GTMMCasingBlocks.init();
    }

    @Override
    public void registerRecipeKeys(KJSRecipeKeyEvent event) {
        IGTAddon.super.registerRecipeKeys(event);
    }

    @Override
    public boolean requiresHighTier() {
        return IGTAddon.super.requiresHighTier();
    }
}
