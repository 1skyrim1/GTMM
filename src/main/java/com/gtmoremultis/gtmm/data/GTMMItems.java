package com.gtmoremultis.gtmm.data;

import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.common.item.TooltipBehavior;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.util.NonNullConsumer;

import static com.gtmoremultis.gtmm.GTMMRegistries.REGISTRATE;

public class GTMMItems {
    static {
        REGISTRATE.creativeModeTab(() -> GTMMCreativeModeTabs.MAIN_TAB);
    }

    private static NonNullConsumer<? super ComponentItem> attach(TooltipBehavior components) {
        return (item) -> item.attachComponents(components);
    }
}
