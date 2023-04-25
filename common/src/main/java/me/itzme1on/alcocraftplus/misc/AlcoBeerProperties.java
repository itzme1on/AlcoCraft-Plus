package me.itzme1on.alcocraftplus.misc;

import me.itzme1on.alcocraftplus.effects.AlcoEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class AlcoBeerProperties {
    public static final FoodProperties HOP = new FoodProperties.Builder()
            .nutrition(5)
            .saturationMod(0.6f)
            .fast()
            .effect(new MobEffectInstance(MobEffects.CONFUSION, 100, 0), 1.0f)
            .build();

    public static final FoodProperties CHORUS_ALE = new FoodProperties.Builder()
            .nutrition(5)
            .saturationMod(0.6f)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.LUCK, 1200, 0), 1.0f)
            .effect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0), 1.0f)
            .build();

    public static final FoodProperties DIGGER_BITTER = new FoodProperties.Builder()
            .nutrition(5)
            .saturationMod(0.6f)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.DIG_SPEED, 1200, 1), 1.0f)
            .effect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0), 1.0f)
            .build();

    public static final FoodProperties DROWNED_ALE = new FoodProperties.Builder()
            .nutrition(5)
            .saturationMod(0.6f)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.WATER_BREATHING, 6000, 0), 1.0f)
            .effect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 3000, 0), 1.0f)
            .effect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0), 1.0f)
            .build();

    public static final FoodProperties ICE_BEER = new FoodProperties.Builder()
            .nutrition(5)
            .saturationMod(0.6f)
            .alwaysEat()
            .effect(new MobEffectInstance(AlcoEffects.FREEZE.get(), 6000, 0), 1.0f)
            .effect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0), 1.0f)
            .build();

    public static final FoodProperties KVASS = new FoodProperties.Builder()
            .nutrition(5)
            .saturationMod(0.6f)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.SATURATION, 2400, 0), 1.0f)
            .effect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 600, 1), 1.0f)
            .effect(new MobEffectInstance(MobEffects.CONFUSION, 100, 0), 1.0f)
            .build();

    public static final FoodProperties LEPRECHAUN_CIDER = new FoodProperties.Builder()
            .nutrition(5)
            .saturationMod(0.6f)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.LUCK, 1200, 0), 1.0f)
            .effect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0), 1.0f)
            .build();

    public static final FoodProperties MAGNET_PILSNER = new FoodProperties.Builder()
            .nutrition(5)
            .saturationMod(0.6f)
            .alwaysEat()
            .effect(new MobEffectInstance(AlcoEffects.ATTRACT.get(), 6000, 0), 1.0f)
            .effect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0), 1.0f)
            .build();

    public static final FoodProperties NETHER_PORTER = new FoodProperties.Builder()
            .nutrition(5)
            .saturationMod(0.6f)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 1), 1.0f)
            .effect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0), 1.0f)
            .build();

    public static final FoodProperties NETHER_STAR_LAGER = new FoodProperties.Builder()
            .nutrition(5)
            .saturationMod(0.6f)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0), 1.0f)
            .effect(new MobEffectInstance(MobEffects.LUCK, 2400, 0), 1.0f)
            .effect(new MobEffectInstance(MobEffects.JUMP, 6000, 0), 1.0f)
            .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 6000, 1), 1.0f)
            .effect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 6000, 0), 1.0f)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 3600, 0), 1.0f)
            .build();

    public static final FoodProperties NIGHT_RAUCH = new FoodProperties.Builder()
            .nutrition(5)
            .saturationMod(0.6f)
            .alwaysEat()
            .effect(new MobEffectInstance(AlcoEffects.PHANTOM.get(), 12000, 0), 1.0f)
            .effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 2400, 0), 1.0f)
            .effect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0), 1.0f)
            .build();

    public static final FoodProperties SUN_PALE_ALE = new FoodProperties.Builder()
            .nutrition(5)
            .saturationMod(0.6f)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 3600, 0), 1.0f)
            .build();

    public static final FoodProperties WITHER_STOUT = new FoodProperties.Builder()
            .nutrition(5)
            .saturationMod(0.6f)
            .alwaysEat()
            .effect(new MobEffectInstance(AlcoEffects.WITHER.get(), 2400, 0), 1.0f)
            .effect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0), 1.0f)
            .build();
}
