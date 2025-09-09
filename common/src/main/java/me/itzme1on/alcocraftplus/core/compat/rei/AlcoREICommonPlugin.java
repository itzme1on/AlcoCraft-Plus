package me.itzme1on.alcocraftplus.core.compat.rei;

import me.itzme1on.alcocraftplus.core.compat.rei.keg.KegDisplay;
import me.itzme1on.alcocraftplus.core.recipes.KegRecipes;
import me.itzme1on.alcocraftplus.core.registries.RecipesRegistry;
import me.shedaniel.rei.api.client.registry.display.reason.DisplayAdditionReason;
import me.shedaniel.rei.api.client.registry.display.reason.DisplayAdditionReasons;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REICommonPlugin;
import me.shedaniel.rei.api.common.registry.display.ServerDisplayRegistry;
import net.minecraft.world.item.crafting.RecipeHolder;

public class AlcoREICommonPlugin implements REICommonPlugin {
    @Override
    public void registerDisplays(ServerDisplayRegistry registry) {
        registry.beginRecipeFiller(KegRecipes.class)
                .filterType(RecipesRegistry.KEG_RECIPE_TYPE.get())
                .filterWithReason((RecipeHolder<KegRecipes> h, DisplayAdditionReasons reasons) ->
                        reasons.has(DisplayAdditionReason.RECIPE_MANAGER))
                .fill((RecipeHolder<KegRecipes> holder) -> KegDisplay.of(holder.value()));
    }

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        registry.register(KegDisplay.SERIALIZER_ID, KegDisplay.SERIALIZER);
    }
}
