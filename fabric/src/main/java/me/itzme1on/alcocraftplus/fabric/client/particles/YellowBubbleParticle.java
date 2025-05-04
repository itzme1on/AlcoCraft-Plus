package me.itzme1on.alcocraftplus.fabric.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class YellowBubbleParticle extends TextureSheetParticle {
    protected YellowBubbleParticle(ClientLevel level, double x, double y, double z,
                                   SpriteSet sprites, double xd, double yd, double zd) {
        super(level, x, y, z, xd, yd, zd);

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
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Factory(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new YellowBubbleParticle(level, x, y, z, this.sprites, xSpeed, ySpeed, zSpeed);
        }
    }
}
