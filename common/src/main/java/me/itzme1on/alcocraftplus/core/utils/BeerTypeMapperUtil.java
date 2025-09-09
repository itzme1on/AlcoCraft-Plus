package me.itzme1on.alcocraftplus.core.utils;

import me.itzme1on.alcocraftplus.core.registries.ItemsRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class BeerTypeMapperUtil {
    private static final Map<Item, Integer> BEER_TYPE_MAP;
    private static final Map<Integer, ItemStack> BEER_ITEM_MAP;

    static {
        Map<Item, Integer> type = new HashMap<>();
        type.put(ItemsRegistry.SUN_PALE_ALE.get(), 1);
        type.put(ItemsRegistry.DIGGER_BITTER.get(), 2);
        type.put(ItemsRegistry.NETHER_PORTER.get(), 3);
        type.put(ItemsRegistry.WITHER_STOUT.get(), 4);
        type.put(ItemsRegistry.MAGNET_PILSNER.get(), 5);
        type.put(ItemsRegistry.DROWNED_ALE.get(), 6);
        type.put(ItemsRegistry.NIGHT_RAUCH.get(), 7);
        type.put(ItemsRegistry.ICE_BEER.get(), 8);
        type.put(ItemsRegistry.KVASS.get(), 9);
        type.put(ItemsRegistry.LEPRECHAUN_CIDER.get(), 10);
        type.put(ItemsRegistry.CHORUS_ALE.get(), 11);
        type.put(ItemsRegistry.NETHER_STAR_LAGER.get(), 12);

        BEER_TYPE_MAP = Collections.unmodifiableMap(type);

        Map<Integer, ItemStack> items = new HashMap<>();
        items.put(1, new ItemStack(ItemsRegistry.SUN_PALE_ALE.get()));
        items.put(2, new ItemStack(ItemsRegistry.DIGGER_BITTER.get()));
        items.put(3, new ItemStack(ItemsRegistry.NETHER_PORTER.get()));
        items.put(4, new ItemStack(ItemsRegistry.WITHER_STOUT.get()));
        items.put(5, new ItemStack(ItemsRegistry.MAGNET_PILSNER.get()));
        items.put(6, new ItemStack(ItemsRegistry.DROWNED_ALE.get()));
        items.put(7, new ItemStack(ItemsRegistry.NIGHT_RAUCH.get()));
        items.put(8, new ItemStack(ItemsRegistry.ICE_BEER.get()));
        items.put(9, new ItemStack(ItemsRegistry.KVASS.get()));
        items.put(10, new ItemStack(ItemsRegistry.LEPRECHAUN_CIDER.get()));
        items.put(11, new ItemStack(ItemsRegistry.CHORUS_ALE.get()));
        items.put(12, new ItemStack(ItemsRegistry.NETHER_STAR_LAGER.get()));

        BEER_ITEM_MAP = Collections.unmodifiableMap(items);
    }

    private BeerTypeMapperUtil() {
    }

    public static int getBeerType(Item item) {
        return BEER_TYPE_MAP.getOrDefault(item, 0);
    }

    public static ItemStack getBeerStack(int beerType) {
        return BEER_ITEM_MAP.getOrDefault(beerType, ItemStack.EMPTY);
    }
}
