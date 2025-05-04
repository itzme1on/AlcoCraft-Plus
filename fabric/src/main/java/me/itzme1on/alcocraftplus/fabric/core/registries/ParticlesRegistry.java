package me.itzme1on.alcocraftplus.fabric.core.registries;

import dev.architectury.platform.Platform;
import dev.architectury.registry.client.particle.ParticleProviderRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.architectury.utils.Env;
import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.fabric.client.particles.YellowBubbleParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;

public class ParticlesRegistry {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(AlcoCraftPlus.MOD_ID, Registries.PARTICLE_TYPE);

    public static final RegistrySupplier<SimpleParticleType> YELLOW_BUBBLE = PARTICLES.register("yellow_bubble",
            () -> new SimpleParticleType(true) {
            });

    public static void register() {
        PARTICLES.register();

        if (Platform.getEnvironment() == Env.CLIENT)
            ParticleProviderRegistry.register(YELLOW_BUBBLE, YellowBubbleParticle.Factory::new);
    }
}
