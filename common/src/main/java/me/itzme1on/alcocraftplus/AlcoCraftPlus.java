package me.itzme1on.alcocraftplus;

import dev.architectury.event.events.common.LifecycleEvent;
import me.itzme1on.alcocraftplus.core.events.CommonEvents;
import me.itzme1on.alcocraftplus.core.registries.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AlcoCraftPlus {
    public static final String MOD_ID = "alcocraftplus";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

   public static void initializeCommon() {
       LOGGER.info("Initializing AlcoCraftPlus...");

        ScreenHandlerRegistry.register();
        RecipesRegistry.register();
        ParticlesRegistry.register();
        EffectsRegistry.register();
        BlocksRegistry.register();
        ItemsRegistry.register();
        TabGroupRegistry.register();
        BlockEntitiesRegistry.register();
        CommonEvents.register();
        LifecycleEvent.SETUP.register(CompostableRegistry::register);

        LOGGER.info("AlcoCraftPlus Initialized!");
    }
}
