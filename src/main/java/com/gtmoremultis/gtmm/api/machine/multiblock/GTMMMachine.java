package com.gtmoremultis.gtmm.api.machine.multiblock;

import com.gregtechceu.gtceu.api.capability.recipe.IRecipeCapabilityHolder;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.pattern.MultiblockState;
import com.gregtechceu.gtceu.api.pattern.util.PatternMatchContext;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gtmoremultis.gtmm.api.block.MachineCasingType;
import com.gtmoremultis.gtmm.api.machine.feature.IGTmmMachine;
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.annotation.RequireRerender;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import lombok.Getter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GTMMMachine extends WorkableElectricMultiblockMachine implements IGTmmMachine {
    private static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(GTMMMachine.class, WorkableElectricMultiblockMachine.MANAGED_FIELD_HOLDER);


    @Getter
    @Persisted
    @DescSynced
    @RequireRerender
    private int tier;
    private MachineCasingType machineCasingType;

    public GTMMMachine(IMachineBlockEntity holder) {super(holder);}

    @Override
    public void onStructureFormed() {
        scheduleRenderUpdate();
        super.onStructureFormed();

        // Retrieve the multiblock state
        MultiblockState multiblockState = getMultiblockState();
        PatternMatchContext matchContext = multiblockState.getMatchContext();

        // Set type Variables
        this.machineCasingType = machineCasingType;
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

    @Nullable
    public static GTRecipe GTMMRecipe(MetaMachine machine, @Nonnull GTRecipe recipe) {
        if (machine instanceof GTMMMachine gtmmmachine) {
            if (RecipeHelper.getRecipeEUtTier(recipe) > gtmmmachine.getMachineCasingTier() + 1) {
                return null;
            }

            var maxParallel = (int) (1 + Math.pow(4, gtmmmachine.getMachineCasingTier()));
//            var Parallellimit = Math.min(maxParallel, (int) (gtmmmachine.getMaxVoltage()));
            var Parallels = ParallelLogic.getParallelAmount(gtmmmachine, recipe, maxParallel);
            var euT = RecipeHelper.getInputEUt(recipe);
            var Parallellimit = (int) (gtmmmachine.getMaxVoltage() / euT);

            while (maxParallel > 0) {
                // copy and apply parallel. Will affect all recipe contents and duration
                var copied = recipe.copy(ContentModifier.multiplier(Parallellimit));

                // If machine has enough ingredients, return copied recipe
                if (copied.matchRecipe(gtmmmachine) == GTRecipe.ActionResult.SUCCESS) {
                    copied.duration = copied.duration / Parallellimit;
                    return copied;
                }

                Parallellimit /= 2;
            }
        }
        return null;
    }

    // NBT Save ?
    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }
}
