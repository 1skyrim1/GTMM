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


    public static class MachineConfigs {

        @Configurable
        @Configurable.Comment({"Base for Parallel Logic (Base^(Casing Tier))", "Default: 2"})
        public int parallelMultiplier = 2;
        @Configurable
        @Configurable.Comment({"Casing irgendwas. Keine Ahnung mir fällt grad nix ein. Das ist (Base^(Casing Tier - das hier))", "Default: 2 (MV)"})
        public int casingParallel = 2;
    }
}
