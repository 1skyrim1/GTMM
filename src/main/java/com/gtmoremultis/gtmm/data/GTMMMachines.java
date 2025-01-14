package com.gtmoremultis.gtmm.data;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.MultiblockShapeInfo;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.client.renderer.machine.WorkableCasingMachineRenderer;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gtmoremultis.gtmm.GTMM;
import com.gtmoremultis.gtmm.api.machine.multiblock.APartAbility;
import com.gtmoremultis.gtmm.api.machine.multiblock.GTMMMachine;
import com.gtmoremultis.gtmm.api.pattern.APredicates;
import com.gtmoremultis.gtmm.block.BlockTier;
import com.gtmoremultis.gtmm.block.MachineCasingBlock;
import com.gtmoremultis.gtmm.config.ConfigHandler;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.pattern.Predicates.abilities;
import static com.gregtechceu.gtceu.api.pattern.Predicates.autoAbilities;
import static com.gtmoremultis.gtmm.GTMMRegistries.REGISTRATE;
import static com.gtmoremultis.gtmm.data.GTMMMachineUtils.*;

@SuppressWarnings("unused")
public class GTMMMachines {
    public static final int[] ULV2UV = GTValues.tiersBetween(0, 8);
    public static final int[] MV2ZPM = GTValues.tiersBetween(2, 7);
    public static final int[] EV2UV = GTValues.tiersBetween(4, 8);
    static {
        REGISTRATE.creativeModeTab(() -> GTMMCreativeModeTabs.MAIN_TAB);
    }
    // Part

    public static final MachineDefinition[] WIRELESS_ENERGY_INPUT_HATCH = registerWirelessHatch(IO.IN, 1, APartAbility.INPUT_WIRELESS_ENERGY);
    public static final MachineDefinition[] WIRELESS_ENERGY_OUTPUT_HATCH = registerWirelessHatch(IO.OUT, 1, APartAbility.OUTPUT_WIRELESS_ENERGY);
    public static final MachineDefinition[] WIRELESS_ENERGY_INPUT_HATCH_4 = registerWirelessHatch(IO.IN, 4, APartAbility.INPUT_WIRELESS_ENERGY);
    public static final MachineDefinition[] WIRELESS_ENERGY_OUTPUT_HATCH_4 = registerWirelessHatch(IO.OUT, 4, APartAbility.OUTPUT_WIRELESS_ENERGY);
    public static final MachineDefinition[] WIRELESS_ENERGY_INPUT_HATCH_16 = registerWirelessHatch(IO.IN, 16, APartAbility.INPUT_WIRELESS_ENERGY);
    public static final MachineDefinition[] WIRELESS_ENERGY_OUTPUT_HATCH_16 = registerWirelessHatch(IO.OUT, 16, APartAbility.OUTPUT_WIRELESS_ENERGY);
    public static final MachineDefinition[] WIRELESS_ENERGY_INPUT_HATCH_64 = registerWirelessHatch(IO.IN, 64, APartAbility.INPUT_WIRELESS_ENERGY);
    public static final MachineDefinition[] WIRELESS_ENERGY_OUTPUT_HATCH_64 = registerWirelessHatch(IO.OUT, 64, APartAbility.OUTPUT_WIRELESS_ENERGY);

