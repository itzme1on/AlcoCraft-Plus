package me.itzme1on.alcocraftplus.core.events;

import dev.architectury.event.events.common.LootEvent;
import me.itzme1on.alcocraftplus.core.loot.LootModifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public class CommonEvents {
    private CommonEvents() {
    }

    public static void register() {
        LootEvent.MODIFY_LOOT_TABLE.register(CommonEvents::onModifyLootTable);
    }

    public static void onModifyLootTable(ResourceKey<LootTable> key, LootEvent.LootTableModificationContext context, boolean builtin) {
        LootModifier.injectLoot(key, context);
    }
}
