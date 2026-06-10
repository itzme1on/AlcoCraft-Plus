package me.itzme1on.alcocraftplus.core.misc;

import net.minecraft.world.food.FoodProperties;

public final class BeerProperties {
    public static final FoodProperties HOP = new FoodProperties.Builder()
            .nutrition(5)
            .saturationModifier(0.6f)
            .build();

    public static final FoodProperties KVASS = new FoodProperties.Builder()
            .nutrition(5)
            .saturationModifier(0.6f)
            .alwaysEdible()
            .build();

    public static final FoodProperties CHORUS_ALE = new FoodProperties.Builder()
            .nutrition(5)
            .saturationModifier(0.6f)
            .alwaysEdible()
            .build();

    public static final FoodProperties DIGGER_BITTER = new FoodProperties.Builder()
            .nutrition(5)
            .saturationModifier(0.6f)
            .alwaysEdible()
            .build();

    public static final FoodProperties DROWNED_ALE = new FoodProperties.Builder()
            .nutrition(5)
            .saturationModifier(0.6f)
            .alwaysEdible()
            .build();

    public static final FoodProperties ICE_BEER = new FoodProperties.Builder()
            .nutrition(5)
            .saturationModifier(0.6f)
            .alwaysEdible()
            .build();

    public static final FoodProperties LEPRECHAUN_CIDER = new FoodProperties.Builder()
            .nutrition(5)
            .saturationModifier(0.6f)
            .alwaysEdible()
            .build();

    public static final FoodProperties MAGNET_PILSNER = new FoodProperties.Builder()
            .nutrition(5)
            .saturationModifier(0.6f)
            .alwaysEdible()
            .build();

    public static final FoodProperties NETHER_PORTER = new FoodProperties.Builder()
            .nutrition(5)
            .saturationModifier(0.6f)
            .alwaysEdible()
            .build();

    public static final FoodProperties NETHER_STAR_LAGER = new FoodProperties.Builder()
            .nutrition(5)
            .saturationModifier(0.6f)
            .alwaysEdible()
            .build();

    public static final FoodProperties NIGHT_RAUCH = new FoodProperties.Builder()
            .nutrition(5)
            .saturationModifier(0.6f)
            .alwaysEdible()
            .build();

    public static final FoodProperties SUN_PALE_ALE = new FoodProperties.Builder()
            .nutrition(5)
            .saturationModifier(0.6f)
            .alwaysEdible()
            .build();
    
    public static final FoodProperties WITHER_STOUT = new FoodProperties.Builder()
            .nutrition(5)
            .saturationModifier(0.6f)
            .alwaysEdible()
            .build();

    private BeerProperties() {
        throw new AssertionError("No instances of BeerProperties");
    }
}
