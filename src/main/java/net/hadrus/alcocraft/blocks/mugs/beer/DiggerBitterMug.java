package net.hadrus.alcocraft.blocks.mugs.beer;

import net.hadrus.alcocraft.blocks.mugs.MugBlock;
import net.hadrus.alcocraft.particles.AlcoParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class DiggerBitterMug extends MugBlock {
    public DiggerBitterMug(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRand) {
        if (pRand.nextFloat() < 0.4f) {
            double d0 = (double)pPos.getX() + 0.4D + pRand.nextDouble() * 0.2D;
            double d1 = (double)pPos.getY() + 0.2D + pRand.nextDouble() * (0.2D - 0.02D);
            double d2 = (double)pPos.getZ() + 0.4D + pRand.nextDouble() * 0.2D;
            pLevel.addParticle(AlcoParticles.YELLOW_BUBBLES.get(), d0, d1, d2, 0.0D, pRand.nextDouble() * 0.1D, 0.0D);
        }
    }
}