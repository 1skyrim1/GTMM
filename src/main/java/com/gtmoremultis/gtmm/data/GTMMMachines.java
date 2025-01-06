package com.gtmoremultis.gtmm.data;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.MultiblockShapeInfo;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.client.renderer.machine.WorkableCasingMachineRenderer;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.machines.GCYMMachines;
import com.gtmoremultis.gtmm.GTMM;
import com.gtmoremultis.gtmm.api.machine.multiblock.GTMMMachine;
import com.gtmoremultis.gtmm.api.pattern.APredicates;
import com.gtmoremultis.gtmm.block.BlockTier;
import com.gtmoremultis.gtmm.block.MachineCasingBlock;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.pattern.Predicates.abilities;
import static com.gregtechceu.gtceu.api.pattern.Predicates.autoAbilities;
import static com.gtmoremultis.gtmm.GTMMRegistries.REGISTRATE;

@SuppressWarnings("unused")
public class GTMMMachines {
    public static final int[] ULV2UV = GTValues.tiersBetween(0, 8);
    public static final int[] MV2ZPM = GTValues.tiersBetween(2, 7);
    public static final int[] EV2UV = GTValues.tiersBetween(4, 8);
    static {
        REGISTRATE.creativeModeTab(() -> GTMMCreativeModeTabs.MAIN_TAB);
    }
    // Part

//    public static final MachineDefinition[] WIRELESS_ENERGY_HATCH = REGISTRATE.machine("wireless_energy_hatch", )

    // Machine

