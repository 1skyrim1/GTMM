package com.gtmoremultis.gtmm.data;

import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import com.gregtechceu.gtceu.common.data.GTModels;
import com.gtmoremultis.gtmm.GTMM;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

import static com.gtmoremultis.gtmm.GTMMRegistries.REGISTRATE;

public class GTMMCasingBlocks {
    static {
        REGISTRATE.creativeModeTab(() -> GTMMCreativeModeTabs.MAIN_TAB);
    }

    public static final BlockEntry<Block> IRIDIUM_MACHINE_CASING = createCasingBlock(
            "iridium_machine_casing", "Iridium Machine Casing",
            GTMM.id("block/casings/solid/iridium_machine_casing"));

    public static final BlockEntry<Block> NAQUADAH_MACHINE_CASING = createCasingBlock(
            "naquadah_machine_casing", "Naquadah Machine Casing",
            GTMM.id("block/casings/solid/naquadah_machine_casing"));

    private static BlockEntry<Block> createCasingBlock(String name, String displayName,
                                                       ResourceLocation texture) {
        return createCasingBlock(name, displayName, Block::new, texture, () -> Blocks.IRON_BLOCK, () -> RenderType::cutoutMipped);
    }

    @SuppressWarnings("all")
    private static BlockEntry<Block> createCasingBlock(String name, String displayName,
                                                       NonNullFunction<BlockBehaviour.Properties, Block> blockSupplier,
                                                       ResourceLocation texture,
                                                       NonNullSupplier<? extends Block> properties,
                                                       Supplier<Supplier<RenderType>> type) {
        return REGISTRATE.block(name, blockSupplier)
                .initialProperties(properties)
                .properties(p -> p.isValidSpawn((state, level, pos, ent) -> false))
                .lang(displayName)
                .blockstate(GTModels.cubeAllModel(name, texture))
                .tag(GTToolType.WRENCH.harvestTags.get(0), BlockTags.MINEABLE_WITH_PICKAXE)
                .item(BlockItem::new)
                .model(NonNullBiConsumer.noop())
                .build()
                .register();
    }

    public static void init(){
    }
}