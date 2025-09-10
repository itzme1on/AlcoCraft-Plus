package me.itzme1on.alcocraftplus.client.renderer;

import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import me.itzme1on.alcocraftplus.core.registries.BlocksRegistry;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;

public class BlockRenderTypes {
    private BlockRenderTypes() {
    }

    public static void init() {
        RenderTypeRegistry.register(ChunkSectionLayer.CUTOUT, BlocksRegistry.HOP.get());
        RenderTypeRegistry.register(ChunkSectionLayer.CUTOUT, BlocksRegistry.HOP_PLANT.get());
        RenderTypeRegistry.register(ChunkSectionLayer.CUTOUT, BlocksRegistry.MUG.get());
        RenderTypeRegistry.register(ChunkSectionLayer.CUTOUT, BlocksRegistry.KVASS.get());
        RenderTypeRegistry.register(ChunkSectionLayer.CUTOUT, BlocksRegistry.CHORUS_ALE.get());
        RenderTypeRegistry.register(ChunkSectionLayer.CUTOUT, BlocksRegistry.DIGGER_BITTER.get());
        RenderTypeRegistry.register(ChunkSectionLayer.CUTOUT, BlocksRegistry.DROWNED_ALE.get());
        RenderTypeRegistry.register(ChunkSectionLayer.CUTOUT, BlocksRegistry.ICE_BEER.get());
        RenderTypeRegistry.register(ChunkSectionLayer.CUTOUT, BlocksRegistry.LEPRECHAUN_CIDER.get());
        RenderTypeRegistry.register(ChunkSectionLayer.CUTOUT, BlocksRegistry.MAGNET_PILSNER.get());
        RenderTypeRegistry.register(ChunkSectionLayer.CUTOUT, BlocksRegistry.NETHER_PORTER.get());
        RenderTypeRegistry.register(ChunkSectionLayer.CUTOUT, BlocksRegistry.NETHER_STAR_LAGER.get());
        RenderTypeRegistry.register(ChunkSectionLayer.CUTOUT, BlocksRegistry.NIGHT_RAUCH.get());
        RenderTypeRegistry.register(ChunkSectionLayer.CUTOUT, BlocksRegistry.SUN_PALE_ALE.get());
        RenderTypeRegistry.register(ChunkSectionLayer.CUTOUT, BlocksRegistry.WITHER_STOUT.get());
    }
}
