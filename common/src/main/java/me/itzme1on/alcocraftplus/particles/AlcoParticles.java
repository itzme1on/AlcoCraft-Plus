package me.itzme1on.alcocraftplus.particles;

import dev.architectury.platform.Platform;
import dev.architectury.registry.client.particle.ParticleProviderRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.architectury.utils.Env;
import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

public class AlcoParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(AlcoCraftPlus.MOD_ID, Registry.PARTICLE_TYPE_REGISTRY);

    public static final RegistrySupplier<SimpleParticleType> YELLOW_BUBBLE = PARTICLE_TYPES.register("yellow_bubble",
            () -> new SimpleParticleType(false) {});

    public static void init() {
        PARTICLE_TYPES.register();
        if (Platform.getEnvironment() == Env.CLIENT)
            ParticleProviderRegistry.register(YELLOW_BUBBLE, YellowBubbleParticle.Provider::new);
    }
}
