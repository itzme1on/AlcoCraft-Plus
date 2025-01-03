package me.itzme1on.alcocraftplus.forge.client;

import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.client.AlcoCraftPlusClient;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = AlcoCraftPlus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class AlcoCraftPlusForgeClient {
    @SubscribeEvent
    public static void setupClient(FMLClientSetupEvent event) {
        AlcoCraftPlusClient.initClient();

        registerScreenFactory();

        AlcoCraftPlusClient.onPostInit();
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        AlcoCraftPlusClient.registerParticles(
                (particleType, spriteSetFunction) -> event.registerSpriteSet(particleType, (ParticleEngine.SpriteParticleRegistration) spriteSetFunction::apply));
    }

    public static void registerScreenFactory() {
        AlcoCraftPlusClient.registerScreenFactory();
    }
}
