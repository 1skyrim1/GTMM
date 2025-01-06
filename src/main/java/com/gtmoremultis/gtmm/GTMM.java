package com.gtmoremultis.gtmm;

import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.gtmoremultis.gtmm.config.ConfigHandler;
import com.gtmoremultis.gtmm.init.CommonProxy;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(GTMM.MOD_ID)
public class GTMM {
    public static final String MOD_ID = "gtmm";
    public static final Logger LOGGER = LogManager.getLogger();

    public GTMM() {
//        MinecraftForge.EVENT_BUS.register(this);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.register(this);
        CommonProxy.init();
        modEventBus.addGenericListener(MachineDefinition.class, GTMMRegistries::registerMachines);
    }


    public static ConfigHandler.ClientConfigs getClientConfig() {
        return ConfigHandler.INSTANCE.Client;
    }

    public static ConfigHandler.ServerConfigs getServerConfig() {
        return ConfigHandler.INSTANCE.Server;
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, FormattingUtil.toLowerCaseUnder(path));
    }
}
