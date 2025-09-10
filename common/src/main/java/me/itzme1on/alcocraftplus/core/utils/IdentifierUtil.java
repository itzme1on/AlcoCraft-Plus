package me.itzme1on.alcocraftplus.core.utils;

import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("unused")
public final class IdentifierUtil {
    private IdentifierUtil() {
    }

    public static ResourceLocation of(String path) {
        return ResourceLocation.fromNamespaceAndPath(AlcoCraftPlus.MOD_ID, path);
    }

    public static ResourceLocation asResourceLocation(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }
}
