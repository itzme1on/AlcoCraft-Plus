package me.itzme1on.alcocraftplus.core.compat.rei;

import me.itzme1on.alcocraftplus.core.compat.rei.keg.KegCategory;
import me.itzme1on.alcocraftplus.core.compat.rei.keg.KegDisplay;
import me.itzme1on.alcocraftplus.core.registries.BlocksRegistry;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;

public class AlcoREIClientPlugin implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new KegCategory());
        registry.addWorkstations(KegDisplay.CATEGORY, EntryStacks.of(BlocksRegistry.KEG.get()));
    }
}
