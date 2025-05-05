package me.itzme1on.alcocraftplus.fabric.client;

// Common imports
import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.core.registries.BlocksRegistry; // Import common BlocksRegistry
import me.itzme1on.alcocraftplus.core.registries.ParticlesRegistry;
import me.itzme1on.alcocraftplus.fabric.client.gui.KegGui;
import me.itzme1on.alcocraftplus.fabric.client.particles.YellowBubbleParticle;
import me.itzme1on.alcocraftplus.fabric.client.renderer.BlockEntityRenderer;
import me.itzme1on.alcocraftplus.fabric.client.renderer.BlockRenderer;
import me.itzme1on.alcocraftplus.fabric.core.registries.ScreenHandlerRegistry; // Assuming this holds the MenuType

// Fabric API imports
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap; // Import for render layers
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.HandledScreens;
import net.minecraft.client.renderer.RenderType; // Import RenderType

@Environment(EnvType.CLIENT)
public final class AlcoCraftPlusFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AlcoCraftPlus.LOGGER.info("Initializing AlcoCraftPlus Fabric Client...");

        // --- Screen Registration ---
        // This line looks correct, assuming ScreenHandlerRegistry.KEG_MENU.get() returns the MenuType<KegMenuCommon>
        HandledScreens.register(ScreenHandlerRegistry.KEG_MENU.get(), KegGui::new);
        AlcoCraftPlus.LOGGER.debug("Registered Screen Factories");

        // --- Particle Registration ---
        ParticleFactoryRegistry.getInstance().register(ParticlesRegistry.YELLOW_BUBBLE.get(), YellowBubbleParticle.Factory::new);
        AlcoCraftPlus.LOGGER.debug("Registered Particle Factories");

        // --- Block Render Layers (Add this section if not handled in BlockRenderer.init()) ---
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.HOPS_CROP.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.MUG.get(), RenderType.cutout()); // Empty mug block
        // Add all your filled mug blocks (AlcoDrink instances)
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.CHORUS_ALE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.DIGGER_BITTER.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.DROWNED_ALE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.ICE_BEER.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.KVASS.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.LEPRECHAUN_CIDER.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.MAGNET_PILSNER.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.NETHER_PORTER.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.NETHER_STAR_LAGER.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.NIGHT_RAUCH.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.SUN_PALE_ALE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.WITHER_STOUT.get(), RenderType.cutout());
        // Keg is likely solid, so no need to add it unless its model uses transparency

        AlcoCraftPlus.LOGGER.debug("Registered Block Render Layers");
        // --- End Block Render Layers Section ---


        // --- Renderer Initialization (Calls your helper classes) ---
        // If BlockRenderer.init() handles the render layers above, you don't need the section above.
        BlockRenderer.init();
        // If BlockEntityRenderer.init() registers BERs (like for the Keg), keep this.
        BlockEntityRenderer.init();
        AlcoCraftPlus.LOGGER.debug("Registered Renderers via helper classes");


        AlcoCraftPlus.LOGGER.info("AlcoCraftPlus Fabric Client Initialized!");
    }
}