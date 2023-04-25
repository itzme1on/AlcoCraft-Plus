package me.itzme1on.alcocraftplus.misc;

import dev.architectury.registry.CreativeTabRegistry;
import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.items.AlcoItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class AlcoTabGroup {
    public static final CreativeModeTab ALCOCRAFTPLUS = CreativeTabRegistry.create(new ResourceLocation(AlcoCraftPlus.MOD_ID, "tab"),
            () -> new ItemStack(AlcoItems.HOP.get()));
}
