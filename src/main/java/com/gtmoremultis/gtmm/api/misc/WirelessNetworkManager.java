//package com.gtmoremultis.gtmm.api.misc;
//
//import com.gtmoremultis.gtmm.data.misc.GlobalEnergyWorldSavedData;
//import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
//
//import java.math.BigInteger;
//import java.util.UUID;
//
//import static com.gtmoremultis.gtmm.data.misc.GlobalVariableStorage.GlobalEnergy;
//
//public class WirelessNetworkManager {
//
//    private WirelessNetworkManager() {}
//
//    public static boolean addEUToGlobalEnergyNet(UUID user_uuid, BigInteger EU) {
//        // mark the data as dirty and in need of saving
//        try {
//            GlobalEnergyWorldSavedData.INSTANCE.setDirty();
//        } catch (Exception exception) {
//            System.out.println("COULD NOT SET GLOBAL ENERGY AS DIRTY IN ADD EU");
//            exception.printStackTrace();
//        }
//
//        // Get the team UUID
//        UUID teamUUID = getTeamUUID(user_uuid);
//
//        // Get the teams total energy stored. If they are not in the map, return 0
//        BigInteger teamEnergy = GlobalEnergy.getOrDefault(teamUUID, BigInteger.ZERO);
//        teamEnergy = teamEnergy.add(EU);
//
//        // If there is sufficient EU then complete the operation and return true
//        if (teamEnergy.signum() >= 0) {
//            GlobalEnergy.put(teamUUID, teamEnergy);
//            return true;
//        }
//
//        // If there is not sufficient EU then return false
//        return false;
//    }
//
//    public static boolean addEUToGlobalEnergyNet(UUID user_uuid, long EU) {
//        return addEUToGlobalEnergyNet(user_uuid, BigInteger.valueOf(EU));
//    }
//
//    public static boolean addEUToGlobalEnergyNet(UUID user_uuid, int EU) {
//        return addEUToGlobalEnergyNet(user_uuid, BigInteger.valueOf(EU));
//    }
//
//    public static BigInteger getUserEU(UUID user_uuid) {
//        // Get the teams total energy stored. If they are not in the map, return 0
//        return GlobalEnergy.getOrDefault(getTeamUUID(user_uuid), BigInteger.ZERO);
//    }
//
//    public static void setUserEU(UUID user_uuid, BigInteger EU) {
//        // mark the data as dirty and in need of saving
//        try {
//            GlobalEnergyWorldSavedData.INSTANCE.setDirty();
//        } catch (Exception exception) {
//            System.out.println("COULD NOT SET GLOBAL ENERGY AS DIRTY IN SET USER EU");
//            exception.printStackTrace();
//        }
//
//        GlobalEnergy.put(getTeamUUID(user_uuid), EU);
//    }
//
//    public static void clearGlobalEnergy() {
//        // DO NOT USE THIS METHOD UNLESS YOU KNOW WHAT YOU ARE DOING
//        GlobalEnergy.clear();
//    }
//
//
//    public static UUID getTeamUUID(UUID user_uuid) {
//        return FTBTeamsAPI.api().getManager().getTeamForPlayerID(user_uuid).get().getOwner();
//    }
//}
