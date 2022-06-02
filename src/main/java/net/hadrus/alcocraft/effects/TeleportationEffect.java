package net.hadrus.alcocraft.effects;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;

import java.util.Random;

public class TeleportationEffect extends MobEffect {

    protected TeleportationEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        Random random = new Random();

        double d0 = pLivingEntity.getX();
        double d1 = pLivingEntity.getY();
        double d2 = pLivingEntity.getZ();

        pLivingEntity.teleportTo(d0 + random.nextDouble(4), d1 + random.nextDouble(4), d2 + random.nextDouble(4));
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
