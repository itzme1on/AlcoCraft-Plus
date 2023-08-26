package me.itzme1on.alcocraftplus.misc;

import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.blocks.AlcoBlocks;
import me.itzme1on.alcocraftplus.items.AlcoItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AlcoCraftPlus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AlcoCraftPlusTabGroup {
    @SubscribeEvent
    public static void creativeTabRegistry(CreativeModeTabEvent.Register event) {
        alcoCraftPlusTab = event.registerCreativeModeTab(new ResourceLocation(AlcoCraftPlus.MOD_ID, "alcocraftplustab"), builder ->

            builder.icon(() -> AlcoItems.MUG_OF_CHORUS_ALE.get().getDefaultInstance())
            .displayItems((enabledFlags, populator, hasPermissions) -> {
                populator.accept(AlcoItems.HOP.get());
                populator.accept(AlcoItems.HOP_SEEDS.get());
                populator.accept(AlcoItems.DRY_SEEDS.get());
                populator.accept(AlcoItems.MUG_EMPTY.get());
                populator.accept(AlcoItems.MUG_OF_CHORUS_ALE.get());
                populator.accept(AlcoItems.MUG_OF_DIGGER_BITTER.get());
                populator.accept(AlcoItems.MUG_OF_DROWNED_ALE.get());
                populator.accept(AlcoItems.MUG_OF_ICE_BEER.get());
                populator.accept(AlcoItems.MUG_OF_KVASS.get());
                populator.accept(AlcoItems.MUG_OF_LEPRECHAUN_CIDER.get());
                populator.accept(AlcoItems.MUG_OF_MAGNET_PILSNER.get());
                populator.accept(AlcoItems.MUG_OF_NETHER_PORTER.get());
                populator.accept(AlcoItems.MUG_OF_NIGHT_RAUCH.get());
                populator.accept(AlcoItems.MUG_OF_SUN_PALE_ALE.get());
                populator.accept(AlcoItems.MUG_OF_WITHER_STOUT.get());
                populator.accept(AlcoBlocks.KEG.get());
            }).title(Component.translatable( "itemGroup.alcocraftplustab"))
        );
    }
    static CreativeModeTab alcoCraftPlusTab;
}
