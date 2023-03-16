package me.itzme1on.alcocraftplus.particles;

import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AlcoParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, AlcoCraftPlus.MOD_ID);

    public static final RegistryObject<SimpleParticleType> YELLOW_BUBBLES = PARTICLE_TYPES.register("yellow_bubbles",
            () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
