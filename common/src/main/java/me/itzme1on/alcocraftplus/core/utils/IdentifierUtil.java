package me.itzme1on.alcocraftplus.core.utils;

import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import net.minecraft.resources.Identifier;

@SuppressWarnings("unused")
public final class IdentifierUtil {
    private IdentifierUtil() {
    }

    public static Identifier of(String path) {
        return Identifier.fromNamespaceAndPath(AlcoCraftPlus.MOD_ID, path);
    }

    public static Identifier asIdentifier(String namespace, String path) {
        return Identifier.fromNamespaceAndPath(namespace, path);
    }
}