    public static final MultiblockMachineDefinition CoAL = REGISTRATE.multiblock("coal", GTMMMachine::new)
            .rotationState(RotationState.ALL)
            .tooltips(Component.translatable("gtmm.multiblock.coal.tooltip"))
            .recipeTypes(GTMMRecipeTypes.CoAL_RECIPES)
            .recipeModifier(GTMMMachine::GTMMRecipeModifier)
            .appearanceBlock(GTMMCasingBlocks.IRIDIUM_MACHINE_CASING)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("CCCCCCCCC", "C  CCC  C", "C       C", "C       C", "C       C", "C       C", "CC     CC", " CCCCCCC ", "         ", "         ")
                    .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FCCCCCF ", "         ")
                    .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G A   A G", "GL     LG", "GL     LG", "CL     LC", "F       F", " FC   CF ", "   CMC   ")
                    .aisle("CCCCCCCCC", "G  F F  G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FC   CF ", "   CMC   ")
                    .aisle("CCCCCCCCC", "GP     PG", "GP CCC PG", "GP     PG", "GP     PG", "GP  L  PG", "CPP A PPC", "F PPAPP F", " FC   CF ", "   MMM   ")
                    .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FC   CF ", "   CMC   ")
                    .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G A   A G", "GL     LG", "GL     LG", "CL     LC", "F       F", " FC   CF ", "   CMC   ")
                    .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FC   CF ", "   CMC   ")
                    .aisle("CCCCCCCCC", "CP     PC", "CP CCC PC", "CP     PC", "CP     PC", "CP  L  PC", "CPP A PPC", "F PPAPP F", " FC   CF ", "   MMM   ")
                    .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FC   CF ", "   CMC   ")
                    .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G A   A G", "GL     LG", "GL     LG", "CL     LC", "F       F", " FC   CF ", "   CMC   ")
                    .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FC   CF ", "   CMC   ")
                    .aisle("CCCCCCCCC", "GP     PG", "GP CCC PG", "GP     PG", "GP     PG", "GP  L  PG", "CPP A PPC", "F PPAPP F", " FC   CF ", "   MMM   ")
                    .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FC   CF ", "   CMC   ")
                    .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G A   A G", "GL     LG", "GL     LG", "CL     LC", "F       F", " FC   CF ", "   CMC   ")
                    .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FC   CF ", "   CMC   ")
                    .aisle("CCCCCCCCC", "CP     PC", "CP CCC PC", "CP     PC", "CP     PC", "CP  L  PC", "CPP A PPC", "F PPAPP F", " FC   CF ", "   MMM   ")
                    .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FC   CF ", "   CMC   ")
                    .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G A   A G", "GL     LG", "GL     LG", "CL     LC", "F       F", " FC   CF ", "   CMC   ")
                    .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FC   CF ", "   CMC   ")
                    .aisle("CCCCCCCCC", "GP     PG", "GP CCC PG", "GP     PG", "GP     PG", "GP  L  PG", "CPP A PPC", "F PPAPP F", " FC   CF ", "   MMM   ")
                    .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FC   CF ", "   CMC   ")
                    .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G A   A G", "GL     LG", "GL     LG", "CL     LC", "F       F", " FC   CF ", "   CMC   ")
                    .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FC   CF ", "   CMC   ")
                    .aisle("CCCCCCCCC", "CP     PC", "CP CCC PC", "CP     PC", "CP     PC", "CP  L  PC", "CPP A PPC", "F PPAPP F", " FC   CF ", "   MMM   ")
                    .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FC   CF ", "   CMC   ")
                    .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G A   A G", "GL     LG", "GL     LG", "CL     LC", "F       F", " FC   CF ", "   CMC   ")
                    .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FC   CF ", "   CMC   ")
                    .aisle("CCCCCCCCC", "GP     PG", "GP CCC PG", "GP     PG", "GP     PG", "GP  L  PG", "CPP A PPC", "F PPAPP F", " FC   CF ", "   MMM   ")
                    .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FC   CF ", "   CMC   ")
                    .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G A   A G", "GL     LG", "GL     LG", "CL     LC", "F       F", " FC   CF ", "   CMC   ")
                    .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FCCCCCF ", "         ")
                    .aisle("CCCCCCCCC", "C  T T  C", "C  CCC  C", "C  CCC  C", "C       C", "C       C", "CC CCC CC", " CCCKCCC ", "   CCC   ", "         ")
                    .where("K", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("C", Predicates.blocks(GTMMCasingBlocks.IRIDIUM_MACHINE_CASING.get())
                            .or(autoAbilities(true, false, false))
                            .or(abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(2, 1))
                            .or(abilities(PartAbility.IMPORT_ITEMS))
                            .or(abilities(PartAbility.EXPORT_ITEMS))
                            .or(abilities(PartAbility.IMPORT_FLUIDS))
                    )
                    .where('G', Predicates.blocks(GTBlocks.CASING_LAMINATED_GLASS.get()))
                    .where('F', Predicates.blocks(GTBlocks.FILTER_CASING.get()))
                    .where('P', Predicates.blocks(GTBlocks.CASING_PTFE_INERT.get()))
                    .where('A', Predicates.blocks(GTBlocks.CASING_ASSEMBLY_CONTROL.get()))
                    .where('T', Predicates.blocks(ChemicalHelper.getBlock(TagPrefix.frameGt, GTMaterials.TungstenSteel)))
                    .where('L', Predicates.blocks(GTBlocks.CASING_ASSEMBLY_LINE.get()))
                    .where('M', APredicates.machineCasing())
                    .where(' ', Predicates.any())
                    .build())
            .shapeInfos(definition -> {
                List<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
                var builder = MultiblockShapeInfo.builder()
                        .aisle("CCCCCCCCC", "C  CCC  C", "C       C", "C       C", "C       C", "C       C", "CC     CC", " CCCCCCC ", "         ", "         ")
                        .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FCCCCCF ", "         ")
                        .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G A   A G", "GL     LG", "GL     LG", "CL     LC", "F       F", " FC   CF ", "   CMC   ")
                        .aisle("CCCCCCCCC", "G  F F  G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FC   CF ", "   CMC   ")
                        .aisle("CCCCCCCCC", "GP     PG", "GP CCC PG", "GP     PG", "GP     PG", "GP  L  PG", "CPP A PPC", "F PPAPP F", " FC   CF ", "   MMM   ")
                        .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FC   CF ", "   CMC   ")
                        .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G A   A G", "GL     LG", "GL     LG", "CL     LC", "F       F", " FC   CF ", "   CMC   ")
                        .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FC   CF ", "   CMC   ")
                        .aisle("CCCCCCCCC", "CP     PC", "CP CCC PC", "CP     PC", "CP     PC", "CP  L  PC", "CPP A PPC", "F PPAPP F", " FC   CF ", "   MMM   ")
                        .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FC   CF ", "   CMC   ")
                        .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G A   A G", "GL     LG", "GL     LG", "CL     LC", "F       F", " FC   CF ", "   CMC   ")
                        .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FC   CF ", "   CMC   ")
                        .aisle("CCCCCCCCC", "GP     PG", "GP CCC PG", "GP     PG", "GP     PG", "GP  L  PG", "CPP A PPC", "F PPAPP F", " FC   CF ", "   MMM   ")
                        .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FC   CF ", "   CMC   ")
                        .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G A   A G", "GL     LG", "GL     LG", "CL     LC", "F       F", " FC   CF ", "   CMC   ")
                        .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FC   CF ", "   CMC   ")
                        .aisle("CCCCCCCCC", "CP     PC", "CP CCC PC", "CP     PC", "CP     PC", "CP  L  PC", "CPP A PPC", "F PPAPP F", " FC   CF ", "   MMM   ")
                        .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FC   CF ", "   CMC   ")
                        .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G A   A G", "GL     LG", "GL     LG", "CL     LC", "F       F", " FC   CF ", "   CMC   ")
                        .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FC   CF ", "   CMC   ")
                        .aisle("CCCCCCCCC", "GP     PG", "GP CCC PG", "GP     PG", "GP     PG", "GP  L  PG", "CPP A PPC", "F PPAPP F", " FC   CF ", "   MMM   ")
                        .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FC   CF ", "   CMC   ")
                        .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G A   A G", "GL     LG", "GL     LG", "CL     LC", "F       F", " FC   CF ", "   CMC   ")
                        .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FC   CF ", "   CMC   ")
                        .aisle("CCCCCCCCC", "CP     PC", "CP CCC PC", "CP     PC", "CP     PC", "CP  L  PC", "CPP A PPC", "F PPAPP F", " FC   CF ", "   MMM   ")
                        .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FC   CF ", "   CMC   ")
                        .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G A   A G", "GL     LG", "GL     LG", "CL     LC", "F       F", " FC   CF ", "   CMC   ")
                        .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FC   CF ", "   CMC   ")
                        .aisle("CCCCCCCCC", "GP     PG", "GP CCC PG", "GP     PG", "GP     PG", "GP  L  PG", "CPP A PPC", "F PPAPP F", " FC   CF ", "   MMM   ")
                        .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FC   CF ", "   CMC   ")
                        .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G A   A G", "GL     LG", "GL     LG", "CL     LC", "F       F", " FC   CF ", "   CMC   ")
                        .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FCCCCCF ", "         ")
                        .aisle("UDJCCCCCE", "C  T T  C", "C  CCC  C", "C  CCC  C", "C       C", "C       C", "CC CHC CC", " CCCKCCC ", "   CCC   ", "         ")
                        .where('K', definition, Direction.SOUTH)
                        .where(' ', Blocks.AIR.defaultBlockState())
                        .where('H', GTMachines.AUTO_MAINTENANCE_HATCH, Direction.SOUTH)
                        .where('C', GTMMCasingBlocks.IRIDIUM_MACHINE_CASING.getDefaultState())
                        .where('G', GTBlocks.CASING_LAMINATED_GLASS.getDefaultState())
                        .where('F', GTBlocks.FILTER_CASING.getDefaultState())
                        .where('P', GTBlocks.CASING_PTFE_INERT.getDefaultState())
                        .where('A', GTBlocks.CASING_ASSEMBLY_CONTROL.getDefaultState())
                        .where('T', ChemicalHelper.getBlock(TagPrefix.frameGt, GTMaterials.TungstenSteel))
                        .where('L', GTBlocks.CASING_ASSEMBLY_LINE.getDefaultState())
                        .where('E', GTMachines.ENERGY_INPUT_HATCH[10], Direction.SOUTH)
                        .where('D', GTMachines.ITEM_IMPORT_BUS[9], Direction.SOUTH)
                        .where('J', GTMachines.ITEM_EXPORT_BUS[9], Direction.SOUTH)
                        .where('U', GTMachines.FLUID_IMPORT_HATCH_9X[9], Direction.SOUTH);
                Map<Integer, BlockState> shapeBlock = new HashMap<>();
                for (MachineCasingBlock.MachineCasing machineCasing : MachineCasingBlock.MachineCasing.values()) {
                    shapeBlock.put(machineCasing.getTier(), machineCasing.getMachineCasing(machineCasing.getTier()).getDefaultState());
                }
                for (BlockTier tier : BlockTier.values()) {
                    builder.where('M', shapeBlock.get(tier.tier()));
                    shapeInfo.add(builder.shallowCopy().build());
                }
                return shapeInfo;
            })
            .renderer(() -> new WorkableCasingMachineRenderer(GTMM.id("block/casings/solid/iridium_machine_casing"), GTCEu.id("block/multiblock/assembly_line")))
            .additionalDisplay((controller, components) -> {
                if (controller instanceof GTMMMachine gtmmMachine && controller.isFormed()) {
                    components.add(Component.translatable("gtmm.multiblock.coal.parallel_level", Math.pow(4, gtmmMachine.getMachineCasingTier())));
                    components.add(Component.translatable("gtmm.multiblock.coal.tier", VNF[gtmmMachine.getMachineCasingTier()]));
                }
            })
            .register();

    public static void init() {
        modifyGT();
    }

    private static void modifyGT() {
//        var largeAssembler = GCYMMachines.LARGE_ASSEMBLER;
//        var gtRecipeTypes = new ArrayList<>(Arrays.asList(Objects.requireNonNull(largeAssembler.getRecipeTypes())));
//        gtRecipeTypes.add(GTMMRecipeTypes.PRECISION_ASSEMBLY_RECIPES);
//        GTRecipeType[] gtRecipeTypesArray = gtRecipeTypes.toArray(GTRecipeType[]::new);
//        largeAssembler.setRecipeTypes(gtRecipeTypesArray);
    }
}