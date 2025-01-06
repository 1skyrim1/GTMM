package com.gtmoremultis.gtmm;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialRegistryEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.registry.MaterialRegistry;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import com.gtmoremultis.gtmm.data.GTMMMachines;
import com.gtmoremultis.gtmm.data.GTMMMaterials;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = GTMM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class GTMMRegistries {
    public static final GTRegistrate REGISTRATE = GTRegistrate.create(GTMM.MOD_ID);
    public static MaterialRegistry MATERIAL_REGISTRY;

    public static void registerMachine(GTCEuAPI.RegisterEvent<ResourceLocation, MachineDefinition> ignoredEvent){
        GTMMMachines.init();
    }

    @SubscribeEvent
    public static void registerMaterialRegistryEvent(MaterialRegistryEvent event) {
        MATERIAL_REGISTRY = GTCEuAPI.materialManager.createRegistry(GTMM.MOD_ID);
    }

    @SubscribeEvent
    public static void registerMaterials(MaterialEvent event) {
        GTMMMaterials.init();
    }

    public GTMMRegistries() {
    }
}
