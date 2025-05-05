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

public abstract class NightRauchFluid extends SimpleFlowingFluid {

    public static final Identifier STILL_TEXTURE = new Identifier(AlcoCraftPlus.MOD_ID, "fluid/night_rauch_still");
    public static final Identifier FLOWING_TEXTURE = new Identifier(AlcoCraftPlus.MOD_ID, "fluid/night_rauch_flow");
    public static final Identifier OVERLAY_TEXTURE = STILL_TEXTURE;

    public static final SimpleArchitecturyFluidAttributes ATTRIBUTES = SimpleArchitecturyFluidAttributes.builder()
            .density(1030)
            .viscosity(1250)
            .temperature(295)
            .luminosity(0)
            .sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY)
            .overlay(OVERLAY_TEXTURE)
            .tintColor(0xFF362419) // Very Dark Brown / Smoky Black
            .translationKey("fluid.alcocraftplus.night_rauch")
            .texture(STILL_TEXTURE)
            .build(FluidsRegistry.NIGHT_RAUCH_STILL, FluidsRegistry.NIGHT_RAUCH_FLOWING);

    protected NightRauchFluid(SimpleArchitecturyFluidAttributes attributes) {
        super(FluidsRegistry.NIGHT_RAUCH_STILL,
                FluidsRegistry.NIGHT_RAUCH_FLOWING,
                BlocksRegistry.NIGHT_RAUCH_BLOCK,
                ItemsRegistry.NIGHT_RAUCH_BUCKET,
                attributes);
    }

    public static class Flowing extends NightRauchFluid {
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

    public static class Still extends NightRauchFluid {
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