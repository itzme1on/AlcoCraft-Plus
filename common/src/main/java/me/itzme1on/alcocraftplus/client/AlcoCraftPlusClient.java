package me.itzme1on.alcocraftplus.client;

import dev.architectury.registry.menu.MenuRegistry;
import me.itzme1on.alcocraftplus.client.gui.KegGui;
import me.itzme1on.alcocraftplus.client.particles.YellowBubbleParticle;
import me.itzme1on.alcocraftplus.client.renderer.BlockEntityRenderer;
import me.itzme1on.alcocraftplus.client.renderer.BlockRenderer;
import me.itzme1on.alcocraftplus.core.registries.ScreenHandlerRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

import java.util.function.BiConsumer;
import java.util.function.Function;

import static me.itzme1on.alcocraftplus.core.registries.ParticlesRegistry.YELLOW_BUBBLE;

@Environment(EnvType.CLIENT)
public class AlcoCraftPlusClient {
    public static void initClient() {
    }

    public static void onPostInit() {
        BlockRenderer.init();
        BlockEntityRenderer.init();
    }

    public static void registerParticles(BiConsumer<ParticleType<? extends ParticleOptions>, Function<SpriteSet, ? extends ParticleProvider<? extends ParticleOptions>>> spriteProvider) {
        spriteProvider.accept(YELLOW_BUBBLE.get(), YellowBubbleParticle.Factory::new);
    }

    public static void registerScreenFactory() {
        MenuRegistry.registerScreenFactory(ScreenHandlerRegistry.KEG_MENU.get(), KegGui::new);
    }
}
