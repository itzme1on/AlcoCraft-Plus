package me.itzme1on.alcocraftplus.core.registries;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.core.blockentities.KegEntity; // Import your KegEntity
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BlockEntitiesRegistry {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(AlcoCraftPlus.MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    // Register the Keg Block Entity Type
    public static final RegistrySupplier<BlockEntityType<KegEntity>> KEG_ENTITY = BLOCK_ENTITIES.register(
            "keg_entity", // The registry name
            () -> BlockEntityType.Builder.of(
                    KegEntity::new, // Reference to the KegEntity constructor
                    BlocksRegistry.KEG.get() // The block(s) this entity is associated with
            ).build(null) // build(null) is standard for DeferredRegister
    );

    public static void init() {
        BLOCK_ENTITIES.register();
        AlcoCraftPlus.LOGGER.info("Registering Mod Block Entities for " + AlcoCraftPlus.MOD_ID);
    }
}