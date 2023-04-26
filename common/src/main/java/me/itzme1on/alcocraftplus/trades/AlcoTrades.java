package me.itzme1on.alcocraftplus.trades;

import dev.architectury.registry.level.entity.trade.SimpleTrade;
import dev.architectury.registry.level.entity.trade.TradeRegistry;
import me.itzme1on.alcocraftplus.items.AlcoItems;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class AlcoTrades {
    public static VillagerTrades.ItemListing[] FARMER_TRADES;

    public static void setup() {
        FARMER_TRADES = new VillagerTrades.ItemListing[] {
                new SimpleTrade(Items.EMERALD.getDefaultInstance(), ItemStack.EMPTY, AlcoItems.HOP_SEEDS.get().getDefaultInstance(), 100, 0, 1)
        };

        TradeRegistry.registerVillagerTrade(VillagerProfession.FARMER, 1, AlcoTrades.FARMER_TRADES);
    }
}
