package me.itzme1on.alcocraftplus.blocks.mugs.beer;

import me.itzme1on.alcocraftplus.blocks.mugs.MugBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class ChorusAle extends MugBlock {
    public ChorusAle(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(@NotNull BlockState blockState, @NotNull Level world, @NotNull BlockPos pos, Random random) {
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

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        super.onRemove(blockState, level, blockPos, blockState2, bl);

        Block.popResource(level, blockPos, this.asItem().getDefaultInstance());
    }

    @Override
    public PushReaction getPistonPushReaction(@NotNull BlockState blockState) {
        return PushReaction.DESTROY;
    }
}
