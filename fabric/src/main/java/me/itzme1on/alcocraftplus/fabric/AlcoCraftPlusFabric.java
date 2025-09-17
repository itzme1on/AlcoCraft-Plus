package me.itzme1on.alcocraftplus.fabric;

import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.fabric.registrations.FabricBuiltinPackRegistration;
import net.fabricmc.api.ModInitializer;

public final class AlcoCraftPlusFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        AlcoCraftPlus.init();
        
        FabricBuiltinPackRegistration.registerBuiltinPacks();
    }
}
