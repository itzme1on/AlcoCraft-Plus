package me.itzme1on.alcocraftplus.neoforge.client;

import me.itzme1on.alcocraftplus.client.gui.KegGui;
import me.itzme1on.alcocraftplus.client.particles.YellowBubbleParticle;
import me.itzme1on.alcocraftplus.client.renderer.BlockEntityRenderers;
import me.itzme1on.alcocraftplus.client.renderer.BlockRenderTypes;
import me.itzme1on.alcocraftplus.core.registries.ParticlesRegistry;
import me.itzme1on.alcocraftplus.core.registries.ScreenHandlerRegistry;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

public class AlcoCraftPlusNeoForgeClient {
    private AlcoCraftPlusNeoForgeClient() {
    }

    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            BlockRenderTypes.init();
            BlockEntityRenderers.init();
        });
    }

    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ParticlesRegistry.YELLOW_BUBBLE.get(), YellowBubbleParticle.Factory::new);
    }

    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ScreenHandlerRegistry.KEG_MENU.get(), KegGui::new);
    }
}
