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

public abstract class NetherPorterFluid extends SimpleFlowingFluid {

    public static final Identifier STILL_TEXTURE = new Identifier(AlcoCraftPlus.MOD_ID, "fluid/nether_porter_still");
    public static final Identifier FLOWING_TEXTURE = new Identifier(AlcoCraftPlus.MOD_ID, "fluid/nether_porter_flow");
    public static final Identifier OVERLAY_TEXTURE = STILL_TEXTURE;

    public static final SimpleArchitecturyFluidAttributes ATTRIBUTES = SimpleArchitecturyFluidAttributes.builder()
            .density(1040)
            .viscosity(1300)
            .temperature(350) // Warmer
            .luminosity(3) // Glows a bit?
            .sound(SoundEvents.BUCKET_FILL_LAVA, SoundEvents.BUCKET_EMPTY_LAVA) // Lava sounds
            .overlay(OVERLAY_TEXTURE)
            .tintColor(0xFF8B0000) // Dark Red color
            .translationKey("fluid.alcocraftplus.nether_porter")
            .texture(STILL_TEXTURE)
            .build(FluidsRegistry.NETHER_PORTER_STILL, FluidsRegistry.NETHER_PORTER_FLOWING);

    protected NetherPorterFluid(SimpleArchitecturyFluidAttributes attributes) {
        super(FluidsRegistry.NETHER_PORTER_STILL,
                FluidsRegistry.NETHER_PORTER_FLOWING,
                BlocksRegistry.NETHER_PORTER_BLOCK,
                ItemsRegistry.NETHER_PORTER_BUCKET,
                attributes);
    }

    public static class Flowing extends NetherPorterFluid {
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

    public static class Still extends NetherPorterFluid {
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