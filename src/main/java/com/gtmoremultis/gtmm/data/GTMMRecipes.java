package com.gtmoremultis.gtmm.data;

import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

@SuppressWarnings("unused")
public class GTMMRecipes {
    public static void init(Consumer<FinishedRecipe> provider) {

    }

    public static int dur(double seconds) {
        return (int) (seconds * 20d);
    }
}