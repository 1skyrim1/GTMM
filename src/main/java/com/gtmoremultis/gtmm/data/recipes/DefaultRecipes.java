package com.gtmoremultis.gtmm.data.recipes;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class DefaultRecipes {
    public static void init(Consumer<FinishedRecipe> provider) {
        Misc.init(provider);
        SelfRecipes.init(provider);
    }

    public static class Misc {
        public static void init(Consumer<FinishedRecipe> provider) {
            // TODO: Add recipes here
        }

        public static void removeRecipes(Consumer<ResourceLocation> consumer) {
            // Recipes to be removed here
        }
    }
}
