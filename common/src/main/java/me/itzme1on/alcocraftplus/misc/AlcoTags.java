package me.itzme1on.alcocraftplus.misc;

import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class AlcoTags {
    public static class Items {
        public static final TagKey<Item> BEER_MUGS = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(AlcoCraftPlus.MOD_ID, "beer_mugs"));
    }
}
