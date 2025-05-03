package me.itzme1on.alcocraftplus.neoforge.core.utils;

import me.itzme1on.alcocraftplus.neoforge.core.registries.ItemsRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class BeerTypeMapperUtil {
    private static final Map<Item, Integer> BEER_TYPE_MAP = new HashMap<>();
    private static final Map<Integer, ItemStack> BEER_ITEM_MAP = new HashMap<>();

    static {
        BEER_TYPE_MAP.put(ItemsRegistry.SUN_PALE_ALE.get(), 1);
        BEER_TYPE_MAP.put(ItemsRegistry.DIGGER_BITTER.get(), 2);
        BEER_TYPE_MAP.put(ItemsRegistry.NETHER_PORTER.get(), 3);
        BEER_TYPE_MAP.put(ItemsRegistry.WITHER_STOUT.get(), 4);
        BEER_TYPE_MAP.put(ItemsRegistry.MAGNET_PILSNER.get(), 5);
        BEER_TYPE_MAP.put(ItemsRegistry.DROWNED_ALE.get(), 6);
        BEER_TYPE_MAP.put(ItemsRegistry.NIGHT_RAUCH.get(), 7);
        BEER_TYPE_MAP.put(ItemsRegistry.ICE_BEER.get(), 8);
        BEER_TYPE_MAP.put(ItemsRegistry.KVASS.get(), 9);
        BEER_TYPE_MAP.put(ItemsRegistry.LEPRECHAUN_CIDER.get(), 10);
        BEER_TYPE_MAP.put(ItemsRegistry.CHORUS_ALE.get(), 11);
        BEER_TYPE_MAP.put(ItemsRegistry.NETHER_STAR_LAGER.get(), 12);

        BEER_ITEM_MAP.put(1, new ItemStack(ItemsRegistry.SUN_PALE_ALE.get()));
        BEER_ITEM_MAP.put(2, new ItemStack(ItemsRegistry.DIGGER_BITTER.get()));
        BEER_ITEM_MAP.put(3, new ItemStack(ItemsRegistry.NETHER_PORTER.get()));
        BEER_ITEM_MAP.put(4, new ItemStack(ItemsRegistry.WITHER_STOUT.get()));
        BEER_ITEM_MAP.put(5, new ItemStack(ItemsRegistry.MAGNET_PILSNER.get()));
        BEER_ITEM_MAP.put(6, new ItemStack(ItemsRegistry.DROWNED_ALE.get()));
        BEER_ITEM_MAP.put(7, new ItemStack(ItemsRegistry.NIGHT_RAUCH.get()));
        BEER_ITEM_MAP.put(8, new ItemStack(ItemsRegistry.ICE_BEER.get()));
        BEER_ITEM_MAP.put(9, new ItemStack(ItemsRegistry.KVASS.get()));
        BEER_ITEM_MAP.put(10, new ItemStack(ItemsRegistry.LEPRECHAUN_CIDER.get()));
        BEER_ITEM_MAP.put(11, new ItemStack(ItemsRegistry.CHORUS_ALE.get()));
        BEER_ITEM_MAP.put(12, new ItemStack(ItemsRegistry.NETHER_STAR_LAGER.get()));
    }

    public static int getBeerType(Item item) {
        return BEER_TYPE_MAP.getOrDefault(item, 0);
    }

    public static ItemStack getBeerStack(int beerType) {
        return BEER_ITEM_MAP.getOrDefault(beerType, ItemStack.EMPTY);
    }
}