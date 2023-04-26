package me.itzme1on.alcocraftplus.effects;

import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class PhantomEffect extends MobEffect {
    private final int color;

    protected PhantomEffect(int color) {
        super(MobEffectCategory.BENEFICIAL, color);

        this.color = color;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level.isClientSide && entity instanceof Player player)
            player.resetStat(Stats.CUSTOM.get(Stats.TIME_SINCE_REST));
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    public int getColor() {
        return this.color;
    }
}
