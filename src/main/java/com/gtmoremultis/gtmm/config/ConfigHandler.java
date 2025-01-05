package com.gtmoremultis.gtmm.config;

import com.gtmoremultis.gtmm.GTMM;
import dev.toma.configuration.Configuration;
import dev.toma.configuration.config.Config;
import dev.toma.configuration.config.Configurable;
import dev.toma.configuration.config.format.ConfigFormats;

@Config(id = GTMM.MOD_ID)
public class ConfigHandler {
    public static ConfigHandler INSTANCE = Configuration.registerConfig(ConfigHandler.class, ConfigFormats.json()).getConfigInstance();

    @Configurable
    public ClientConfigs Client = new ClientConfigs();
    @Configurable
    public ServerConfigs Server = new ServerConfigs();

    public static class ServerConfigs {
        @Configurable
        @Configurable.Comment({"Makes EMI Better", "Default: true"})
        public boolean makesEMIBetter = true;
        ServerConfigs() {
        }
    }

    public static class ClientConfigs {
        @Configurable
        @Configurable.Comment({"Test Comment", "Default: false"})
        public boolean test = false;
        ClientConfigs() {
        }
    }
}
