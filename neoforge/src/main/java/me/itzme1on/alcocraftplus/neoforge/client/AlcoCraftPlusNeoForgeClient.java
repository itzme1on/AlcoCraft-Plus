package me.itzme1on.alcocraftplus.neoforge.client;

import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.neoforge.client.gui.KegGui;
import me.itzme1on.alcocraftplus.neoforge.client.particles.YellowBubbleParticle;
import me.itzme1on.alcocraftplus.neoforge.client.renderer.BlockEntityRenderer;
import me.itzme1on.alcocraftplus.neoforge.client.renderer.BlockRenderer; // Needs separate handling
import me.itzme1on.alcocraftplus.neoforge.core.registries.ParticlesRegistry;
import me.itzme1on.alcocraftplus.neoforge.core.registries.ScreenHandlerRegistry;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@Mod.EventBusSubscriber(modid = AlcoCraftPlus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class AlcoCraftPlusNeoForgeClient {

    private AlcoCraftPlusNeoForgeClient() {}

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        BlockEntityRenderer.init();
        BlockRenderer.init(); // Needs separate handling (e.g., ModelEvent listeners)
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ScreenHandlerRegistry.KEG_MENU.get(), KegGui::new);
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ParticlesRegistry.YELLOW_BUBBLE.get(), YellowBubbleParticle.Factory::new);
    }
}