package me.itzme1on.alcocraftplus.neoforge; // Correct package

import dev.architectury.platform.neoforge.EventBuses; // Correct import
import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import net.neoforged.bus.api.IEventBus; // Correct import
import net.neoforged.fml.common.Mod; // Correct import

@Mod(AlcoCraftPlus.MOD_ID)
public final class AlcoCraftPlusNeoForge {
    public AlcoCraftPlusNeoForge(IEventBus modEventBus) { // Correct constructor injection
        EventBuses.registerModEventBus(AlcoCraftPlus.MOD_ID, modEventBus); // Correct registration

        AlcoCraftPlus.init(); // Correct common init call
    }
}