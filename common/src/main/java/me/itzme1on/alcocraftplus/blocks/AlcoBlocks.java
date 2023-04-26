package me.itzme1on.alcocraftplus.blocks;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.blocks.mugs.AlcoDrink;
import me.itzme1on.alcocraftplus.blocks.mugs.MugBlock;
import me.itzme1on.alcocraftplus.blocks.plants.Hop;
import me.itzme1on.alcocraftplus.blocks.plants.HopPlant;
import me.itzme1on.alcocraftplus.particles.AlcoParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
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

    public static final RegistrySupplier<Block> MUG = registerBlockWithoutItem("mug", MugBlock::new);

    public static final RegistrySupplier<Block> CHORUS_ALE = registerBlockWithoutItem("chorus_ale",
            () -> new AlcoDrink(ParticleTypes.PORTAL) {
                @Override
                public void animateTick(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Random random) {
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

                            world.addParticle(getParticle(), d0, d1, d2, d3, d4, d5);
                        }
                    }
                }
            });
    
    public static final RegistrySupplier<Block> DIGGER_BITTER = registerBlockWithoutItem("digger_bitter",
            () -> new AlcoDrink(AlcoParticles.YELLOW_BUBBLE.get()));

    public static final RegistrySupplier<Block> DROWNED_ALE = registerBlockWithoutItem("drowned_ale",
            MugBlock::new);

    public static final RegistrySupplier<Block> ICE_BEER = registerBlockWithoutItem("ice_beer",
            () -> new AlcoDrink(ParticleTypes.SNOWFLAKE));

    public static final RegistrySupplier<Block> KVASS = registerBlockWithoutItem("kvass",
            () -> new AlcoDrink(AlcoParticles.YELLOW_BUBBLE.get()));

    public static final RegistrySupplier<Block> LEPRECHAUN_CIDER = registerBlockWithoutItem("leprechaun_cider",
            MugBlock::new);

    public static final RegistrySupplier<Block> MAGNET_PILSNER = registerBlockWithoutItem("magnet_pilsner",
            MugBlock::new);

    public static final RegistrySupplier<Block> NETHER_PORTER = registerBlockWithoutItem("nether_porter",
            MugBlock::new);

    public static final RegistrySupplier<Block> NETHER_STAR_LAGER = registerBlockWithoutItem("nether_star_lager",
            () -> new AlcoDrink(ParticleTypes.FIREWORK) {
                @Override
                public void animateTick(@NotNull BlockState state, @NotNull Level world,
                                        @NotNull BlockPos pos, @NotNull Random random) {
                    if (random.nextFloat() < 0.5f) {
                        double d0 = pos.getX() + 0.4D + random.nextDouble() * 0.2D;
                        double d1 = pos.getY() + 0.2D + random.nextDouble() * (0.2D - 0.02D);
                        double d2 = pos.getZ() + 0.4D + random.nextDouble() * 0.2D;

                        world.addParticle(getParticle(), d0, d1, d2, 0.0D, random.nextDouble() * 0.1D, 0.0D);
                    }
                }
            });

    public static final RegistrySupplier<Block> NIGHT_RAUCH = registerBlockWithoutItem("night_rauch",
            MugBlock::new);

    public static final RegistrySupplier<Block> SUN_PALE_ALE = registerBlockWithoutItem("sun_pale_ale",
            () -> new AlcoDrink(AlcoParticles.YELLOW_BUBBLE.get()));

    public static final RegistrySupplier<Block> WITHER_STOUT = registerBlockWithoutItem("wither_stout",
            MugBlock::new);

    private static <T extends Block> RegistrySupplier<Block> registerBlockWithoutItem(String name,
                                                                                      Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    public static void init() {
        BLOCKS.register();
    }
}
