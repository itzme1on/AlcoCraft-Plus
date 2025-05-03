package me.itzme1on.alcocraftplus.neoforge.core.registries;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.neoforge.core.items.MugItem;
import me.itzme1on.alcocraftplus.neoforge.core.misc.BeerProperties;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;

public class ItemsRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(AlcoCraftPlus.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> HOP = ITEMS.register("hop", () ->
            new Item(new Item.Properties()
                    .food(BeerProperties.HOP)));

    public static final RegistrySupplier<Item> HOP_SEEDS = ITEMS.register("hop_seeds", () ->
            new ItemNameBlockItem(BlocksRegistry.HOP.get(), new Item.Properties()));

    public static final RegistrySupplier<Item> DRY_SEEDS = ITEMS.register("dry_seeds", () ->
            new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> MUG = ITEMS.register("mug", () ->
            new MugItem(BlocksRegistry.MUG.get(), new Item.Properties()));

    public static final RegistrySupplier<Item> KVASS = ITEMS.register("kvass", () ->
            new MugItem(BlocksRegistry.KVASS.get(), new Item.Properties()
                    .stacksTo(16)
                    .food(BeerProperties.KVASS)));

    public static final RegistrySupplier<Item> CHORUS_ALE = ITEMS.register("chorus_ale", () ->
            new MugItem(BlocksRegistry.CHORUS_ALE.get(), new Item.Properties()
                    .stacksTo(16)
                    .food(BeerProperties.CHORUS_ALE)));

    public static final RegistrySupplier<Item> DIGGER_BITTER = ITEMS.register("digger_bitter", () ->
            new MugItem(BlocksRegistry.DIGGER_BITTER.get(), new Item.Properties()
                    .stacksTo(16)
                    .food(BeerProperties.DIGGER_BITTER)));

    public static final RegistrySupplier<Item> DROWNED_ALE = ITEMS.register("drowned_ale", () ->
            new MugItem(BlocksRegistry.DROWNED_ALE.get(), new Item.Properties()
                    .stacksTo(16)
                    .food(BeerProperties.DROWNED_ALE)));

    public static final RegistrySupplier<Item> ICE_BEER = ITEMS.register("ice_beer", () ->
            new MugItem(BlocksRegistry.ICE_BEER.get(), new Item.Properties()
                    .stacksTo(16)
                    .food(BeerProperties.ICE_BEER)));

    public static final RegistrySupplier<Item> LEPRECHAUN_CIDER = ITEMS.register("leprechaun_cider", () ->
            new MugItem(BlocksRegistry.LEPRECHAUN_CIDER.get(), new Item.Properties()
                    .stacksTo(16)
                    .food(BeerProperties.LEPRECHAUN_CIDER)));

    public static final RegistrySupplier<Item> MAGNET_PILSNER = ITEMS.register("magnet_pilsner", () ->
            new MugItem(BlocksRegistry.MAGNET_PILSNER.get(), new Item.Properties()
                    .stacksTo(16)
                    .food(BeerProperties.MAGNET_PILSNER)));

    public static final RegistrySupplier<Item> NETHER_PORTER = ITEMS.register("nether_porter", () ->
            new MugItem(BlocksRegistry.NETHER_PORTER.get(), new Item.Properties()
                    .stacksTo(16)
                    .food(BeerProperties.NETHER_PORTER)));

    public static final RegistrySupplier<Item> NETHER_STAR_LAGER = ITEMS.register("nether_star_lager", () ->
            new MugItem(BlocksRegistry.NETHER_STAR_LAGER.get(), new Item.Properties()
                    .stacksTo(16)
                    .food(BeerProperties.NETHER_STAR_LAGER)));

    public static final RegistrySupplier<Item> NIGHT_RAUCH = ITEMS.register("night_rauch", () ->
            new MugItem(BlocksRegistry.NIGHT_RAUCH.get(), new Item.Properties()
                    .stacksTo(16)
                    .food(BeerProperties.NIGHT_RAUCH)));

    public static final RegistrySupplier<Item> SUN_PALE_ALE = ITEMS.register("sun_pale_ale", () ->
            new MugItem(BlocksRegistry.SUN_PALE_ALE.get(), new Item.Properties()
                    .stacksTo(16)
                    .food(BeerProperties.SUN_PALE_ALE)));

    public static final RegistrySupplier<Item> WITHER_STOUT = ITEMS.register("wither_stout", () ->
            new MugItem(BlocksRegistry.WITHER_STOUT.get(), new Item.Properties()
                    .stacksTo(16)
                    .food(BeerProperties.WITHER_STOUT)));

    public static void register() {
        ITEMS.register();
    }
}
