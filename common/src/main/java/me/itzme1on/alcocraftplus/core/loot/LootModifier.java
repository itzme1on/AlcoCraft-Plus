package me.itzme1on.alcocraftplus.core.loot;

import dev.architectury.event.events.common.LootEvent;
import me.itzme1on.alcocraftplus.core.registries.ItemsRegistry;
import me.itzme1on.alcocraftplus.core.utils.IdentifierUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class LootModifier {
    private LootModifier() {
    }

    public static void injectLoot(ResourceKey<LootTable> key, LootEvent.LootTableModificationContext context) {
        var id = key.location();

        String namespace = id.getNamespace();
        String path = id.getPath();

        if (namespace.equals("minecraft")) {
            switch (path) {
                case "blocks/short_grass", "blocks/tall_grass" -> modifyGrassLootTable(context);

                case "chests/abandoned_mineshaft",
                     "chests/bastion_treasure",
                     "chests/end_city_treasure",
                     "chests/igloo_chest",
                     "chests/nether_bridge",
                     "chests/pillager_outpost",
                     "chests/ruined_portal",
                     "chests/shipwreck_supply",
                     "chests/simple_dungeon",
                     "chests/underwater_ruin_big",
                     "chests/underwater_ruin_small" -> context.addPool(getPool(path.substring("chests/".length())));

                default -> {
                }
            }
        }

    }

    public static void modifyGrassLootTable(LootEvent.LootTableModificationContext context) {
        LootPool.Builder builder =
                LootPool.lootPool()
                        .when(LootItemRandomChanceCondition.randomChance(0.07f))
                        .add(LootItem.lootTableItem(ItemsRegistry.HOP_SEEDS.get()))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)));

        context.addPool(builder);
    }

    public static LootPool.Builder getPool(String entryName) {
        return LootPool.lootPool().add(getPoolEntry(entryName));
    }

    private static LootPoolEntryContainer.Builder<?> getPoolEntry(String name) {
        ResourceKey<LootTable> table =
                ResourceKey.create(Registries.LOOT_TABLE, IdentifierUtil.of("chests/" + name));

        return NestedLootTable.lootTableReference(table);
    }
}
