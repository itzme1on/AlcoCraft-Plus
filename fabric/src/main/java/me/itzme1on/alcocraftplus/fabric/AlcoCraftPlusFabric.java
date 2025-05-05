package me.itzme1on.alcocraftplus.fabric;

// Common Mod Class
import me.itzme1on.alcocraftplus.AlcoCraftPlus;

// Common Registries (Assuming they have static init() methods that call deferredRegister.register())
// Make sure these paths are correct for your project structure
import me.itzme1on.alcocraftplus.core.registries.CreativeModeTabRegistry;
import me.itzme1on.alcocraftplus.core.registries.FluidsRegistry;
import me.itzme1on.alcocraftplus.core.registries.RecipesRegistry;

// Fabric-Specific Registries
import me.itzme1on.alcocraftplus.fabric.core.registries.ScreenHandlerRegistry; // Fabric specific

// Fabric API
import net.fabricmc.api.ModInitializer;

public final class AlcoCraftPlusFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // 1. Call common initialization (if it does anything beyond logging)
        AlcoCraftPlus.init();

        // 2. Trigger registration for all common DeferredRegisters
        // Ensure these classes exist and have a static init() method
        ItemRegistry.init();
        BlockRegistry.init();
        BlockEntityRegistry.init();
        FluidsRegistry.init();
        CreativeModeTabRegistry.init(); // Ensure this works correctly on Fabric side
        RecipesRegistry.init();
        ParticleRegistry.init(); // Use the common ParticleRegistry here

        // 3. Trigger registration for Fabric-specific registries
        ScreenHandlerRegistry.init();

        AlcoCraftPlus.LOGGER.info("AlcoCraft+ Fabric Initialized!"); // Use the common logger
    }
}