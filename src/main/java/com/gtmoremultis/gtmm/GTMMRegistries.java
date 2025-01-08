package com.gtmoremultis.gtmm;

import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = GTMM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class GTMMRegistries {
    public static final GTRegistrate REGISTRATE = GTRegistrate.create(GTMM.MOD_ID);
}
