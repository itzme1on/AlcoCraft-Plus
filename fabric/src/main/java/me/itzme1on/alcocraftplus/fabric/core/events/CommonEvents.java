package me.itzme1on.alcocraftplus.fabric.core.events;

import dev.architectury.event.events.common.LootEvent;
import me.itzme1on.alcocraftplus.fabric.core.loot.LootModifier;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.LootDataManager;

public class CommonEvents {
    public static void register() {
        LootEvent.MODIFY_LOOT_TABLE.register(CommonEvents::onModifyLootTable);
    }

    public static void onModifyLootTable(LootDataManager lootManager, Identifier id, LootEvent.LootTableModificationContext context, boolean builtin) {
        LootModifier.modifyLootTable(id, context);
    }
}
