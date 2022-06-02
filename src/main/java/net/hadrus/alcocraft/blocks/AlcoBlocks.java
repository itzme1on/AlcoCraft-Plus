package net.hadrus.alcocraft.blocks;

import net.hadrus.alcocraft.AlcoCraft;
import net.hadrus.alcocraft.blocks.drinks.MugBlock;
import net.hadrus.alcocraft.blocks.drinks.beer.*;
import net.hadrus.alcocraft.blocks.plants.HopBlock;
import net.hadrus.alcocraft.blocks.plants.HopPlantBlock;
import net.hadrus.alcocraft.blocks.workstations.KegBlock;
import net.hadrus.alcocraft.items.AlcoItems;
import net.hadrus.alcocraft.misc.AlcoTab;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class AlcoBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, AlcoCraft.MOD_ID);

    public static final RegistryObject<Block> SPRUCE_KEG = registerBlock("spruce_keg",
            () -> new KegBlock(BlockBehaviour.Properties.copy(Blocks.BARREL).noOcclusion()), AlcoTab.ALCO_TAB);

    public static final RegistryObject<Block> HOP_PLANT_BLOCK = registerBlockWithoutItem("hop_plant_block",
            () -> new HopPlantBlock(BlockBehaviour.Properties.copy(Blocks.CAVE_VINES_PLANT).lightLevel(CaveVines.emission(0)).noOcclusion()));

    public static final RegistryObject<Block> HOP_BLOCK = registerBlockWithoutItem("hop_block",
            () -> new HopBlock(BlockBehaviour.Properties.copy(Blocks.CAVE_VINES).lightLevel(CaveVines.emission(0)).noOcclusion()));

    public static final RegistryObject<Block> SPRUCE_MUG_BLOCK_EMPTY = registerBlockWithoutItem("spruce_mug_block_empty",
            () -> new MugBlock(BlockBehaviour.Properties.copy(Blocks.BARREL).noOcclusion()));

    public static final RegistryObject<Block> SPRUCE_MUG_BLOCK_SUN_PALE_ALE = registerBlockWithoutItem("spruce_mug_block_sun_pale_ale",
            () -> new SunPaleAleMugBlock(BlockBehaviour.Properties.copy(Blocks.BARREL).noOcclusion()));
    public static final RegistryObject<Block> SPRUCE_MUG_BLOCK_DIGGER_BITTER = registerBlockWithoutItem("spruce_mug_block_digger_bitter",
            () -> new DiggerBitterMugBlock(BlockBehaviour.Properties.copy(Blocks.BARREL).noOcclusion()));
    public static final RegistryObject<Block> SPRUCE_MUG_BLOCK_NETHER_PORTER = registerBlockWithoutItem("spruce_mug_block_nether_porter",
            () -> new NetherPorterMugBlock(BlockBehaviour.Properties.copy(Blocks.BARREL).noOcclusion()));
    public static final RegistryObject<Block> SPRUCE_MUG_BLOCK_WITHER_STOUT = registerBlockWithoutItem("spruce_mug_block_wither_stout",
            () -> new WitherStoutMugBlock(BlockBehaviour.Properties.copy(Blocks.BARREL).noOcclusion()));
    public static final RegistryObject<Block> SPRUCE_MUG_BLOCK_MAGNET_PILSNER = registerBlockWithoutItem("spruce_mug_block_magnet_pilsner",
            () -> new MagnetPilsnerMugBlock(BlockBehaviour.Properties.copy(Blocks.BARREL).noOcclusion()));
    public static final RegistryObject<Block> SPRUCE_MUG_BLOCK_DROWNED_ALE = registerBlockWithoutItem("spruce_mug_block_drowned_ale",
            () -> new DrownedAleMugBlock(BlockBehaviour.Properties.copy(Blocks.BARREL).noOcclusion()));
    public static final RegistryObject<Block> SPRUCE_MUG_BLOCK_NIGHT_RAUCH = registerBlockWithoutItem("spruce_mug_block_night_rauch",
            () -> new NightRauchMugBlock(BlockBehaviour.Properties.copy(Blocks.BARREL).noOcclusion()));
    public static final RegistryObject<Block> SPRUCE_MUG_BLOCK_ICE_BEER = registerBlockWithoutItem("spruce_mug_block_ice_beer",
            () -> new IceBeerMugBlock(BlockBehaviour.Properties.copy(Blocks.BARREL).noOcclusion()));
    public static final RegistryObject<Block> SPRUCE_MUG_BLOCK_KVASS = registerBlockWithoutItem("spruce_mug_block_kvass",
            () -> new KvassMugBlock(BlockBehaviour.Properties.copy(Blocks.BARREL).noOcclusion()));
    public static final RegistryObject<Block> SPRUCE_MUG_BLOCK_LEPRECHAUN_CIDER = registerBlockWithoutItem("spruce_mug_block_leprechaun_cider",
            () -> new LeprechaunCiderMugBlock(BlockBehaviour.Properties.copy(Blocks.BARREL).noOcclusion()));
    public static final RegistryObject<Block> SPRUCE_MUG_BLOCK_CHORUS_ALE = registerBlockWithoutItem("spruce_mug_block_chorus_ale",
            () -> new ChorusAleMugBlock(BlockBehaviour.Properties.copy(Blocks.BARREL).noOcclusion()));
    public static final RegistryObject<Block> SPRUCE_MUG_BLOCK_NETHER_STAR_LAGER = registerBlockWithoutItem("spruce_mug_block_nether_star_lager",
            () -> new NetherStarLagerMugBlock(BlockBehaviour.Properties.copy(Blocks.BARREL).noOcclusion()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name,
                                                                     Supplier<T> block,
                                                                     CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);

        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name,
                                                                            RegistryObject<T> block,
                                                                            CreativeModeTab tab) {
        return AlcoItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    private static <T extends Block> RegistryObject<Block> registerBlockWithoutItem(String name,
                                                                                    Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
