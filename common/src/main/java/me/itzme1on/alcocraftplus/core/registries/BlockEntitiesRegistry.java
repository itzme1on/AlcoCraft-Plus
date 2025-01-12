package me.itzme1on.alcocraftplus.core.registries;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.core.blocks.keg.KegEntity;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BlockEntitiesRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(AlcoCraftPlus.MOD_ID, Registry.BLOCK_ENTITY_TYPE_REGISTRY);

    public static final RegistrySupplier<BlockEntityType<KegEntity>> KEG_ENTITY =
            BLOCK_ENTITIES.register("keg_entity", () -> BlockEntityType.Builder.of(KegEntity::new, BlocksRegistry.KEG.get()).build(null));

    public static void register() {
        BLOCK_ENTITIES.register();
    }
}
