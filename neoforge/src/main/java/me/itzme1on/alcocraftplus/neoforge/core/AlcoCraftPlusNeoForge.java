package me.itzme1on.alcocraftplus.neoforge.core;

import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.neoforge.client.AlcoCraftPlusNeoForgeClient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;

@Mod(AlcoCraftPlus.MOD_ID)
public final class AlcoCraftPlusNeoForge {
    public AlcoCraftPlusNeoForge() {
        AlcoCraftPlus.init();

        IEventBus modBus = ModLoadingContext.get().getActiveContainer().getEventBus();
        modBus.addListener(AlcoCraftPlusNeoForgeClient::onClientSetup);
        modBus.addListener(AlcoCraftPlusNeoForgeClient::registerParticles);
        modBus.addListener(AlcoCraftPlusNeoForgeClient::registerScreens);
    }
}
