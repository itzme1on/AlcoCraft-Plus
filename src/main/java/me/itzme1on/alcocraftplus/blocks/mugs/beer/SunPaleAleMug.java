package me.itzme1on.alcocraftplus.blocks.mugs.beer;

import me.itzme1on.alcocraftplus.blocks.mugs.MugBlock;
import me.itzme1on.alcocraftplus.particles.AlcoParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class SunPaleAleMug extends MugBlock {
    public SunPaleAleMug(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
        if (random.nextFloat() < 0.4f) {
            double d0 = pos.getX() + 0.4D + random.nextDouble() * 0.2D;
            double d1 = pos.getY() + 0.2D + random.nextDouble() * (0.2D - 0.02D);
            double d2 = pos.getZ() + 0.4D + random.nextDouble() * 0.2D;

            world.addParticle(AlcoParticles.YELLOW_BUBBLES.get(), d0, d1, d2, 0.0D, random.nextDouble() * 0.1D, 0.0D);
        }
    }
}