    public static final MachineDefinition[] WIRELESS_ENERGY_INPUT_HATCH_256 = registerHighTierWirelessHatch(IO.IN, 256, APartAbility.INPUT_WIRELESS_ENERGY);
    public static final MachineDefinition[] WIRELESS_ENERGY_OUTPUT_HATCH_256 = registerHighTierWirelessHatch(IO.OUT, 256, APartAbility.OUTPUT_WIRELESS_ENERGY);
    public static final MachineDefinition[] WIRELESS_ENERGY_INPUT_HATCH_1024 = registerHighTierWirelessHatch(IO.IN, 1024, APartAbility.INPUT_WIRELESS_ENERGY);
    public static final MachineDefinition[] WIRELESS_ENERGY_OUTPUT_HATCH_1024 = registerHighTierWirelessHatch(IO.OUT, 1024, APartAbility.OUTPUT_WIRELESS_ENERGY);
    public static final MachineDefinition[] WIRELESS_ENERGY_INPUT_HATCH_4096 = registerHighTierWirelessHatch(IO.IN, 4096, APartAbility.INPUT_WIRELESS_ENERGY);
    public static final MachineDefinition[] WIRELESS_ENERGY_OUTPUT_HATCH_4096 = registerHighTierWirelessHatch(IO.OUT, 4096, APartAbility.OUTPUT_WIRELESS_ENERGY);
    public static final MachineDefinition[] WIRELESS_ENERGY_INPUT_HATCH_16384 = registerHighTierWirelessHatch(IO.IN, 16384, APartAbility.INPUT_WIRELESS_ENERGY);
    public static final MachineDefinition[] WIRELESS_ENERGY_OUTPUT_HATCH_16384 = registerHighTierWirelessHatch(IO.OUT, 16384, APartAbility.OUTPUT_WIRELESS_ENERGY);
    public static final MachineDefinition[] WIRELESS_ENERGY_INPUT_HATCH_65536 = registerHighTierWirelessHatch(IO.IN, 65536, APartAbility.INPUT_WIRELESS_ENERGY);
    public static final MachineDefinition[] WIRELESS_ENERGY_OUTPUT_HATCH_65536 = registerHighTierWirelessHatch(IO.OUT, 65536, APartAbility.OUTPUT_WIRELESS_ENERGY);
    public static final MachineDefinition[] WIRELESS_ENERGY_INPUT_HATCH_262144 = registerHighTierWirelessHatch(IO.IN, 262144, APartAbility.INPUT_WIRELESS_ENERGY);
    public static final MachineDefinition[] WIRELESS_ENERGY_OUTPUT_HATCH_262144 = registerHighTierWirelessHatch(IO.OUT, 262144, APartAbility.OUTPUT_WIRELESS_ENERGY);
    public static final MachineDefinition[] WIRELESS_ENERGY_INPUT_HATCH_1048576 = registerHighTierWirelessHatch(IO.IN, 1048576, APartAbility.INPUT_WIRELESS_ENERGY);
    public static final MachineDefinition[] WIRELESS_ENERGY_OUTPUT_HATCH_1048576 = registerHighTierWirelessHatch(IO.OUT, 1048576, APartAbility.OUTPUT_WIRELESS_ENERGY);
    public static final MachineDefinition[] WIRELESS_ENERGY_INPUT_HATCH_4194304 = registerHighTierWirelessHatch(IO.IN, 4194304, APartAbility.INPUT_WIRELESS_ENERGY);
    public static final MachineDefinition[] WIRELESS_ENERGY_OUTPUT_HATCH_4194304 = registerHighTierWirelessHatch(IO.OUT, 4194304, APartAbility.OUTPUT_WIRELESS_ENERGY);
    public static final MachineDefinition[] WIRELESS_ENERGY_INPUT_HATCH_16777216 = registerHighTierWirelessHatch(IO.IN, 16777216, APartAbility.INPUT_WIRELESS_ENERGY);
    public static final MachineDefinition[] WIRELESS_ENERGY_OUTPUT_HATCH_16777216 = registerHighTierWirelessHatch(IO.OUT, 16777216, APartAbility.OUTPUT_WIRELESS_ENERGY);

    // Machine

