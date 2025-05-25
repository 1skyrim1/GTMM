package com.gtmoremultis.gtmm.common.recipes;

import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.common.data.machines.GTMultiMachines;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;
import com.gtmoremultis.gtmm.GTMM;
import com.gtmoremultis.gtmm.common.data.GTMMCasingBlocks;
import com.gtmoremultis.gtmm.common.data.GTMMMachines;
import com.gtmoremultis.gtmm.common.data.GTMMRecipeTypes;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.*;
import static com.gtmoremultis.gtmm.common.data.GTMMRecipes.dur;
import static com.gtmoremultis.gtmm.common.data.GTMMItems.ADVANCED_TERMINAL;

public class SelfRecipes {
    public static void init(Consumer<FinishedRecipe> provider) {
        machineRecipes(provider);
        blockRecipes(provider);
        materialRecipes(provider);
        itemRecipes(provider);
    }

    private static void itemRecipes(Consumer<FinishedRecipe> provider) {
        VanillaRecipeHelper.addShapedRecipe(provider, true, GTMM.id("advanced_terminal"), ADVANCED_TERMINAL.asStack(),
                "SGS", "PBP", "PWP",
                'S', new UnificationEntry(screw, Steel),
                'G', Tags.Items.GLASS_PANES,
                'B', new ItemStack(Items.BOOK),
                'P', new UnificationEntry(plate, Steel),
                'W', new UnificationEntry(wireGtSingle, Tin));
    }

    private static void materialRecipes(Consumer<FinishedRecipe> provider) {
        GTMMRecipeTypes.CoAL_RECIPES.recipeBuilder("coal_electric_motor_lv")
                .inputItems(rodLong, Iron, 48)
                .inputItems(rodLong, IronMagnetic, 24)
                .inputItems(cableGtHex, Tin, 6)
                .inputItems(wireGtHex, Copper, 12)
                .outputItems(GTItems.ELECTRIC_MOTOR_LV.asStack(64))
                .EUt(7).duration(dur(48)).save(provider);
    }

    private static void blockRecipes(Consumer<FinishedRecipe> provider) {
        ASSEMBLER_RECIPES.recipeBuilder("iridium_machine_casing")
                .inputItems(plate, Iridium, 6)
                .inputItems(frameGt, Iridium)
                .outputItems(GTMMCasingBlocks.IRIDIUM_MACHINE_CASING.asStack())
                .circuitMeta(6)
                .EUt(VA[ZPM]).duration(dur(5)).save(provider);
    }

    private static void machineRecipes(Consumer<FinishedRecipe> provider) {
        ASSEMBLY_LINE_RECIPES.recipeBuilder("component_assembly_line")
                .inputItems(GTMultiMachines.ASSEMBLY_LINE.asStack(16))
                .inputItems(GTBlocks.CASING_ASSEMBLY_CONTROL.asStack(16))
                .inputItems(GTBlocks.CASING_ASSEMBLY_LINE.asStack(32))
                .inputItems(GTItems.ROBOT_ARM_UV.asStack(16))
                .inputItems(GTItems.CONVEYOR_MODULE_UV.asStack(32))
                .inputItems(GTItems.ELECTRIC_MOTOR_ZPM.asStack(32))
                .inputItems(pipeNormalFluid, Polybenzimidazole, 16)
                .inputItems(plateDense, Iridium, 64)
                .inputItems(GTMachines.FLUID_SOLIDIFIER[ZPM].asStack(16))
                .inputItems(CustomTags.UV_CIRCUITS, 16)
                .inputItems(CustomTags.ZPM_CIRCUITS, 20)
                .inputItems(CustomTags.LuV_CIRCUITS, 24)
                .inputFluids(SolderingAlloy.getFluid(1782))
                .inputFluids(Naquadria.getFluid(2304))
                .inputFluids(Lubricant.getFluid(5000))
                .outputItems(GTMMMachines.CoAL.asStack())
                .stationResearch(b -> b
                        .researchStack(GTBlocks.CASING_ASSEMBLY_CONTROL.asStack())
                        .CWUt(64)
                        .EUt(VA[UV]))
                .duration(dur(30)).EUt(983040).save(provider);
    }
}
