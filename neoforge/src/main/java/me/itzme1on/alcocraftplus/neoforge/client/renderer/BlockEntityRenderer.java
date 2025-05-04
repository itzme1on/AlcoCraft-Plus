package me.itzme1on.alcocraftplus.neoforge.client.renderer;

import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import me.itzme1on.alcocraftplus.core.registries.BlockEntitiesRegistry;
import me.itzme1on.alcocraftplus.neoforge.client.renderer.entities.KegBlockEntityRenderer;

public class BlockEntityRenderer {
    public static void init() {
        registerBlockEntitiesRenderer();
    }

    private static void registerBlockEntitiesRenderer() {
        BlockEntityRendererRegistry.register(BlockEntitiesRegistry.KEG_ENTITY.get(), KegBlockEntityRenderer::new);
    }
}
