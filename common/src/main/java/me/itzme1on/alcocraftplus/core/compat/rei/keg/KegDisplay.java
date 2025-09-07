package me.itzme1on.alcocraftplus.core.compat.rei.keg;

import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.core.recipes.KegRecipes;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.core.RegistryAccess;

import java.util.Collections;
import java.util.List;

public class KegDisplay extends BasicDisplay {
    public static final CategoryIdentifier<KegDisplay> CATEGORY = CategoryIdentifier.of(AlcoCraftPlus.MOD_ID, "beer_brewing");

    public KegDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public static KegDisplay of(KegRecipes recipe) {
        List<EntryIngredient> inputs = EntryIngredients.ofIngredients(recipe.getIngredients());
        List<EntryIngredient> outputs = Collections.singletonList(EntryIngredients.of(recipe.getResultItem(RegistryAccess.EMPTY)));
        
        return new KegDisplay(inputs, outputs);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return CATEGORY;
    }
}
