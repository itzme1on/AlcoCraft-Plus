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

public abstract class NetherStarLagerFluid extends SimpleFlowingFluid {

    public static final Identifier STILL_TEXTURE = new Identifier(AlcoCraftPlus.MOD_ID, "fluid/nether_star_lager_still");
    public static final Identifier FLOWING_TEXTURE = new Identifier(AlcoCraftPlus.MOD_ID, "fluid/nether_star_lager_flow");
    public static final Identifier OVERLAY_TEXTURE = STILL_TEXTURE;

    public static final SimpleArchitecturyFluidAttributes ATTRIBUTES = SimpleArchitecturyFluidAttributes.builder()
            .density(1000)
            .viscosity(1000)
            .temperature(300)
            .luminosity(10) // Should glow brightly
            .sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY)
            .overlay(OVERLAY_TEXTURE)
            .tintColor(0xFFFFFFE0) // Light Yellow / White color
            .translationKey("fluid.alcocraftplus.nether_star_lager")
            .texture(STILL_TEXTURE)
            .build(FluidsRegistry.NETHER_STAR_LAGER_STILL, FluidsRegistry.NETHER_STAR_LAGER_FLOWING);

    protected NetherStarLagerFluid(SimpleArchitecturyFluidAttributes attributes) {
        super(FluidsRegistry.NETHER_STAR_LAGER_STILL,
                FluidsRegistry.NETHER_STAR_LAGER_FLOWING,
                BlocksRegistry.NETHER_STAR_LAGER_BLOCK,
                ItemsRegistry.NETHER_STAR_LAGER_BUCKET,
                attributes);
    }

    public static class Flowing extends NetherStarLagerFluid {
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

    public static class Still extends NetherStarLagerFluid {
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