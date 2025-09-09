package me.itzme1on.alcocraftplus.core.registries;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.core.blocks.hop.Hop;
import me.itzme1on.alcocraftplus.core.blocks.hop.HopPlant;
import me.itzme1on.alcocraftplus.core.blocks.keg.Keg;
import me.itzme1on.alcocraftplus.core.blocks.mugs.AlcoDrink;
import me.itzme1on.alcocraftplus.core.blocks.mugs.MugBlock;
import me.itzme1on.alcocraftplus.core.utils.IdentifierUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Function;
import java.util.function.Supplier;

public class BlocksRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(AlcoCraftPlus.MOD_ID, Registries.BLOCK);
    public static final RegistrySupplier<Block> HOP = registerBlockWithoutItemWithProps(
            "hop",
            Hop::new,
            () -> BlockBehaviour.Properties
                    .ofFullCopy(Blocks.CAVE_VINES)
                    .lightLevel(CaveVines.emission(0))
                    .noOcclusion()
    );
    public static final RegistrySupplier<Block> HOP_PLANT = registerBlockWithoutItemWithProps("hop_plant",
            HopPlant::new,
            () -> BlockBehaviour.Properties
                    .ofFullCopy(Blocks.CAVE_VINES)
                    .lightLevel(CaveVines.emission(0))
                    .noOcclusion()
    );
    public static final RegistrySupplier<Block> MUG = registerBlockWithoutItemWithProps("mug",
            MugBlock::new,
            () -> BlockBehaviour.Properties
                    .ofFullCopy(Blocks.SPRUCE_PLANKS)
                    .instabreak()
                    .noOcclusion()
    );
    public static final RegistrySupplier<Block> KEG = registerBlockWithProps("keg",
            Keg::new,
            () -> BlockBehaviour.Properties
                    .ofFullCopy(Blocks.BARREL)
                    .noOcclusion()
    );
    public static final RegistrySupplier<Block> KVASS = registerBlockWithoutItemWithProps("kvass",
            properties -> new AlcoDrink(ParticlesRegistry.YELLOW_BUBBLE::get, properties),
            () -> BlockBehaviour.Properties
                    .ofFullCopy(Blocks.SPRUCE_PLANKS)
                    .instabreak()
                    .noOcclusion()
    );
    public static final RegistrySupplier<Block> CHORUS_ALE = registerBlockWithoutItemWithProps("chorus_ale",
            properties -> new AlcoDrink(() -> ParticleTypes.PORTAL, properties) {
                @Override
                public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
                    if (random.nextFloat() < 0.4f) {
                        for (int i = 0; i < 3; ++i) {
                            int j = random.nextInt(2) * 2 - 1;
                            int k = random.nextInt(2) * 2 - 1;

                            double d0 = pos.getX() + 0.5d + 0.25d * j;
                            double d1 = pos.getY() + random.nextFloat();
                            double d2 = pos.getZ() + 0.5d + 0.25d * k;
                            double d3 = random.nextFloat() * j;
                            double d4 = (random.nextFloat() - 0.5d) * 0.125d;
                            double d5 = random.nextFloat() * k;

                            level.addParticle(getParticle(), d0, d1, d2, d3, d4, d5);
                        }
                    }
                }
            },
            () -> BlockBehaviour.Properties
                    .ofFullCopy(Blocks.SPRUCE_PLANKS)
                    .instabreak()
                    .noOcclusion()
    );
    public static final RegistrySupplier<Block> DIGGER_BITTER = registerBlockWithoutItemWithProps("digger_bitter",
            properties -> new AlcoDrink(ParticlesRegistry.YELLOW_BUBBLE::get, properties),
            () -> BlockBehaviour.Properties
                    .ofFullCopy(Blocks.SPRUCE_PLANKS)
                    .instabreak()
                    .noOcclusion()
    );
    public static final RegistrySupplier<Block> DROWNED_ALE = registerBlockWithoutItemWithProps("drowned_ale",
            MugBlock::new,
            () -> BlockBehaviour.Properties
                    .ofFullCopy(Blocks.SPRUCE_PLANKS)
                    .instabreak()
                    .noOcclusion()
    );
    public static final RegistrySupplier<Block> ICE_BEER = registerBlockWithoutItemWithProps("ice_beer",
            MugBlock::new,
            () -> BlockBehaviour.Properties
                    .ofFullCopy(Blocks.SPRUCE_PLANKS)
                    .instabreak()
                    .noOcclusion()
    );
    public static final RegistrySupplier<Block> LEPRECHAUN_CIDER = registerBlockWithoutItemWithProps("leprechaun_cider",
            MugBlock::new,
            () -> BlockBehaviour.Properties
                    .ofFullCopy(Blocks.SPRUCE_PLANKS)
                    .instabreak()
                    .noOcclusion());
    public static final RegistrySupplier<Block> MAGNET_PILSNER = registerBlockWithoutItemWithProps("magnet_pilsner",
            MugBlock::new,
            () -> BlockBehaviour.Properties
                    .ofFullCopy(Blocks.SPRUCE_PLANKS)
                    .instabreak()
                    .noOcclusion());
    public static final RegistrySupplier<Block> NETHER_PORTER = registerBlockWithoutItemWithProps("nether_porter",
            MugBlock::new,
            () -> BlockBehaviour.Properties
                    .ofFullCopy(Blocks.SPRUCE_PLANKS)
                    .instabreak()
                    .noOcclusion()
    );
    public static final RegistrySupplier<Block> NETHER_STAR_LAGER = registerBlockWithoutItemWithProps("nether_star_lager",
            properties -> new AlcoDrink(() -> ParticleTypes.FIREWORK, properties) {
                @Override
                public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
                    if (random.nextFloat() < 0.5f) {
                        double d0 = pos.getX() + 0.4D + random.nextDouble() * 0.2D;
                        double d1 = pos.getY() + 0.2D + random.nextDouble() * (0.2D - 0.02D);
                        double d2 = pos.getZ() + 0.4D + random.nextDouble() * 0.2D;

                        level.addParticle(getParticle(), d0, d1, d2, 0.0D, random.nextDouble() * 0.1D, 0.0D);
                    }
                }
            },
            () -> BlockBehaviour.Properties
                    .ofFullCopy(Blocks.SPRUCE_PLANKS)
                    .instabreak()
                    .noOcclusion()
    );
    public static final RegistrySupplier<Block> NIGHT_RAUCH = registerBlockWithoutItemWithProps("night_rauch",
            MugBlock::new,
            () -> BlockBehaviour.Properties
                    .ofFullCopy(Blocks.SPRUCE_PLANKS)
                    .instabreak()
                    .noOcclusion()
    );
    public static final RegistrySupplier<Block> SUN_PALE_ALE = registerBlockWithoutItemWithProps("sun_pale_ale",
            properties -> new AlcoDrink(ParticlesRegistry.YELLOW_BUBBLE::get, properties),
            () -> BlockBehaviour.Properties
                    .ofFullCopy(Blocks.SPRUCE_PLANKS)
                    .instabreak()
                    .noOcclusion()
    );
    public static final RegistrySupplier<Block> WITHER_STOUT = registerBlockWithoutItemWithProps("wither_stout",
            MugBlock::new,
            () -> BlockBehaviour.Properties
                    .ofFullCopy(Blocks.SPRUCE_PLANKS)
                    .instabreak()
                    .noOcclusion());

    private BlocksRegistry() {
    }

    private static <T extends Block> void registerBlockItem(String name, RegistrySupplier<T> block) {
        ItemsRegistry.ITEMS.register(name, () ->
                new BlockItem(block.get(), new Item.Properties().setId(itemId(name))));
    }

    private static <T extends Block> RegistrySupplier<T> registerBlockWithoutItemWithProps(
            String name,
            Function<BlockBehaviour.Properties, T> function,
            Supplier<BlockBehaviour.Properties> supplier
    ) {
        return BLOCKS.register(name, () -> function.apply(supplier.get().setId(id(name))));
    }

    private static <T extends Block> RegistrySupplier<T> registerBlockWithProps(
            String name,
            Function<BlockBehaviour.Properties, T> function,
            Supplier<BlockBehaviour.Properties> supplier
    ) {
        RegistrySupplier<T> reg = BLOCKS.register(name, () -> function.apply(supplier.get().setId(id(name))));

        registerBlockItem(name, reg);

        return reg;
    }

    private static ResourceKey<Block> id(String string) {
        return ResourceKey.create(Registries.BLOCK, IdentifierUtil.of(string));
    }

    private static ResourceKey<Item> itemId(String string) {
        return ResourceKey.create(
                Registries.ITEM,
                IdentifierUtil.of(string)
        );
    }

    public static void register() {
        BLOCKS.register();
    }
}
