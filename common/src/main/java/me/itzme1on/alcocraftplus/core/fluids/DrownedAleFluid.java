package me.itzme1on.alcocraftplus.core.fluids;

import dev.architectury.core.fluid.SimpleArchitecturyFluidAttributes;
import dev.architectury.core.fluid.SimpleFlowingFluid;
import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.core.registries.BlocksRegistry;
import me.itzme1on.alcocraftplus.core.registries.FluidsRegistry;
import me.itzme1on.alcocraftplus.core.registries.ItemsRegistry;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.block.state.StateDefinition;

public abstract class DrownedAleFluid extends SimpleFlowingFluid {

    public static final Identifier STILL_TEXTURE = new Identifier(AlcoCraftPlus.MOD_ID, "fluid/drowned_ale_still");
    public static final Identifier FLOWING_TEXTURE = new Identifier(AlcoCraftPlus.MOD_ID, "fluid/drowned_ale_flow");
    public static final Identifier OVERLAY_TEXTURE = STILL_TEXTURE;

    public static final SimpleArchitecturyFluidAttributes ATTRIBUTES = SimpleArchitecturyFluidAttributes.builder()
            .density(1010)
            .viscosity(1050)
            .temperature(290)
            .luminosity(0)
            .sound(SoundEvents.BUCKET_FILL_LAVA, SoundEvents.BUCKET_EMPTY_LAVA) // Murkier sound?
            .overlay(OVERLAY_TEXTURE)
            .tintColor(0xFF2F4F4F) // Dark Slate Gray / Murky Blue-Green
            .translationKey("fluid.alcocraftplus.drowned_ale")
            .texture(STILL_TEXTURE)
            .build(FluidsRegistry.DROWNED_ALE_STILL, FluidsRegistry.DROWNED_ALE_FLOWING);

    protected DrownedAleFluid(SimpleArchitecturyFluidAttributes attributes) {
        super(FluidsRegistry.DROWNED_ALE_STILL,
                FluidsRegistry.DROWNED_ALE_FLOWING,
                BlocksRegistry.DROWNED_ALE_BLOCK,
                ItemsRegistry.DROWNED_ALE_BUCKET,
                attributes);
    }

    public static class Flowing extends DrownedAleFluid {
        public Flowing() {
            super(ATTRIBUTES.texture(FLOWING_TEXTURE));
        }

        @Override
        protected void registerDefaultState(Fluid fluid, StateDefinition.Builder<Fluid, FluidState> builder) {
            super.registerDefaultState(fluid, builder);
            builder.add(LEVEL);
        }

        @Override
        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }

        @Override
        public boolean isSource(FluidState state) {
            return false;
        }
    }

    public static class Still extends DrownedAleFluid {
        public Still() {
            super(ATTRIBUTES);
        }

        @Override
        public int getAmount(FluidState state) {
            return 8;
        }

        @Override
        public boolean isSource(FluidState state) {
            return true;
        }
    }
}