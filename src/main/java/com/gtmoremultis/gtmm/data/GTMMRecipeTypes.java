package com.gtmoremultis.gtmm.data;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.data.GTSoundEntries;
import com.lowdragmc.lowdraglib.gui.texture.ProgressTexture;

public class GTMMRecipeTypes {
    public static final GTRecipeType CoAL_RECIPES = GTRecipeTypes.register("coal", GTRecipeTypes.MULTIBLOCK)
            .setMaxIOSize(12, 1, 8, 0)
            .setEUIO(IO.IN)
            .prepareBuilder(gtRecipeBuilder -> gtRecipeBuilder.EUt(GTValues.VA[GTValues.LV]))
            .setSlotOverlay(false, false, GuiTextures.BOX_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setMaxTooltips(4)
            .setSound(GTSoundEntries.ASSEMBLER);
}