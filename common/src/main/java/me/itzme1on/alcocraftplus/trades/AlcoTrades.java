package me.itzme1on.alcocraftplus.trades;

import dev.architectury.registry.level.entity.trade.SimpleTrade;
import dev.architectury.registry.level.entity.trade.TradeRegistry;
import me.itzme1on.alcocraftplus.items.AlcoItems;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class AlcoTrades {
    public static void init() {
        for (var villagerProfession : Registry.VILLAGER_PROFESSION)
            if (villagerProfession.equals(VillagerProfession.FARMER))
                TradeRegistry.registerVillagerTrade(villagerProfession, 1, createTrades());

        TradeRegistry.registerTradeForWanderingTrader(false, AlcoTrades.createTrades());
    }

    private static VillagerTrades.ItemListing[] createTrades() {
        var trade = new SimpleTrade(Items.EMERALD.getDefaultInstance(), ItemStack.EMPTY, AlcoItems.HOP_SEEDS.get().getDefaultInstance(), 1, 0, 1.0F);

        return new VillagerTrades.ItemListing[] { trade };
    }
}
