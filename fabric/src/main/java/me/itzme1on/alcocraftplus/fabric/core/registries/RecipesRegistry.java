package me.itzme1on.alcocraftplus.fabric.core.registries;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.core.recipes.KegRecipes;
import me.itzme1on.alcocraftplus.core.utils.IdentifierUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.function.Supplier;

public class RecipesRegistry {
    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(AlcoCraftPlus.MOD_ID, Registries.RECIPE_SERIALIZER);
    private static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(AlcoCraftPlus.MOD_ID, Registries.RECIPE_TYPE);

    public static final RegistrySupplier<RecipeType<KegRecipes>> KEG_RECIPE_TYPE = create();

    public static final RegistrySupplier<RecipeSerializer<KegRecipes>> KEG_RECIPE_SERIALIZER = create(KegRecipes.Serializer::new);

    private static <T extends Recipe<?>> RegistrySupplier<RecipeSerializer<T>> create(Supplier<RecipeSerializer<T>> serializer) {
        return RECIPE_SERIALIZERS.register(IdentifierUtil.create("beer_brewing"), serializer);
    }

    private static <T extends Recipe<?>> RegistrySupplier<RecipeType<T>> create() {
        Supplier<RecipeType<T>> type = () -> new RecipeType<>() {
            @Override
            public String toString() {
                return "beer_brewing";
            }
        };
        return RECIPE_TYPES.register(IdentifierUtil.create("beer_brewing"), type);
    }

    public static void register() {
        RECIPE_SERIALIZERS.register();
        RECIPE_TYPES.register();

        AlcoCraftPlus.LOGGER.info("Initializing recipes for AlcoCraftPlus");
    }
}
