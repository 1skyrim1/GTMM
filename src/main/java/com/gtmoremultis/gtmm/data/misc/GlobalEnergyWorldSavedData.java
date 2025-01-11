//package com.gtmoremultis.gtmm.data.misc;
//
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.saveddata.SavedData;
//import net.minecraftforge.event.level.LevelEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//
//import java.util.UUID;
//
//import static com.gtmoremultis.gtmm.data.misc.GlobalVariableStorage.GlobalEnergy;
//
//public class GlobalEnergyWorldSavedData extends SavedData {
//
//    public static GlobalEnergyWorldSavedData INSTANCE;
//
//    private static final String DATA_NAME = "GregTech_WirelessEUWorldSavedData";
//
//    private String worldID;
//
//
//    @SuppressWarnings("unused")
//    public GlobalEnergyWorldSavedData(ServerLevel level) {
//        worldID = level.getServer().getWorldData().getLevelName() + "_" + UUID.randomUUID();
//        this.setDirty();
//    }
//
//    public GlobalEnergyWorldSavedData(CompoundTag tag) {
//        this.worldID = tag.getString("id");
//    }
//
//    @Override
//    public CompoundTag save(CompoundTag compoundTag) {
//        compoundTag.putString("id", worldID);
//        return compoundTag;
//    }
//
//    public static void init(ServerLevel world) {
//        INSTANCE = world.getDataStorage()
//                .computeIfAbsent(GlobalEnergyWorldSavedData::new, () -> GlobalEnergyWorldSavedData(world), DATA_NAME);
//    }
//
//    public static String getWorldID() {
//        return INSTANCE.worldID;
//    }
//}
