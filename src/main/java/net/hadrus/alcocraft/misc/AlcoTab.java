package net.hadrus.alcocraft.misc;

import net.hadrus.alcocraft.items.AlcoItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class AlcoTab {
    public static final CreativeModeTab ALCO_TAB = new CreativeModeTab("alcotab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(AlcoItems.SPRUCE_MUG_SUN_PALE_ALE.get());
        }
    };
}
