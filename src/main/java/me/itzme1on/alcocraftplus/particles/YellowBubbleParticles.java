package me.itzme1on.alcocraftplus.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class YellowBubbleParticles extends TextureSheetParticle {

    protected YellowBubbleParticles(ClientLevel world, double x, double y, double z, SpriteSet sprite, double motionX, double motionY, double motionZ) {
        super(world, x, y, z, motionX, motionY, motionZ);


        this.xd = motionX;
        this.yd = motionY;
        this.zd = motionZ;
        this.lifetime = 10;
        this.quadSize *= 0.6f;
        this.setSpriteFromAge(sprite);
        this.hasPhysics = true;
        this.gravity = -0.1f;

        this.rCol = 1f;
        this.gCol = 1f;
        this.bCol = 1f;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(SimpleParticleType particleType,
                                       ClientLevel world, double x, double y, double z,
                                       double motionX, double motionY, double motionZ) {
            return new YellowBubbleParticles(world, x, y, z, this.sprites, motionX, motionY, motionZ);
        }
    }
}
