package me.itzme1on.alcocraftplus.blocks.mugs;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class AlcoDrink extends MugBlock {
    private final ParticleOptions particle;

    public AlcoDrink(ParticleOptions particle) {
        super();

        this.particle = particle;
    }

    @Override
    public void animateTick(@NotNull BlockState state, @NotNull Level world,
                            @NotNull BlockPos pos, @NotNull Random random) {
        if (particle != null && random.nextFloat() < 0.6f) {
            double d0 = pos.getX() + 0.4D + random.nextDouble() * 0.2D;
            double d1 = pos.getY() + 0.4D + random.nextDouble() * (0.2D - 0.02D);
            double d2 = pos.getZ() + 0.4D + random.nextDouble() * 0.2D;

            world.addParticle(this.particle, d0, d1, d2, 0.0D, random.nextDouble() * 0.1D, 0.0D);
        }
    }

    public ParticleOptions getParticle() {
        return this.particle;
    }
}
