package com.gtmoremultis.gtmm.config;

import com.gtmoremultis.gtmm.GTMM;
import dev.toma.configuration.Configuration;
import dev.toma.configuration.config.Config;
import dev.toma.configuration.config.Configurable;
import dev.toma.configuration.config.format.ConfigFormats;

@Config(id = GTMM.MOD_ID)
public class ConfigHandler {
    public static ConfigHandler INSTANCE;
    private static final Object LOCK = new Object();

    public static void init() {
        synchronized (LOCK) {
            if (INSTANCE == null) {
                INSTANCE = Configuration.registerConfig(ConfigHandler.class, ConfigFormats.yaml()).getConfigInstance();
            }
        }
    }

    @Configurable
    public MachineConfigs machine = new MachineConfigs();

    @Configurable
    public WirelessConfigs wireless = new WirelessConfigs();

    public static class WirelessConfigs {
        @Configurable
        @Configurable.Comment({"Enable Wireless Energy Hatches for Low Tiers?", "Default: false"})
        public boolean enable_low_tier_wireless = false;
    }


    public static class MachineConfigs {
        @Configurable
        @Configurable.Comment({"Base for Parallel Logic (Base^(Casing Tier))", "Default: 4"})
        public int parallelMultiplier = 2;
        @Configurable
        @Configurable.Comment({"Casing irgendwas. Keine Ahnung mir fällt grad nix ein. Das ist (Base^(Casing Tier - das hier))", "Default: 0"})
        public int casingParallel = 2;
        @Configurable
        @Configurable.Comment({"How long the HPCA can", "Max: 15", "Min: 3", "Default: 15"})
        public int hpca_length = 15;
    }
}
