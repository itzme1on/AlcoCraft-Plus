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
    private final int color;

    protected WitherEffect(int color) {
        super(MobEffectCategory.BENEFICIAL, color);

        this.color = color;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        Level world = entity.level;

        for (Entity e : world.getEntities(entity, new AABB(entity.blockPosition()).inflate(50))) {
            if (e instanceof LivingEntity livingEntity) {
                if (livingEntity.getLastHurtByMob() == entity)
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, 40, 10));
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    public int getColor() {
        return this.color;
    }
}
