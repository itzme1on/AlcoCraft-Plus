package me.itzme1on.alcocraftplus.core.registries;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.core.items.MugItem;
import me.itzme1on.alcocraftplus.core.misc.BeerProperties;
import me.itzme1on.alcocraftplus.core.utils.IdentifierUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

import java.util.function.Function;
import java.util.function.Supplier;

public class ItemsRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(AlcoCraftPlus.MOD_ID, Registries.ITEM);
    public static final RegistrySupplier<Item> HOP = registerItemWithProps(
            "hop",
            Item::new,
            () -> new Item.Properties()
                    .food(BeerProperties.HOP)
                    .component(
                            DataComponents.CONSUMABLE,
                            Consumable.builder()
                                    .consumeSeconds(0.8f)
                                    .animation(ItemUseAnimation.EAT)
                                    .sound(SoundEvents.GENERIC_EAT)
                                    .onConsume(new ApplyStatusEffectsConsumeEffect(
                                            new MobEffectInstance(MobEffects.NAUSEA, 100, 0), 1.0f))
                                    .build()
                    )
    );
    public static final RegistrySupplier<Item> HOP_SEEDS = registerItemWithProps(
            "hop_seeds",
            props -> new net.minecraft.world.item.BlockItem(BlocksRegistry.HOP.get(), props),
            Item.Properties::new
    );
    public static final RegistrySupplier<Item> DRY_SEEDS = registerItemWithProps(
            "dry_seeds",
            Item::new,
            Item.Properties::new
    );
    public static final RegistrySupplier<Item> MUG = registerItemWithProps(
            "mug",
            props -> new MugItem(BlocksRegistry.MUG.get(), props),
            Item.Properties::new
    );
    public static final RegistrySupplier<Item> KVASS = registerItemWithProps(
            "kvass",
            props -> new MugItem(BlocksRegistry.KVASS.get(), props),
            () -> drinkProps(
                    BeerProperties.KVASS,
                    new MobEffectInstance(MobEffects.SATURATION, 2400, 0), 1.0f,
                    new MobEffectInstance(MobEffects.HEALTH_BOOST, 600, 1), 1.0f
            )
    );
    public static final RegistrySupplier<Item> CHORUS_ALE = registerItemWithProps(
            "chorus_ale",
            props -> new MugItem(BlocksRegistry.CHORUS_ALE.get(), props),
            () -> drinkProps(
                    BeerProperties.CHORUS_ALE,
                    new MobEffectInstance(MobEffects.LUCK, 1200, 0), 1.0f,
                    new MobEffectInstance(MobEffects.NAUSEA, 200, 0), 1.0f
            )
    );
    public static final RegistrySupplier<Item> DIGGER_BITTER = registerItemWithProps(
            "digger_bitter",
            props -> new MugItem(BlocksRegistry.DIGGER_BITTER.get(), props),
            () -> drinkProps(
                    BeerProperties.DIGGER_BITTER,
                    new MobEffectInstance(MobEffects.HASTE, 1200, 1), 1.0f,
                    new MobEffectInstance(MobEffects.NAUSEA, 200, 0), 1.0f
            )
    );
    public static final RegistrySupplier<Item> DROWNED_ALE = registerItemWithProps(
            "drowned_ale",
            props -> new MugItem(BlocksRegistry.DROWNED_ALE.get(), props),
            () -> drinkProps(
                    BeerProperties.DROWNED_ALE,
                    new MobEffectInstance(MobEffects.WATER_BREATHING, 6000, 0), 1.0f,
                    new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 3000, 0), 1.0f,
                    new MobEffectInstance(MobEffects.NAUSEA, 200, 0), 1.0f
            )
    );
    public static final RegistrySupplier<Item> ICE_BEER = registerItemWithProps(
            "ice_beer",
            props -> new MugItem(BlocksRegistry.ICE_BEER.get(), props),
            () -> drinkProps(
                    BeerProperties.ICE_BEER,
                    new MobEffectInstance(customEffect(EffectsRegistry.FREEZE.get(), 6000)), 1.0f,
                    new MobEffectInstance(MobEffects.NAUSEA, 200, 0), 1.0f
            )
    );
    public static final RegistrySupplier<Item> LEPRECHAUN_CIDER = registerItemWithProps(
            "leprechaun_cider",
            props -> new MugItem(BlocksRegistry.LEPRECHAUN_CIDER.get(), props),
            () -> drinkProps(
                    BeerProperties.LEPRECHAUN_CIDER,
                    new MobEffectInstance(MobEffects.LUCK, 1200, 0), 1.0f,
                    new MobEffectInstance(MobEffects.NAUSEA, 200, 0), 1.0f
            )
    );
    public static final RegistrySupplier<Item> MAGNET_PILSNER = registerItemWithProps(
            "magnet_pilsner",
            props -> new MugItem(BlocksRegistry.MAGNET_PILSNER.get(), props),
            () -> drinkProps(
                    BeerProperties.MAGNET_PILSNER,
                    new MobEffectInstance(customEffect(EffectsRegistry.ATTRACT.get(), 6000)), 1.0f,
                    new MobEffectInstance(MobEffects.NAUSEA, 200, 0), 1.0f
            )
    );
    public static final RegistrySupplier<Item> NETHER_PORTER = registerItemWithProps(
            "nether_porter",
            props -> new MugItem(BlocksRegistry.NETHER_PORTER.get(), props),
            () -> drinkProps(
                    BeerProperties.NETHER_PORTER,
                    new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 1), 1.0f,
                    new MobEffectInstance(MobEffects.NAUSEA, 200, 0), 1.0f
            )
    );
    public static final RegistrySupplier<Item> NETHER_STAR_LAGER = registerItemWithProps(
            "nether_star_lager",
            props -> new MugItem(BlocksRegistry.NETHER_STAR_LAGER.get(), props),
            () -> drinkProps(
                    BeerProperties.NETHER_STAR_LAGER,
                    new MobEffectInstance(MobEffects.NAUSEA, 200, 0), 1.0f,
                    new MobEffectInstance(MobEffects.LUCK, 2400, 0), 1.0f,
                    new MobEffectInstance(MobEffects.JUMP_BOOST, 6000, 0), 1.0f,
                    new MobEffectInstance(MobEffects.SPEED, 6000, 1), 1.0f,
                    new MobEffectInstance(MobEffects.HEALTH_BOOST, 6000, 0), 1.0f,
                    new MobEffectInstance(MobEffects.STRENGTH, 3600, 0), 1.0f
            )
    );
    public static final RegistrySupplier<Item> NIGHT_RAUCH = registerItemWithProps(
            "night_rauch",
            props -> new MugItem(BlocksRegistry.NIGHT_RAUCH.get(), props),
            () -> drinkProps(
                    BeerProperties.NIGHT_RAUCH,
                    new MobEffectInstance(customEffect(EffectsRegistry.PHANTOM.get(), 12000)), 1.0f,
                    new MobEffectInstance(MobEffects.NIGHT_VISION, 2400, 0), 1.0f,
                    new MobEffectInstance(MobEffects.NAUSEA, 200, 0), 1.0f
            )
    );
    public static final RegistrySupplier<Item> SUN_PALE_ALE = registerItemWithProps(
            "sun_pale_ale",
            props -> new MugItem(BlocksRegistry.SUN_PALE_ALE.get(), props),
            () -> drinkProps(
                    BeerProperties.SUN_PALE_ALE,
                    new MobEffectInstance(MobEffects.REGENERATION, 3600, 0), 1.0f,
                    new MobEffectInstance(MobEffects.NAUSEA, 200, 0), 1.0f
            )
    );
    public static final RegistrySupplier<Item> WITHER_STOUT = registerItemWithProps(
            "wither_stout",
            props -> new MugItem(BlocksRegistry.WITHER_STOUT.get(), props),
            () -> drinkProps(
                    BeerProperties.WITHER_STOUT,
                    new MobEffectInstance(customEffect(EffectsRegistry.WITHER.get(), 3600)), 1.0f,
                    new MobEffectInstance(MobEffects.NAUSEA, 200, 0), 1.0f
            )
    );

    private ItemsRegistry() {
    }

    public static void register() {
        ITEMS.register();
    }

    private static <T extends Item> RegistrySupplier<T> registerItemWithProps(
            String name,
            Function<Item.Properties, T> function,
            Supplier<Item.Properties> supplier
    ) {
        return ITEMS.register(name, () -> function.apply(supplier.get().setId(id(name))));
    }

    private static ResourceKey<Item> id(String string) {
        return ResourceKey.create(Registries.ITEM, IdentifierUtil.of(string));
    }

    private static Item.Properties drinkProps(net.minecraft.world.food.FoodProperties food,
                                              Object... effectPairs) {
        var consume = Consumable.builder()
                .consumeSeconds(1.6f)
                .animation(ItemUseAnimation.DRINK)
                .hasConsumeParticles(false)
                .sound(SoundEvents.HONEY_DRINK)
                .soundAfterConsume(SoundEvents.HONEY_DRINK);

        for (int i = 0; i + 1 < effectPairs.length; i += 2) {
            var inst = (MobEffectInstance) effectPairs[i];
            float chance = (float) effectPairs[i + 1];

            consume.onConsume(new ApplyStatusEffectsConsumeEffect(inst, chance));
        }

        return new Item.Properties()
                .stacksTo(16)
                .food(food)
                .component(DataComponents.CONSUMABLE, consume.build())
                .usingConvertsTo(MUG.get());
    }

    private static MobEffectInstance customEffect(MobEffect effect, int duration) {
        return new MobEffectInstance(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect), duration, 0);
    }
}

