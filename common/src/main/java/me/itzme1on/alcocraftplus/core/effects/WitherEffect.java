package me.itzme1on.alcocraftplus.core.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class WitherEffect extends MobEffect {
    public WitherEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        Level level = entity.level();

        if (level.isClientSide()) return;

        LivingEntity attacker = entity.getLastHurtByMob();

        if (attacker != null && attacker.isAlive()) {
            double radius = 50.0D;

            if (attacker.distanceToSqr(entity) <= radius * radius)
                attacker.addEffect(new MobEffectInstance(MobEffects.WITHER, 40, 5));
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 10 == 0;
    }
}
