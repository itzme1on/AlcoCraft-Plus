package me.itzme1on.alcocraftplus.core.utils;

import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import net.minecraft.resources.Identifier;

public class IdentifierUtil extends Identifier {
    public IdentifierUtil(String path) {
        super(AlcoCraftPlus.MOD_ID, path);
    }

    public static Identifier create(String path) {
        return new Identifier(AlcoCraftPlus.MOD_ID, path);
    }
}
