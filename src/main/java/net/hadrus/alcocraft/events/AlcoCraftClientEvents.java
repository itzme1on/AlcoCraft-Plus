package net.hadrus.alcocraft.events;

import net.hadrus.alcocraft.AlcoCraftPlus;
import net.hadrus.alcocraft.particles.AlcoParticles;
import net.hadrus.alcocraft.particles.YellowBubbleParticles;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AlcoCraftPlus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class AlcoCraftClientEvents {
    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        event.register(AlcoParticles.YELLOW_BUBBLES.get(), YellowBubbleParticles.Provider::new);
    }
}
