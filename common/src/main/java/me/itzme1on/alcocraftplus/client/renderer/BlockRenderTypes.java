package me.itzme1on.alcocraftplus.client.renderer;

import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import me.itzme1on.alcocraftplus.core.registries.BlocksRegistry;
import net.minecraft.client.renderer.RenderType;

public class BlockRenderTypes {
    private BlockRenderTypes() {
    }
    
    public static void init() {
        RenderTypeRegistry.register(RenderType.cutout(), BlocksRegistry.HOP.get());
        RenderTypeRegistry.register(RenderType.cutout(), BlocksRegistry.HOP_PLANT.get());
        RenderTypeRegistry.register(RenderType.cutout(), BlocksRegistry.MUG.get());
        RenderTypeRegistry.register(RenderType.cutout(), BlocksRegistry.KVASS.get());
        RenderTypeRegistry.register(RenderType.cutout(), BlocksRegistry.CHORUS_ALE.get());
        RenderTypeRegistry.register(RenderType.cutout(), BlocksRegistry.DIGGER_BITTER.get());
        RenderTypeRegistry.register(RenderType.cutout(), BlocksRegistry.DROWNED_ALE.get());
        RenderTypeRegistry.register(RenderType.cutout(), BlocksRegistry.ICE_BEER.get());
        RenderTypeRegistry.register(RenderType.cutout(), BlocksRegistry.LEPRECHAUN_CIDER.get());
        RenderTypeRegistry.register(RenderType.cutout(), BlocksRegistry.MAGNET_PILSNER.get());
        RenderTypeRegistry.register(RenderType.cutout(), BlocksRegistry.NETHER_PORTER.get());
        RenderTypeRegistry.register(RenderType.cutout(), BlocksRegistry.NETHER_STAR_LAGER.get());
        RenderTypeRegistry.register(RenderType.cutout(), BlocksRegistry.NIGHT_RAUCH.get());
        RenderTypeRegistry.register(RenderType.cutout(), BlocksRegistry.SUN_PALE_ALE.get());
        RenderTypeRegistry.register(RenderType.cutout(), BlocksRegistry.WITHER_STOUT.get());
    }
}
