package me.itzme1on.alcocraftplus.fabric;

import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import net.fabricmc.api.ModInitializer;

public class AlcoCraftFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        AlcoCraftPlus.init();
    }
}