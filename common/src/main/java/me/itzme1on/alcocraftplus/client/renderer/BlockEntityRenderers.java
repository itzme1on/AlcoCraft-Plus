package me.itzme1on.alcocraftplus.client.renderer;

import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import me.itzme1on.alcocraftplus.client.renderer.entities.KegBlockEntityRenderer;
import me.itzme1on.alcocraftplus.core.registries.BlockEntitiesRegistry;

public class BlockEntityRenderers {
    private BlockEntityRenderers() {
    }

    public static void init() {
        registerBlockEntitiesRenderer();
    }

    private static void registerBlockEntitiesRenderer() {
        BlockEntityRendererRegistry.register(BlockEntitiesRegistry.KEG_ENTITY.get(), KegBlockEntityRenderer::new);
    }
}
