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

public abstract class LeprechaunCiderFluid extends SimpleFlowingFluid {

    public static final Identifier STILL_TEXTURE = new Identifier(AlcoCraftPlus.MOD_ID, "fluid/leprechaun_cider_still");
    public static final Identifier FLOWING_TEXTURE = new Identifier(AlcoCraftPlus.MOD_ID, "fluid/leprechaun_cider_flow");
    public static final Identifier OVERLAY_TEXTURE = STILL_TEXTURE;

    public static final SimpleArchitecturyFluidAttributes ATTRIBUTES = SimpleArchitecturyFluidAttributes.builder()
            .density(1000)
            .viscosity(1000)
            .temperature(295)
            .luminosity(2) // A bit sparkly?
            .sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY)
            .overlay(OVERLAY_TEXTURE)
            .tintColor(0xFFADFF2F) // Green-Yellow color
            .translationKey("fluid.alcocraftplus.leprechaun_cider")
            .texture(STILL_TEXTURE)
            .build(FluidsRegistry.LEPRECHAUN_CIDER_STILL, FluidsRegistry.LEPRECHAUN_CIDER_FLOWING);

    protected LeprechaunCiderFluid(SimpleArchitecturyFluidAttributes attributes) {
        super(FluidsRegistry.LEPRECHAUN_CIDER_STILL,
                FluidsRegistry.LEPRECHAUN_CIDER_FLOWING,
                BlocksRegistry.LEPRECHAUN_CIDER_BLOCK,
                ItemsRegistry.LEPRECHAUN_CIDER_BUCKET,
                attributes);
    }

    public static class Flowing extends LeprechaunCiderFluid {
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

    public static class Still extends LeprechaunCiderFluid {
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