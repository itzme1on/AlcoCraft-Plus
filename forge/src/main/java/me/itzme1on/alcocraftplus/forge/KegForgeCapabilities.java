package me.itzme1on.alcocraftplus.forge;

import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.core.blocks.keg.KegEntity;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber(modid = AlcoCraftPlus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class KegForgeCapabilities {
    private static final ResourceLocation KEG_CAPS = new ResourceLocation(AlcoCraftPlus.MOD_ID, "keg_caps");

    private KegForgeCapabilities() {
    }

    @SubscribeEvent
    public static void onAttachBlockEntity(AttachCapabilitiesEvent<BlockEntity> event) {
        if (event.getObject() instanceof KegEntity keg) {
            KegCapProvider provider = new KegCapProvider(keg);
            event.addCapability(KEG_CAPS, provider);
            event.addListener(provider::invalidate);
        }
    }

    private static final class KegCapProvider implements ICapabilityProvider {
        private final LazyOptional<IItemHandler> items;

        KegCapProvider(KegEntity keg) {
            this.items = LazyOptional.of(() -> new SidedInvWrapper(keg, Direction.UP));
        }

        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            if (cap == ForgeCapabilities.ITEM_HANDLER) return items.cast();
            return LazyOptional.empty();
        }

        void invalidate() {
            items.invalidate();
        }
    }
}
