package net.hadrus.alcocraft.items;

import net.hadrus.alcocraft.AlcoCraft;
import net.hadrus.alcocraft.blocks.AlcoBlocks;
import net.hadrus.alcocraft.misc.AlcoBeerProperties;
import net.hadrus.alcocraft.misc.AlcoTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AlcoItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, AlcoCraft.MOD_ID);

    public static final RegistryObject<Item> HOP = ITEMS.register("hop",
            () -> new Item(new Item.Properties().food(AlcoBeerProperties.HOP).tab(AlcoTab.ALCO_TAB)));

    public static final RegistryObject<Item> HOP_SEEDS = ITEMS.register("hop_seeds",
            () -> new ItemNameBlockItem(AlcoBlocks.HOP_BLOCK.get(), new Item.Properties().tab(AlcoTab.ALCO_TAB)));

    public static final RegistryObject<Item> DRY_SEEDS = ITEMS.register("dry_seeds",
            () -> new Item(new Item.Properties().tab(AlcoTab.ALCO_TAB)));

    public static final RegistryObject<Item> SPRUCE_EMPTY_MUG = ITEMS.register("spruce_mug_empty",
            () -> new MugItem(AlcoBlocks.SPRUCE_MUG_BLOCK_EMPTY.get(),new Item.Properties().tab(AlcoTab.ALCO_TAB)));

    public static final RegistryObject<Item> SPRUCE_MUG_SUN_PALE_ALE = ITEMS.register("spruce_mug_sun_pale_ale",
            () -> new MugItem(AlcoBlocks.SPRUCE_MUG_BLOCK_SUN_PALE_ALE.get(),new Item.Properties().food(AlcoBeerProperties.SUN_PALE_ALE).stacksTo(16).tab(AlcoTab.ALCO_TAB)));
    public static final RegistryObject<Item> SPRUCE_MUG_DIGGER_BITTER = ITEMS.register("spruce_mug_digger_bitter",
            () -> new MugItem(AlcoBlocks.SPRUCE_MUG_BLOCK_DIGGER_BITTER.get(),new Item.Properties().food(AlcoBeerProperties.DIGGER_BITTER).stacksTo(16).tab(AlcoTab.ALCO_TAB)));
    public static final RegistryObject<Item> SPRUCE_MUG_NETHER_PORTER = ITEMS.register("spruce_mug_nether_porter",
            () -> new MugItem(AlcoBlocks.SPRUCE_MUG_BLOCK_NETHER_PORTER.get(),new Item.Properties().food(AlcoBeerProperties.NETHER_PORTER).stacksTo(16).tab(AlcoTab.ALCO_TAB)));
    public static final RegistryObject<Item> SPRUCE_MUG_WITHER_STOUT = ITEMS.register("spruce_mug_wither_stout",
            () -> new MugItem(AlcoBlocks.SPRUCE_MUG_BLOCK_WITHER_STOUT.get(),new Item.Properties().food(AlcoBeerProperties.WITHER_STOUT).stacksTo(16).tab(AlcoTab.ALCO_TAB)));
    public static final RegistryObject<Item> SPRUCE_MUG_MAGNET_PILSNER = ITEMS.register("spruce_mug_magnet_pilsner",
            () -> new MugItem(AlcoBlocks.SPRUCE_MUG_BLOCK_MAGNET_PILSNER.get(),new Item.Properties().food(AlcoBeerProperties.MAGNET_PILSNER).stacksTo(16).tab(AlcoTab.ALCO_TAB)));
    public static final RegistryObject<Item> SPRUCE_MUG_DROWNED_ALE = ITEMS.register("spruce_mug_drowned_ale",
            () -> new MugItem(AlcoBlocks.SPRUCE_MUG_BLOCK_DROWNED_ALE.get(),new Item.Properties().food(AlcoBeerProperties.DROWNED_ALE).stacksTo(16).tab(AlcoTab.ALCO_TAB)));
    public static final RegistryObject<Item> SPRUCE_MUG_NIGHT_RAUCH = ITEMS.register("spruce_mug_night_rauch",
            () -> new MugItem(AlcoBlocks.SPRUCE_MUG_BLOCK_NIGHT_RAUCH.get(),new Item.Properties().food(AlcoBeerProperties.NIGHT_RAUCH).stacksTo(16).tab(AlcoTab.ALCO_TAB)));
    public static final RegistryObject<Item> SPRUCE_MUG_ICE_BEER = ITEMS.register("spruce_mug_ice_beer",
            () -> new MugItem(AlcoBlocks.SPRUCE_MUG_BLOCK_ICE_BEER.get(),new Item.Properties().food(AlcoBeerProperties.ICE_BEER).stacksTo(16).tab(AlcoTab.ALCO_TAB)));
    public static final RegistryObject<Item> SPRUCE_MUG_KVASS = ITEMS.register("spruce_mug_kvass",
            () -> new MugItem(AlcoBlocks.SPRUCE_MUG_BLOCK_KVASS.get(),new Item.Properties().food(AlcoBeerProperties.KVASS).stacksTo(16).tab(AlcoTab.ALCO_TAB)));
    public static final RegistryObject<Item> SPRUCE_MUG_LEPRECHAUN_CIDER = ITEMS.register("spruce_mug_leprechaun_cider",
            () -> new MugItem(AlcoBlocks.SPRUCE_MUG_BLOCK_LEPRECHAUN_CIDER.get(),new Item.Properties().food(AlcoBeerProperties.LEPRECHAUN_CIDER).stacksTo(16).tab(AlcoTab.ALCO_TAB)));
    public static final RegistryObject<Item> SPRUCE_MUG_CHORUS_ALE = ITEMS.register("spruce_mug_chorus_ale",
            () -> new MugItem(AlcoBlocks.SPRUCE_MUG_BLOCK_CHORUS_ALE.get(),new Item.Properties().food(AlcoBeerProperties.CHORUS_ALE).stacksTo(16).tab(AlcoTab.ALCO_TAB)));
    public static final RegistryObject<Item> SPRUCE_MUG_NETHER_STAR_LAGER = ITEMS.register("spruce_mug_nether_star_lager",
            () -> new MugItem(AlcoBlocks.SPRUCE_MUG_BLOCK_NETHER_STAR_LAGER.get(),new Item.Properties().food(AlcoBeerProperties.NETHER_STAR_LAGER).stacksTo(16).tab(AlcoTab.ALCO_TAB)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
