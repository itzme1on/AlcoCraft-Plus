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

public abstract class MagnetPilsnerFluid extends SimpleFlowingFluid {

    public static final Identifier STILL_TEXTURE = new Identifier(AlcoCraftPlus.MOD_ID, "fluid/magnet_pilsner_still");
    public static final Identifier FLOWING_TEXTURE = new Identifier(AlcoCraftPlus.MOD_ID, "fluid/magnet_pilsner_flow");
    public static final Identifier OVERLAY_TEXTURE = STILL_TEXTURE;

    public static final SimpleArchitecturyFluidAttributes ATTRIBUTES = SimpleArchitecturyFluidAttributes.builder()
            .density(1050) // Slightly denser?
            .viscosity(1000)
            .temperature(295)
            .luminosity(0)
            .sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY)
            .overlay(OVERLAY_TEXTURE)
            .tintColor(0xFFFFD700) // Golden color
            .translationKey("fluid.alcocraftplus.magnet_pilsner")
            .texture(STILL_TEXTURE)
            .build(FluidsRegistry.MAGNET_PILSNER_STILL, FluidsRegistry.MAGNET_PILSNER_FLOWING);

    protected MagnetPilsnerFluid(SimpleArchitecturyFluidAttributes attributes) {
        super(FluidsRegistry.MAGNET_PILSNER_STILL,
                FluidsRegistry.MAGNET_PILSNER_FLOWING,
                BlocksRegistry.MAGNET_PILSNER_BLOCK,
                ItemsRegistry.MAGNET_PILSNER_BUCKET,
                attributes);
    }

    public static class Flowing extends MagnetPilsnerFluid {
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

    public static class Still extends MagnetPilsnerFluid {
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