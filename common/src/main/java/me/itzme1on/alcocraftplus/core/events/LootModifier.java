package me.itzme1on.alcocraftplus.core.events;

import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.core.registries.ItemsRegistry;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class LootModifier {
    public static void modifyChestLootTable(Identifier id, LootEvent.LootTableModificationContext context) {
        String prefix = "minecraft:chests/";
        String name = id.toString();

        if (name.startsWith(prefix)) {
            String file = name.substring(name.indexOf(prefix) + prefix.length());

            switch (file) {
                case "abandoned_mineshaft",
                     "bastion_treasure",
                     "end_city_treasure",
                     "igloo_chest",
                     "nether_bridge",
                     "pillager_outpost",
                     "ruined_portal",
                     "shipwreck_supply",
                     "simple_dungeon",
                     "underwater_ruin_big",
                     "underwater_ruin_small" -> context.addPool(getPool(file));
                default -> {
                }
            }
        }
    }

    public static void modifyGrassLootTable(LootEvent.LootTableModificationContext context) {
        LootPool.Builder builder = LootPool.lootPool()
                .when(LootItemRandomChanceCondition.randomChance(0.07f))
                .add(LootItem.lootTableItem(ItemsRegistry.HOP_SEEDS.get()))
                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)));

        context.addPool(builder.build());
    }

    public static LootPool getPool(String entryName) {
        return LootPool.lootPool().add(getPoolEntry(entryName)).build();
    }

    @SuppressWarnings("rawtypes")
    private static LootPoolEntryContainer.Builder getPoolEntry(String name) {
        Identifier table = new Identifier(AlcoCraftPlus.MOD_ID, "chests/" + name);

        return LootTableReference.lootTableReference(table);
    }

    public static void modifyLootTable(Identifier id, LootEvent.LootTableModificationContext context) {
        if (id.equals(new Identifier("minecraft", "blocks/grass"))) {
            modifyGrassLootTable(context);
        } else {
            modifyChestLootTable(id, context);
        }
    }
}