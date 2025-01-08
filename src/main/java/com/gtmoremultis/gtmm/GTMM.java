package com.gtmoremultis.gtmm;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialRegistryEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.registry.MaterialRegistry;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.config.ConfigHolder;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.gtmoremultis.gtmm.config.ConfigHandler;
import com.gtmoremultis.gtmm.data.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(GTMM.MOD_ID)
public class GTMM {
    public static final String MOD_ID = "gtmm";
    public static final Logger LOGGER = LogManager.getLogger();
    public static MaterialRegistry MATERIAL_REGISTRY;

    public GTMM() {
        GTMM.init();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.register(this);

        modEventBus.addGenericListener(GTRecipeType.class, this::registerRecipeTypes);
        modEventBus.addGenericListener(MachineDefinition.class, this::registerMachines);
    }

    public static void init() {
        ConfigHolder.init();
        GTMMBlocks.init();
        GTMMItems.init();

        GTMMDataGen.init();

        GTMMRegistries.REGISTRATE.registerRegistrate();
    }

    @SubscribeEvent
    public void registerMaterialRegistryEvent(MaterialRegistryEvent event) {
        MATERIAL_REGISTRY = GTCEuAPI.materialManager.createRegistry(GTMM.MOD_ID);
    }

    @SubscribeEvent
    public void registerMaterials(MaterialEvent event) {
        GTMMMaterials.init();
    }

    public void registerRecipeTypes(GTCEuAPI.RegisterEvent<ResourceLocation, GTRecipeType> event) {
        GTMMRecipeTypes.init();
    }

    public void registerMachines(GTCEuAPI.RegisterEvent<ResourceLocation, MachineDefinition> event){
        GTMMMachines.init();
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
