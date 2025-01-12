package me.itzme1on.alcocraftplus.core.registries;

import dev.architectury.registry.CreativeTabRegistry;
import me.itzme1on.alcocraftplus.core.utils.IdentifierUtil;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class TabGroupRegistry {
    public static final CreativeModeTab TAB = CreativeTabRegistry.create(new IdentifierUtil("tab"), () -> new ItemStack(ItemsRegistry.MUG.get()));
}
