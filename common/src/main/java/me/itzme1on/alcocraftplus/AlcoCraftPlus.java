package me.itzme1on.alcocraftplus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AlcoCraftPlus {
    // Define MOD_ID and LOGGER here so they are accessible commonly
    public static final String MOD_ID = "alcocraftplus";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static void initializeCommon() {
        // This method is called by both Fabric and NeoForge entry points.
        // All registry calls have been moved to platform-specific modules.
        LOGGER.info("Initializing AlcoCraftPlus Common...");


        LOGGER.info("AlcoCraftPlus Common Initialized!");
    }
}