package me.itzme1on.alcocraftplus.blocks.mugs.beer;

import me.itzme1on.alcocraftplus.blocks.mugs.MugBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class ChorusAleMug extends MugBlock {
    public ChorusAleMug(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
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

                world.addParticle(ParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
            }
        }
    }
}
