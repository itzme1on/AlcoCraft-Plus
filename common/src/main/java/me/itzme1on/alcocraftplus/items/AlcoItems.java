package me.itzme1on.alcocraftplus.items;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.blocks.AlcoBlocks;
import me.itzme1on.alcocraftplus.misc.AlcoBeerProperties;
import me.itzme1on.alcocraftplus.misc.AlcoTabGroup;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;

public class AlcoItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(AlcoCraftPlus.MOD_ID, Registry.ITEM_REGISTRY);

    public static final RegistrySupplier<Item> HOP = ITEMS.register("hop", () ->
            new Item(new Item.Properties()
                    .food(AlcoBeerProperties.HOP)
                    .tab(AlcoTabGroup.ALCOCRAFTPLUS)));

    public static final RegistrySupplier<Item> HOP_SEEDS = ITEMS.register("hop_seeds", () ->
            new ItemNameBlockItem(AlcoBlocks.HOP.get(), new Item.Properties()
                    .tab(AlcoTabGroup.ALCOCRAFTPLUS)));

    public static final RegistrySupplier<Item> DRY_SEEDS = ITEMS.register("dry_seeds", () ->
            new Item(new Item.Properties()
                    .tab(AlcoTabGroup.ALCOCRAFTPLUS)));

    public static final RegistrySupplier<Item> MUG = ITEMS.register("mug", () ->
            new MugItem(AlcoBlocks.MUG.get(), new Item.Properties()
                    .tab(AlcoTabGroup.ALCOCRAFTPLUS)));

    public static final RegistrySupplier<Item> CHORUS_ALE = ITEMS.register("chorus_ale", () ->
            new MugItem(AlcoBlocks.CHORUS_ALE.get(), new Item.Properties()
                    .tab(AlcoTabGroup.ALCOCRAFTPLUS)
                    .stacksTo(16)
                    .food(AlcoBeerProperties.CHORUS_ALE)));

    public static final RegistrySupplier<Item> DIGGER_BITTER = ITEMS.register("digger_bitter", () ->
            new MugItem(AlcoBlocks.DIGGER_BITTER.get(), new Item.Properties()
                    .tab(AlcoTabGroup.ALCOCRAFTPLUS)
                    .stacksTo(16)
                    .food(AlcoBeerProperties.DIGGER_BITTER)));

    public static final RegistrySupplier<Item> DROWNED_ALE = ITEMS.register("drowned_ale", () ->
            new MugItem(AlcoBlocks.DROWNED_ALE.get(), new Item.Properties()
                    .tab(AlcoTabGroup.ALCOCRAFTPLUS)
                    .stacksTo(16)
                    .food(AlcoBeerProperties.DROWNED_ALE)));

    public static final RegistrySupplier<Item> ICE_BEER = ITEMS.register("ice_beer", () ->
            new MugItem(AlcoBlocks.ICE_BEER.get(), new Item.Properties()
                    .tab(AlcoTabGroup.ALCOCRAFTPLUS)
                    .stacksTo(16)
                    .food(AlcoBeerProperties.ICE_BEER)));

    public static final RegistrySupplier<Item> KVASS = ITEMS.register("kvass", () ->
            new MugItem(AlcoBlocks.KVASS.get(), new Item.Properties()
                    .tab(AlcoTabGroup.ALCOCRAFTPLUS)
                    .stacksTo(16)
                    .food(AlcoBeerProperties.KVASS)));

    public static final RegistrySupplier<Item> LEPRECHAUN_CIDER = ITEMS.register("leprechaun_cider", () ->
            new MugItem(AlcoBlocks.LEPRECHAUN_CIDER.get(), new Item.Properties()
                    .tab(AlcoTabGroup.ALCOCRAFTPLUS)
                    .stacksTo(16)
                    .food(AlcoBeerProperties.LEPRECHAUN_CIDER)));

    public static final RegistrySupplier<Item> MAGNET_PILSNER = ITEMS.register("magnet_pilsner", () ->
            new MugItem(AlcoBlocks.MAGNET_PILSNER.get(), new Item.Properties()
                    .tab(AlcoTabGroup.ALCOCRAFTPLUS)
                    .stacksTo(16)
                    .food(AlcoBeerProperties.MAGNET_PILSNER)));

    public static final RegistrySupplier<Item> NETHER_PORTER = ITEMS.register("nether_porter", () ->
            new MugItem(AlcoBlocks.NETHER_PORTER.get(), new Item.Properties()
                    .tab(AlcoTabGroup.ALCOCRAFTPLUS)
                    .stacksTo(16)
                    .food(AlcoBeerProperties.NETHER_PORTER)));

    public static final RegistrySupplier<Item> NETHER_STAR_LAGER = ITEMS.register("nether_star_lager", () ->
            new MugItem(AlcoBlocks.NETHER_STAR_LAGER.get(), new Item.Properties()
                    .tab(AlcoTabGroup.ALCOCRAFTPLUS)
                    .stacksTo(16)
                    .food(AlcoBeerProperties.NETHER_STAR_LAGER)));

    public static final RegistrySupplier<Item> NIGHT_RAUCH = ITEMS.register("night_rauch", () ->
            new MugItem(AlcoBlocks.NIGHT_RAUCH.get(), new Item.Properties()
                    .tab(AlcoTabGroup.ALCOCRAFTPLUS)
                    .stacksTo(16)
                    .food(AlcoBeerProperties.NIGHT_RAUCH)));

    public static final RegistrySupplier<Item> SUN_PALE_ALE = ITEMS.register("sun_pale_ale", () ->
            new MugItem(AlcoBlocks.SUN_PALE_ALE.get(), new Item.Properties()
                    .tab(AlcoTabGroup.ALCOCRAFTPLUS)
                    .stacksTo(16)
                    .food(AlcoBeerProperties.SUN_PALE_ALE)));

    public static final RegistrySupplier<Item> WITHER_STOUT = ITEMS.register("wither_stout", () ->
            new MugItem(AlcoBlocks.WITHER_STOUT.get(), new Item.Properties()
                    .tab(AlcoTabGroup.ALCOCRAFTPLUS)
                    .stacksTo(16)
                    .food(AlcoBeerProperties.WITHER_STOUT)));

    public static void init() {
        ITEMS.register();
    }
}
