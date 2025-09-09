package me.itzme1on.alcocraftplus.fabric.client;

import dev.architectury.registry.menu.MenuRegistry;
import me.itzme1on.alcocraftplus.client.gui.KegGui;
import me.itzme1on.alcocraftplus.client.particles.YellowBubbleParticle;
import me.itzme1on.alcocraftplus.client.renderer.BlockEntityRenderers;
import me.itzme1on.alcocraftplus.client.renderer.BlockRenderTypes;
import me.itzme1on.alcocraftplus.core.registries.ParticlesRegistry;
import me.itzme1on.alcocraftplus.core.registries.ScreenHandlerRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

public final class AlcoCraftPlusFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MenuRegistry.registerScreenFactory(ScreenHandlerRegistry.KEG_MENU.get(), KegGui::new);

        BlockRenderTypes.init();
        BlockEntityRenderers.init();

        ParticleFactoryRegistry.getInstance().register(ParticlesRegistry.YELLOW_BUBBLE.get(), YellowBubbleParticle.Factory::new);
    }
}
