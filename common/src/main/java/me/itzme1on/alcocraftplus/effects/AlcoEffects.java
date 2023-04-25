package me.itzme1on.alcocraftplus.effects;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import net.minecraft.core.Registry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class AlcoEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(AlcoCraftPlus.MOD_ID, Registry.MOB_EFFECT_REGISTRY);

    public static final RegistrySupplier<MobEffect> ATTRACT = EFFECTS.register("attract", () ->
            new AttractEffect(MobEffectCategory.BENEFICIAL, rawColorFromRGB(170, 14, 1)) {});

    public static final RegistrySupplier<MobEffect> FREEZE = EFFECTS.register("freeze", () ->
            new FreezeEffect(MobEffectCategory.NEUTRAL, rawColorFromRGB(143, 181, 246)) {});

    public static final RegistrySupplier<MobEffect> PHANTOM = EFFECTS.register("phantom", () ->
            new PhantomEffect(MobEffectCategory.BENEFICIAL, rawColorFromRGB(58, 70, 123)) {});

    public static final RegistrySupplier<MobEffect> WITHER = EFFECTS.register("wither", () ->
            new WitherEffect(MobEffectCategory.BENEFICIAL, rawColorFromRGB(29, 3, 3)) {});

    public static int rawColorFromRGB(int red, int green, int blue) {
        int rgb = Math.max(Math.min(0xFF, red), 0);
        rgb = (rgb << 8) + Math.max(Math.min(0xFF, green), 0);
        rgb = (rgb << 8) + Math.max(Math.min(0xFF, blue), 0);

        return rgb;
    }

    public static void init() {
        EFFECTS.register();
    }
}
