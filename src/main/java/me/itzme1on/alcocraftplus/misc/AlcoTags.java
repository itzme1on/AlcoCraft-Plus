package me.itzme1on.alcocraftplus.misc;

import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class AlcoTags {
    public static class Items {
        public static final TagKey<Item> BEER_MUGS = tag("beer_mugs");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(AlcoCraftPlus.MOD_ID, name));
        }

        private static TagKey<Item> forgeTag(String name) {
            return ItemTags.create(new ResourceLocation(AlcoCraftPlus.MOD_ID, name));
        }
    }
}
