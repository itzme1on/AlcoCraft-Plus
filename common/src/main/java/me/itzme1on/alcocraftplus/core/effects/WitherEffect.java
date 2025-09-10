package me.itzme1on.alcocraftplus.core.effects;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class WitherEffect extends MobEffect {
    public WitherEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int i) {
        if (level.isClientSide()) return true;

        LivingEntity attacker = entity.getLastHurtByMob();

        if (attacker != null && attacker.isAlive()) {
            double radius = 50.0D;

            if (attacker.distanceToSqr(entity) <= radius * radius)
                attacker.addEffect(new MobEffectInstance(MobEffects.WITHER, 40, 5));
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int interval = 10;

        return duration % interval == 0;
    }
}
