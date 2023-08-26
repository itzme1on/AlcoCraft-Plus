package me.itzme1on.alcocraftplus.blocks;

import me.itzme1on.alcocraftplus.blocks.mugs.MugBlock;
import me.itzme1on.alcocraftplus.blocks.mugs.beer.*;
import me.itzme1on.alcocraftplus.blocks.plants.Hop;
import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.blocks.plants.HopPlant;
import me.itzme1on.alcocraftplus.blocks.workstations.Keg;
import me.itzme1on.alcocraftplus.items.AlcoItems;
import net.minecraft.world.item.BlockItem;
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
            DeferredRegister.create(ForgeRegistries.BLOCKS, AlcoCraftPlus.MOD_ID);

    public static final RegistryObject<Block> KEG = registerBlock("keg",
            () -> new Keg(BlockBehaviour.Properties
                    .copy(Blocks.BARREL)
                    .noOcclusion()));

    public static final RegistryObject<Block> HOP = registerBlockWithoutItem("hop",
            () -> new Hop(BlockBehaviour.Properties
                    .copy(Blocks.CAVE_VINES)
                    .lightLevel(CaveVines.emission(0))
                    .noOcclusion()));

    public static final RegistryObject<Block> HOP_PLANT = registerBlockWithoutItem("hop_plant",
            () -> new HopPlant(BlockBehaviour.Properties
                    .copy(Blocks.CAVE_VINES)
                    .lightLevel(CaveVines.emission(0))
                    .noOcclusion()));

    public static final RegistryObject<Block> MUG_EMPTY = registerBlockWithoutItem(
            "mug_empty",
            () -> new MugBlock(BlockBehaviour.Properties
                    .copy(Blocks.BARREL)
                    .noOcclusion()));

    public static final RegistryObject<Block> MUG_OF_CHORUS_ALE = registerBlockWithoutItem("mug_of_chorus_ale",
            () -> new ChorusAleMug(BlockBehaviour.Properties
                    .copy(Blocks.BARREL)
                    .noOcclusion()));

    public static final RegistryObject<Block> MUG_OF_DIGGER_BITTER = registerBlockWithoutItem("mug_of_digger_bitter",
            () -> new DiggerBitterMug(BlockBehaviour.Properties
                    .copy(Blocks.BARREL)
                    .noOcclusion()));

    public static final RegistryObject<Block> MUG_OF_DROWNED_ALE = registerBlockWithoutItem("mug_of_drowned_ale",
            () -> new DrownedAleMug(BlockBehaviour.Properties
                    .copy(Blocks.BARREL)
                    .noOcclusion()));

    public static final RegistryObject<Block> MUG_OF_ICE_BEER = registerBlockWithoutItem("mug_of_ice_beer",
            () -> new IceBeerMug(BlockBehaviour.Properties
                    .copy(Blocks.BARREL)
                    .noOcclusion()));

    public static final RegistryObject<Block> MUG_OF_KVASS = registerBlockWithoutItem("mug_of_kvass",
            () -> new KvassMug(BlockBehaviour.Properties
                    .copy(Blocks.BARREL)
                    .noOcclusion()));

    public static final RegistryObject<Block> MUG_OF_LEPRECHAUN_CIDER = registerBlockWithoutItem("mug_of_leprechaun_cider",
            () -> new LeprechaunCiderMug(BlockBehaviour.Properties
                    .copy(Blocks.BARREL)
                    .noOcclusion()));

    public static final RegistryObject<Block> MUG_OF_MAGNET_PILSNER = registerBlockWithoutItem("mug_of_magnet_pilsner",
            () -> new MagnetPilsnerMug(BlockBehaviour.Properties
                    .copy(Blocks.BARREL)
                    .noOcclusion()));

    public static final RegistryObject<Block> MUG_OF_NETHER_PORTER = registerBlockWithoutItem("mug_of_nether_porter",
            () -> new NetherPorterMug(BlockBehaviour.Properties
                    .copy(Blocks.BARREL)
                    .noOcclusion()));

    public static final RegistryObject<Block> MUG_OF_NIGHT_RAUCH = registerBlockWithoutItem("mug_of_night_rauch",
            () -> new NightRauchMug(BlockBehaviour.Properties
                    .copy(Blocks.BARREL)
                    .noOcclusion()));

    public static final RegistryObject<Block> MUG_OF_SUN_PALE_ALE = registerBlockWithoutItem("mug_of_sun_pale_ale",
            () -> new SunPaleAleMug(BlockBehaviour.Properties
                    .copy(Blocks.BARREL)
                    .noOcclusion()));

    public static final RegistryObject<Block> MUG_OF_WITHER_STOUT = registerBlockWithoutItem("mug_of_wither_stout",
            () -> new WitherStoutMug(BlockBehaviour.Properties
                    .copy(Blocks.BARREL)
                    .noOcclusion()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name,
                                                                     Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);

        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name,
                                                                            RegistryObject<T> block) {

        return AlcoItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<Block> registerBlockWithoutItem(String name,
                                                                                    Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
