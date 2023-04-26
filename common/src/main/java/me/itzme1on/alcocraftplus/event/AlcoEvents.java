package me.itzme1on.alcocraftplus.event;

import dev.architectury.event.events.common.LifecycleEvent;
import me.itzme1on.alcocraftplus.trades.AlcoTrades;

public class AlcoEvents {
    public static void init() {
        LifecycleEvent.SETUP.register(AlcoEvents::onSetup);
    }

    public static void onSetup() {
        AlcoTrades.setup();
    }
}
