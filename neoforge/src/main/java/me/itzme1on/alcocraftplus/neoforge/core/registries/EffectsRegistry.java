package me.itzme1on.alcocraftplus.neoforge.core.registries;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.neoforge.core.effects.AttractEffect;
import me.itzme1on.alcocraftplus.neoforge.core.effects.FreezeEffect;
import me.itzme1on.alcocraftplus.neoforge.core.effects.PhantomEffect;
import me.itzme1on.alcocraftplus.neoforge.core.effects.WitherEffect;
import me.itzme1on.alcocraftplus.neoforge.core.utils.ColorUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class EffectsRegistry {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(AlcoCraftPlus.MOD_ID, Registries.MOB_EFFECT);

    public static final RegistrySupplier<PhantomEffect> PHANTOM = MOB_EFFECTS.register("phantom", () ->
            new PhantomEffect(MobEffectCategory.BENEFICIAL, ColorUtil.getColorFromRGB(58, 70, 123)));

    public static final RegistrySupplier<AttractEffect> ATTRACT = MOB_EFFECTS.register("attract", () ->
            new AttractEffect(MobEffectCategory.BENEFICIAL, ColorUtil.getColorFromRGB(170, 14, 1)));

    public static final RegistrySupplier<FreezeEffect> FREEZE = MOB_EFFECTS.register("freeze", () ->
            new FreezeEffect(MobEffectCategory.BENEFICIAL, ColorUtil.getColorFromRGB(143, 181, 246)));

    public static final RegistrySupplier<WitherEffect> WITHER = MOB_EFFECTS.register("wither", () ->
            new WitherEffect(MobEffectCategory.BENEFICIAL, ColorUtil.getColorFromRGB(29, 5, 3)));

    public static void register() {
        MOB_EFFECTS.register();
    }
}
