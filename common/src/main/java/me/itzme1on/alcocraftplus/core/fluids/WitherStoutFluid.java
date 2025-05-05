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

public abstract class WitherStoutFluid extends SimpleFlowingFluid {

    public static final Identifier STILL_TEXTURE = new Identifier(AlcoCraftPlus.MOD_ID, "fluid/wither_stout_still");
    public static final Identifier FLOWING_TEXTURE = new Identifier(AlcoCraftPlus.MOD_ID, "fluid/wither_stout_flow");
    public static final Identifier OVERLAY_TEXTURE = STILL_TEXTURE;

    public static final SimpleArchitecturyFluidAttributes ATTRIBUTES = SimpleArchitecturyFluidAttributes.builder()
            .density(1050)
            .viscosity(1400) // Thick stout
            .temperature(295)
            .luminosity(0)
            .sound(SoundEvents.BUCKET_FILL_LAVA, SoundEvents.BUCKET_EMPTY_LAVA) // Maybe a deeper sound
            .overlay(OVERLAY_TEXTURE)
            .tintColor(0xFF1C1C1C) // Very Dark Grey / Black color
            .translationKey("fluid.alcocraftplus.wither_stout")
            .texture(STILL_TEXTURE)
            .build(FluidsRegistry.WITHER_STOUT_STILL, FluidsRegistry.WITHER_STOUT_FLOWING);

    protected WitherStoutFluid(SimpleArchitecturyFluidAttributes attributes) {
        super(FluidsRegistry.WITHER_STOUT_STILL,
                FluidsRegistry.WITHER_STOUT_FLOWING,
                BlocksRegistry.WITHER_STOUT_BLOCK,
                ItemsRegistry.WITHER_STOUT_BUCKET,
                attributes);
    }

    public static class Flowing extends WitherStoutFluid {
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

    public static class Still extends WitherStoutFluid {
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