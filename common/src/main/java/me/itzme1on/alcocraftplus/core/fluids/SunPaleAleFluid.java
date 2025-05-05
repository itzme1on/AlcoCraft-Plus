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

public abstract class SunPaleAleFluid extends SimpleFlowingFluid {

    public static final Identifier STILL_TEXTURE = new Identifier(AlcoCraftPlus.MOD_ID, "fluid/sun_pale_ale_still");
    public static final Identifier FLOWING_TEXTURE = new Identifier(AlcoCraftPlus.MOD_ID, "fluid/sun_pale_ale_flow");
    public static final Identifier OVERLAY_TEXTURE = STILL_TEXTURE;

    public static final SimpleArchitecturyFluidAttributes ATTRIBUTES = SimpleArchitecturyFluidAttributes.builder()
            .density(1010)
            .viscosity(1050)
            .temperature(300)
            .luminosity(5) // Sunny glow
            .sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY)
            .overlay(OVERLAY_TEXTURE)
            .tintColor(0xFFFFA500) // Bright Orange/Yellow color
            .translationKey("fluid.alcocraftplus.sun_pale_ale")
            .texture(STILL_TEXTURE)
            .build(FluidsRegistry.SUN_PALE_ALE_STILL, FluidsRegistry.SUN_PALE_ALE_FLOWING);

    protected SunPaleAleFluid(SimpleArchitecturyFluidAttributes attributes) {
        super(FluidsRegistry.SUN_PALE_ALE_STILL,
                FluidsRegistry.SUN_PALE_ALE_FLOWING,
                BlocksRegistry.SUN_PALE_ALE_BLOCK,
                ItemsRegistry.SUN_PALE_ALE_BUCKET,
                attributes);
    }

    public static class Flowing extends SunPaleAleFluid {
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

    public static class Still extends SunPaleAleFluid {
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