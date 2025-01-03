package me.itzme1on.alcocraftplus.core.utils;

import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import net.minecraft.resources.ResourceLocation;

public class IdentifierUtil extends ResourceLocation {
    public IdentifierUtil(String path) {
        super(AlcoCraftPlus.MOD_ID, path);
    }

    public static ResourceLocation create(String path) {
        return new ResourceLocation(AlcoCraftPlus.MOD_ID, path);
    }
}
