package me.itzme1on.alcocraftplus.neoforge; // Correct package

import me.itzme1on.alcocraftplus.neoforge.core.registries.*;
import dev.architectury.platform.neoforge.EventBuses;
import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(AlcoCraftPlus.MOD_ID)
public final class AlcoCraftPlusNeoForge {
    public AlcoCraftPlusNeoForge(IEventBus modEventBus) {
        EventBuses.registerModEventBus(AlcoCraftPlus.MOD_ID, modEventBus); // Correct registration
        ItemsRegistry.register(modEventBus);
        BlocksRegistry.register(modEventBus);
        BlockEntitiesRegistry.register(modEventBus);
        ScreenHandlerRegistry.register(modEventBus);
        EffectsRegistry.register(modEventBus);
        RecipesRegistry.register(modEventBus);
        ParticlesRegistry.register(modEventBus);
        AlcoCraftPlus.initializeCommon();
    }
}