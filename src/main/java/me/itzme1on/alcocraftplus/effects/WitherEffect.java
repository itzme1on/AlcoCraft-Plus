package me.itzme1on.alcocraftplus.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class WitherEffect extends MobEffect {
    protected WitherEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        Level world = entity.level;

        for (Entity e : world.getEntities(entity, new AABB(entity.blockPosition()).inflate(50))) {
            if (e instanceof LivingEntity livingEntity) {
                if (entity.getLastHurtByMob() == livingEntity) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, 40, 10));
                }
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
