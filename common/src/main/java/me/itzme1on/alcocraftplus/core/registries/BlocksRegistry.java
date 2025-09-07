package me.itzme1on.alcocraftplus.core.registries;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.core.blocks.hop.Hop;
import me.itzme1on.alcocraftplus.core.blocks.hop.HopPlant;
import me.itzme1on.alcocraftplus.core.blocks.keg.Keg;
import me.itzme1on.alcocraftplus.core.blocks.mugs.AlcoDrink;
import me.itzme1on.alcocraftplus.core.blocks.mugs.MugBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class BlocksRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(AlcoCraftPlus.MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<Block> HOP = registerBlockWithoutItem("hop", () ->
            new Hop(BlockBehaviour.Properties
                    .ofFullCopy(Blocks.CAVE_VINES)
                    .lightLevel(CaveVines.emission(0))
                    .noOcclusion())
    );

    public static final RegistrySupplier<Block> HOP_PLANT = registerBlockWithoutItem("hop_plant", () ->
            new HopPlant(BlockBehaviour.Properties
                    .ofFullCopy(Blocks.CAVE_VINES)
                    .lightLevel(CaveVines.emission(0))
                    .noOcclusion())
    );

    public static final RegistrySupplier<Block> MUG = registerBlockWithoutItem("mug", MugBlock::new);

    public static final RegistrySupplier<Block> KEG = registerBlock("keg", () ->
            new Keg(BlockBehaviour.Properties
                    .ofFullCopy(Blocks.BARREL)
                    .noOcclusion()));

    public static final RegistrySupplier<Block> KVASS = registerBlockWithoutItem("kvass", () ->
            new AlcoDrink(ParticlesRegistry.YELLOW_BUBBLE::get));

    public static final RegistrySupplier<Block> CHORUS_ALE = registerBlockWithoutItem("chorus_ale", () ->
            new AlcoDrink(() -> ParticleTypes.PORTAL) {
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
            });

    public static final RegistrySupplier<Block> DIGGER_BITTER = registerBlockWithoutItem("digger_bitter", () ->
            new AlcoDrink(ParticlesRegistry.YELLOW_BUBBLE::get));

    public static final RegistrySupplier<Block> DROWNED_ALE = registerBlockWithoutItem("drowned_ale", MugBlock::new);

    public static final RegistrySupplier<Block> ICE_BEER = registerBlockWithoutItem("ice_beer", () ->
            new AlcoDrink(ParticlesRegistry.YELLOW_BUBBLE::get));

    public static final RegistrySupplier<Block> LEPRECHAUN_CIDER = registerBlockWithoutItem("leprechaun_cider", MugBlock::new);

    public static final RegistrySupplier<Block> MAGNET_PILSNER = registerBlockWithoutItem("magnet_pilsner", MugBlock::new);

    public static final RegistrySupplier<Block> NETHER_PORTER = registerBlockWithoutItem("nether_porter",
            MugBlock::new);

    public static final RegistrySupplier<Block> NETHER_STAR_LAGER = registerBlockWithoutItem("nether_star_lager", () ->
            new AlcoDrink(() -> ParticleTypes.FIREWORK) {
                @Override
                public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
                    if (random.nextFloat() < 0.5f) {
                        double d0 = pos.getX() + 0.4D + random.nextDouble() * 0.2D;
                        double d1 = pos.getY() + 0.2D + random.nextDouble() * (0.2D - 0.02D);
                        double d2 = pos.getZ() + 0.4D + random.nextDouble() * 0.2D;

                        level.addParticle(getParticle(), d0, d1, d2, 0.0D, random.nextDouble() * 0.1D, 0.0D);
                    }
                }
            });

    public static final RegistrySupplier<Block> NIGHT_RAUCH = registerBlockWithoutItem("night_rauch", MugBlock::new);

    public static final RegistrySupplier<Block> SUN_PALE_ALE = registerBlockWithoutItem("sun_pale_ale", () ->
            new AlcoDrink(ParticlesRegistry.YELLOW_BUBBLE::get));

    public static final RegistrySupplier<Block> WITHER_STOUT = registerBlockWithoutItem("wither_stout", MugBlock::new);

    private BlocksRegistry() {
    }
    
    private static <T extends Block> RegistrySupplier<T> registerBlock(String name,
                                                                       Supplier<T> block) {
        RegistrySupplier<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);

        return toReturn;
    }

    private static <T extends Block> RegistrySupplier<Block> registerBlockWithoutItem(String name,
                                                                                      Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistrySupplier<Item> registerBlockItem(String name,
                                                                              RegistrySupplier<T> block) {

        return ItemsRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register() {
        BLOCKS.register();
    }
}
