package me.itzme1on.alcocraftplus.blocks;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.blocks.mugs.MugBlock;
import me.itzme1on.alcocraftplus.blocks.mugs.beer.*;
import me.itzme1on.alcocraftplus.blocks.plants.Hop;
import me.itzme1on.alcocraftplus.blocks.plants.HopPlant;
import me.itzme1on.alcocraftplus.items.AlcoItems;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class AlcoBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(AlcoCraftPlus.MOD_ID, Registry.BLOCK_REGISTRY);

    public static final RegistrySupplier<Block> HOP = registerBlockWithoutItem("hop",
            () -> new Hop(BlockBehaviour.Properties
                    .copy(Blocks.CAVE_VINES)
                    .lightLevel(CaveVines.emission(0))
                    .noOcclusion()));

    public static final RegistrySupplier<Block> HOP_PLANT = registerBlockWithoutItem("hop_plant",
            () -> new HopPlant(BlockBehaviour.Properties
                    .copy(Blocks.CAVE_VINES)
                    .lightLevel(CaveVines.emission(0))
                    .noOcclusion()));

    public static final RegistrySupplier<Block> MUG = registerBlockWithoutItem("mug",
            () -> new MugBlock(BlockBehaviour.Properties
                    .copy(Blocks.BARREL)
                    .instabreak()
                    .noOcclusion())
    );

    public static final RegistrySupplier<Block> CHORUS_ALE = registerBlockWithoutItem("chorus_ale",
            () -> new ChorusAle(BlockBehaviour.Properties
                    .copy(Blocks.BARREL)
                    .instabreak()
                    .noOcclusion()));
    
    public static final RegistrySupplier<Block> DIGGER_BITTER = registerBlockWithoutItem("digger_bitter",
            () -> new DiggerBitter(BlockBehaviour.Properties
                    .copy(Blocks.BARREL)
                    .instabreak()
                    .noOcclusion()));

    public static final RegistrySupplier<Block> DROWNED_ALE = registerBlockWithoutItem("drowned_ale",
            () -> new DrownedAle(BlockBehaviour.Properties
                    .copy(Blocks.BARREL)
                    .instabreak()
                    .noOcclusion()));

    public static final RegistrySupplier<Block> ICE_BEER = registerBlockWithoutItem("ice_beer",
            () -> new IceBeer(BlockBehaviour.Properties
                    .copy(Blocks.BARREL)
                    .instabreak()
                    .noOcclusion()));

    public static final RegistrySupplier<Block> KVASS = registerBlockWithoutItem("kvass",
            () -> new Kvass(BlockBehaviour.Properties
                    .copy(Blocks.BARREL)
                    .instabreak()
                    .noOcclusion()));

    public static final RegistrySupplier<Block> LEPRECHAUN_CIDER = registerBlockWithoutItem("leprechaun_cider",
            () -> new LeprechaunCider(BlockBehaviour.Properties
                    .copy(Blocks.BARREL)
                    .instabreak()
                    .noOcclusion()));

    public static final RegistrySupplier<Block> MAGNET_PILSNER = registerBlockWithoutItem("magnet_pilsner",
            () -> new MagnetPilsner(BlockBehaviour.Properties
                    .copy(Blocks.BARREL)
                    .instabreak()
                    .noOcclusion()));

    public static final RegistrySupplier<Block> NETHER_PORTER = registerBlockWithoutItem("nether_porter",
            () -> new NetherPorter(BlockBehaviour.Properties
                    .copy(Blocks.BARREL)
                    .instabreak()
                    .noOcclusion()));

    public static final RegistrySupplier<Block> NETHER_STAR_LAGER = registerBlockWithoutItem("nether_star_lager",
            () -> new NetherStarLager(BlockBehaviour.Properties
                    .copy(Blocks.BARREL)
                    .instabreak()
                    .noOcclusion()));

    public static final RegistrySupplier<Block> NIGHT_RAUCH = registerBlockWithoutItem("night_rauch",
            () -> new NightRauch(BlockBehaviour.Properties
                    .copy(Blocks.BARREL)
                    .instabreak()
                    .noOcclusion()));

    public static final RegistrySupplier<Block> SUN_PALE_ALE = registerBlockWithoutItem("sun_pale_ale",
            () -> new SunPaleAle(BlockBehaviour.Properties
                    .copy(Blocks.BARREL)
                    .instabreak()
                    .noOcclusion()));

    public static final RegistrySupplier<Block> WITHER_STOUT = registerBlockWithoutItem("wither_stout",
            () -> new WitherStout(BlockBehaviour.Properties
                    .copy(Blocks.BARREL)
                    .instabreak()
                    .noOcclusion()));

    private static <T extends Block> RegistrySupplier<T> registerBlock(String name,
                                                                       Supplier<T> block,
                                                                       CreativeModeTab tab) {
        RegistrySupplier<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);

        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name,
                                                            RegistrySupplier<T> block,
                                                            CreativeModeTab tab) {
        AlcoItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()
                .tab(tab)));
    }

    private static <T extends Block> RegistrySupplier<Block> registerBlockWithoutItem(String name,
                                                                                      Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    public static void init() {
        BLOCKS.register();
    }
}
