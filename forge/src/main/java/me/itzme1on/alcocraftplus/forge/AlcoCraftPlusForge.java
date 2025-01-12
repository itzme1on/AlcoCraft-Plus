package me.itzme1on.alcocraftplus.forge;

import dev.architectury.platform.forge.EventBuses;
import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(AlcoCraftPlus.MOD_ID)
public final class AlcoCraftPlusForge {
    public AlcoCraftPlusForge() {
        EventBuses.registerModEventBus(AlcoCraftPlus.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        AlcoCraftPlus.init();
    }
}
