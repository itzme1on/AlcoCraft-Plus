package me.itzme1on.alcocraftplus.misc;

import me.itzme1on.alcocraftplus.items.AlcoItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class AlcoTabGroup {
    public static final CreativeModeTab GROUP = new CreativeModeTab("alcotabgroup") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(AlcoItems.MUG_OF_CHORUS_ALE.get());
        }
    };
}
