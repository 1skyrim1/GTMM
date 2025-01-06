package com.gtmoremultis.gtmm.data;

import com.gregtechceu.gtceu.common.data.GTCreativeModeTabs;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gtmoremultis.gtmm.GTMM;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.world.item.CreativeModeTab;

import static com.gtmoremultis.gtmm.GTMMRegistries.REGISTRATE;

public class GTMMCreativeModeTabs {
    public static final RegistryEntry<CreativeModeTab> MAIN_TAB = REGISTRATE.defaultCreativeTab("main",
            builder -> builder.displayItems(new GTCreativeModeTabs.RegistrateDisplayItemsGenerator("main", REGISTRATE))
                    .title(REGISTRATE.addLang("itemGroup", GTMM.id("main"), "GT More Multis"))
                    .icon(GTMachines.CREATIVE_ITEM::asStack)
                    .build())
            .register();
}
