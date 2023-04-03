package net.hadrus.alcocraft.blocks;

import net.hadrus.alcocraft.AlcoCraft;
import net.hadrus.alcocraft.blocks.workstations.KegBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AlcoBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, AlcoCraft.MOD_ID);

    public static final RegistryObject<BlockEntityType<KegBlockEntity>> KEG_ENTITY =
            BLOCK_ENTITIES.register("keg_entity",
                    () -> BlockEntityType.Builder.of(KegBlockEntity::new, AlcoBlocks.SPRUCE_KEG.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}