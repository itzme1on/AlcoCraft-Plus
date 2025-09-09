package me.itzme1on.alcocraftplus.core.registries;

import net.minecraft.world.level.block.ComposterBlock;

public class CompostableRegistry {
    private CompostableRegistry() {}
    public static void register() {
        ComposterBlock.COMPOSTABLES.put(ItemsRegistry.HOP.get(), 0.3f);
        ComposterBlock.COMPOSTABLES.put(ItemsRegistry.HOP_SEEDS.get(), 0.3f);
        ComposterBlock.COMPOSTABLES.put(ItemsRegistry.DRY_SEEDS.get(), 0.3f);
    }
}