    public static final MultiblockMachineDefinition CoAL = REGISTRATE.multiblock("component_assembly_line", GTMMMachine::new)
            .rotationState(RotationState.ALL)
            .recipeTypes(GTMMRecipeTypes.CoAL_RECIPES)
            .recipeModifier(GTMMMachine::GTMMRecipeModifier, true)
            .appearanceBlock(GTMMCasingBlocks.IRIDIUM_MACHINE_CASING)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("CCCCCCCCC", "C  CCC  C", "C       C", "C       C", "C       C", "C       C", "CC     CC", " CCCCCCC ", "         ", "         ")
                    .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FCCCCCF ", "         ")
                    .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G A   A G", "GL     LG", "GL     LG", "CL     LC", "F       F", " FC   CF ", "   CMC   ")
                    .aisle("CCCCCCCCC", "G  T T  G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FC   CF ", "   CMC   ")
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
                            .or(abilities(PartAbility.INPUT_LASER).setMaxGlobalLimited(1, 1))
                            .or(abilities(APartAbility.INPUT_WIRELESS_ENERGY).setMaxGlobalLimited(1, 1))
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
                        .aisle("UDJCCCCCE", "C  T T  C", "C  CCC  C", "C  CCC  C", "C       C", "C       C", "CC CHC CC", " CCCKCCC ", "   CCC   ", "         ")
                        .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FCCCCCF ", "         ")
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
                        .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FC   CF ", "   CMC   ")
                        .aisle("CCCCCCCCC", "CP     PC", "CP CCC PC", "CP     PC", "CP     PC", "CP  L  PC", "CPP A PPC", "F PPAPP F", " FC   CF ", "   MMM   ")
                        .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FC   CF ", "   CMC   ")
                        .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G A   A G", "GL     LG", "GL     LG", "CL     LC", "F       F", " FC   CF ", "   CMC   ")
                        .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FC   CF ", "   CMC   ")
                        .aisle("CCCCCCCCC", "GP     PG", "GP CCC PG", "GP     PG", "GP     PG", "GP  L  PG", "CPP A PPC", "F PPAPP F", " FC   CF ", "   MMM   ")
                        .aisle("CCCCCCCCC", "G  T T  G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FC   CF ", "   CMC   ")
                        .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G A   A G", "GL     LG", "GL     LG", "CL     LC", "F       F", " FC   CF ", "   CMC   ")
                        .aisle("CCCCCCCCC", "G       G", "G  CCC  G", "G       G", "G       G", "G       G", "C       C", "F       F", " FCCCCCF ", "         ")
                        .aisle("CCCCCCCCC", "C  CCC  C", "C       C", "C       C", "C       C", "C       C", "CC     CC", " CCCCCCC ", "         ", "         ")
                        .where('K', definition, Direction.NORTH)
                        .where(' ', Blocks.AIR.defaultBlockState())
                        .where('H', GTMachines.AUTO_MAINTENANCE_HATCH, Direction.NORTH)
                        .where('C', GTMMCasingBlocks.IRIDIUM_MACHINE_CASING.getDefaultState())
                        .where('G', GTBlocks.CASING_LAMINATED_GLASS.getDefaultState())
                        .where('F', GTBlocks.FILTER_CASING.getDefaultState())
                        .where('P', GTBlocks.CASING_PTFE_INERT.getDefaultState())
                        .where('A', GTBlocks.CASING_ASSEMBLY_CONTROL.getDefaultState())
                        .where('T', ChemicalHelper.getBlock(TagPrefix.frameGt, GTMaterials.TungstenSteel))
                        .where('L', GTBlocks.CASING_ASSEMBLY_LINE.getDefaultState())
                        .where('E', GTMachines.ENERGY_INPUT_HATCH[10], Direction.NORTH)
                        .where('D', GTMachines.ITEM_IMPORT_BUS[9], Direction.NORTH)
                        .where('J', GTMachines.ITEM_EXPORT_BUS[9], Direction.NORTH)
                        .where('U', GTMachines.FLUID_IMPORT_HATCH_9X[9], Direction.NORTH);
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
                    components.add(Component.translatable("gtmm.multiblock.coal.tier", VNF[gtmmMachine.getMachineCasingTier()]));
                    components.add(Component.translatable("gtmm.multiblock.coal.parallel_level", (int) Math.pow(ConfigHandler.INSTANCE.machine.parallelMultiplier, gtmmMachine.getMachineCasingTier() - ConfigHandler.INSTANCE.machine.casingParallel)));
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
