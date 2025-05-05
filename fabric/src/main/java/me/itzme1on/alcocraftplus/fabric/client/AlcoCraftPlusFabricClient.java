package me.itzme1on.alcocraftplus.fabric.client;

//import necessary components from common
import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.fabric.client.gui.KegGui;
import me.itzme1on.alcocraftplus.fabric.client.particles.YellowBubbleParticle;
import me.itzme1on.alcocraftplus.fabric.client.renderer.BlockEntityRenderer;
import me.itzme1on.alcocraftplus.fabric.client.renderer.BlockRenderer;
import me.itzme1on.alcocraftplus.core.registries.ParticlesRegistry;
import me.itzme1on.alcocraftplus.fabric.core.registries.ScreenHandlerRegistry;

//Import Fabric APIs
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.HandledScreens;

@Environment(EnvType.CLIENT)
public final class AlcoCraftPlusFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AlcoCraftPlus.LOGGER.info("Initializing AlcoCraftPlus Fabric Client...");

        HandledScreens.register(ScreenHandlerRegistry.KEG_MENU.get(), KegGui::new);
        AlcoCraftPlus.LOGGER.debug("Registered Screen Factories");

        ParticleFactoryRegistry.getInstance().register(ParticlesRegistry.YELLOW_BUBBLE.get(), YellowBubbleParticle.Factory::new);
        AlcoCraftPlus.LOGGER.debug("Registered Particle Factories");

        BlockRenderer.init();
        BlockEntityRenderer.init();
        AlcoCraftPlus.LOGGER.debug("Registered Renderers");

        AlcoCraftPlus.LOGGER.info("AlcoCraftPlus Fabric Client Initialized!");
    }
}