package me.itzme1on.alcocraftplus.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class YellowBubbleParticle extends SingleQuadParticle {
    protected YellowBubbleParticle(ClientLevel level, double x, double y, double z,
                                   SpriteSet sprites, double xd, double yd, double zd) {
        super(level, x, y, z, xd, yd, zd, sprites.first());

        this.friction = 0.8F;
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
        this.quadSize *= 0.6F;
        this.lifetime = 10;
        this.setSpriteFromAge(sprites);
        this.hasPhysics = true;
        this.gravity = -0.1f;

        this.rCol = 1f;
        this.gCol = 1f;
        this.bCol = 1f;
    }

    @Override
    protected SingleQuadParticle.Layer getLayer() {
        return SingleQuadParticle.Layer.TRANSLUCENT;
    }

    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Factory(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, RandomSource randomSource) {
            return new YellowBubbleParticle(level, x, y, z, this.sprites, xSpeed, ySpeed, zSpeed);
        }
    }
}
