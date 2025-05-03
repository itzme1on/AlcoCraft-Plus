package me.itzme1on.alcocraftplus.fabric.core.blocks.mugs;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class AlcoDrink extends MugBlock {
    private final Supplier<ParticleOptions> particle;

    public AlcoDrink(Supplier<ParticleOptions> particle) {
        super();

        this.particle = particle;
    }

    @Override
    public void animateTick(BlockState state, Level level,
                            BlockPos pos, RandomSource random) {
        if (particle != null && random.nextFloat() < 0.6f) {
            double d0 = pos.getX() + 0.4D + random.nextDouble() * 0.2D;
            double d1 = pos.getY() + 0.4D + random.nextDouble() * (0.2D - 0.02D);
            double d2 = pos.getZ() + 0.4D + random.nextDouble() * 0.2D;

            level.addParticle(this.particle.get(), d0, d1, d2, 0.0D, random.nextDouble() * 0.1D, 0.0D);
        }
    }

    protected ParticleOptions getParticle() {
        return this.particle.get();
    }
}
