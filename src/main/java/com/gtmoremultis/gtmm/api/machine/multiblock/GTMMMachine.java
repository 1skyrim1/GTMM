package com.gtmoremultis.gtmm.api.machine.multiblock;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.pattern.MultiblockState;
import com.gregtechceu.gtceu.api.pattern.util.PatternMatchContext;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gtmoremultis.gtmm.api.block.MachineCasingType;
import com.gtmoremultis.gtmm.api.machine.feature.IGTmmMachine;
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.annotation.RequireRerender;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import com.gtmoremultis.gtmm.config.ConfigHandler;

public class GTMMMachine extends WorkableElectricMultiblockMachine implements IGTmmMachine {
    private static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(GTMMMachine.class, WorkableElectricMultiblockMachine.MANAGED_FIELD_HOLDER);


    @Getter
    @Persisted
    @DescSynced
    @RequireRerender
    private int tier;
    private MachineCasingType machineCasingType;

    public GTMMMachine(IMachineBlockEntity holder) {
        super(holder);
    }

    @Override
    public void onStructureFormed() {
        scheduleRenderUpdate();
        super.onStructureFormed();

        // Retrieve the multiblock state
        MultiblockState multiblockState = getMultiblockState();
        PatternMatchContext matchContext = multiblockState.getMatchContext();

        MachineCasingType machineCasingType = matchContext.get("MachineCasing") instanceof MachineCasingType ? (MachineCasingType) matchContext.get("MachineCasing") : null;

        // Set type Variables
        this.machineCasingType = machineCasingType;
        this.tier = getMachineCasingTier();
    }


    @Override
    public void scheduleRenderUpdate() {scheduleRenderUpdate(this);}

    public int getMachineCasingTier() {
        if (this.machineCasingType != null) {
            return this.machineCasingType.getTier();
        }
        return 0;
    }


    // Recipe Logic

    public static ModifierFunction GTMMRecipeModifier(@NotNull MetaMachine machine, @NotNull GTRecipe recipe) {
        if (machine instanceof GTMMMachine gtmmmachine) {
            if (RecipeHelper.getRecipeEUtTier(recipe) > gtmmmachine.getMachineCasingTier() + 1) {
                return null;
            }

            var maxParallel = (int) (1 + Math.pow(ConfigHandler.INSTANCE.machine.parallelMultiplier, gtmmmachine.getMachineCasingTier() - ConfigHandler.INSTANCE.machine.casingParallel));
            var Parallellimit = Math.min(maxParallel, (int) (gtmmmachine.getMaxVoltage()));
            var euT = RecipeHelper.getInputEUt(recipe);

            int parallels = ParallelLogic.getParallelAmount(gtmmmachine, recipe, Parallellimit);

            return ModifierFunction.builder()
                    .inputModifier(ContentModifier.multiplier(parallels))
                    .outputModifier(ContentModifier.multiplier(parallels))
                    .eutModifier(ContentModifier.multiplier(parallels))
                    .parallels(parallels)
                    .build();

        }
        throw new RuntimeException("Not the right machine");
    }

    // NBT Save ?
    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }
}
