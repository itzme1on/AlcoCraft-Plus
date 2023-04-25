package me.itzme1on.alcocraftplus.blocks.mugs.beer;

import me.itzme1on.alcocraftplus.blocks.mugs.MugBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class NetherStarLager extends MugBlock {
    public NetherStarLager(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, Random random) {
        if (random.nextFloat() < 0.6f) {
            double d0 = blockPos.getX() + 0.4D + random.nextDouble() * 0.2D;
            double d1 = blockPos.getY() + 0.4D + random.nextDouble() * (0.2D - 0.02D);
            double d2 = blockPos.getZ() + 0.4D + random.nextDouble() * 0.2D;

            level.addParticle(ParticleTypes.FIREWORK, d0, d1, d2, 0.0D, random.nextDouble() * 0.1D, 0.0D);
        }
    }
}
