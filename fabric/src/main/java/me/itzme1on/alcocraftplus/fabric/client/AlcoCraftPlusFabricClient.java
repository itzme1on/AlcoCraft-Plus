package me.itzme1on.alcocraftplus.fabric.client;

import me.itzme1on.alcocraftplus.client.AlcoCraftPlusClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

public final class AlcoCraftPlusFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        AlcoCraftPlusClient.initClient();

        AlcoCraftPlusClient.registerParticles(
                ((particleType, spriteSetFunction) ->
                        ParticleFactoryRegistry.getInstance().register(particleType, (ParticleFactoryRegistry.PendingParticleFactory) spriteSetFunction::apply))
        );

        registerScreenFactory();

        AlcoCraftPlusClient.onPostInit();
    }

    public static void registerScreenFactory() {
        AlcoCraftPlusClient.registerScreenFactory();
    }
}
