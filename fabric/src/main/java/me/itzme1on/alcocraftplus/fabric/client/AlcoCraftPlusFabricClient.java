package me.itzme1on.alcocraftplus.fabric.client;

import dev.architectury.registry.client.gui.MenuScreenRegistry;
import me.itzme1on.alcocraftplus.client.gui.KegGui;
import me.itzme1on.alcocraftplus.client.particles.YellowBubbleParticle;
import me.itzme1on.alcocraftplus.client.renderer.BlockEntityRenderers;
import me.itzme1on.alcocraftplus.client.renderer.BlockRenderTypes;
import me.itzme1on.alcocraftplus.core.registries.ParticlesRegistry;
import me.itzme1on.alcocraftplus.core.registries.ScreenHandlerRegistry;
import dev.architectury.registry.client.particle.ParticleProviderRegistry;
import net.fabricmc.api.ClientModInitializer;

public final class AlcoCraftPlusFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MenuScreenRegistry.registerScreenFactory(ScreenHandlerRegistry.KEG_MENU.get(), KegGui::new);

        BlockRenderTypes.init();
        BlockEntityRenderers.init();

        ParticleProviderRegistry.register(ParticlesRegistry.YELLOW_BUBBLE.get(), YellowBubbleParticle.Factory::new);
    }
}
