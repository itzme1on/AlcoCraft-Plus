package me.itzme1on.alcocraftplus.neoforge.client.renderer;

import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import me.itzme1on.alcocraftplus.core.registries.BlocksRegistry;
import net.minecraft.client.renderer.RenderType;

public class BlockRenderer {
    public static void init() {
        RenderTypeRegistry.register(RenderType.cutout(), BlocksRegistry.HOP.get());
        RenderTypeRegistry.register(RenderType.cutout(), BlocksRegistry.HOP_PLANT.get());
        RenderTypeRegistry.register(RenderType.cutout(), BlocksRegistry.MUG.get());
    }
}
