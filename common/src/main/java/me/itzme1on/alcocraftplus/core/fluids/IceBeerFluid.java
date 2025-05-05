package me.itzme1on.alcocraftplus.core.fluids;

import dev.architectury.core.fluid.SimpleArchitecturyFluidAttributes;
import dev.architectury.core.fluid.SimpleFlowingFluid;
import dev.architectury.core.item.ArchitecturyBucketItem; // Use Architectury's bucket
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import me.itzme1on.alcocraftplus.AlcoCraftPlus; // Assuming your main mod class is here
import me.itzme1on.alcocraftplus.core.registries.ItemsRegistry; // Assuming your common Item registry is here
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BucketItem; // Need vanilla BucketItem for type
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items; // Need vanilla Items for empty bucket
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor; // For block properties

import java.util.function.Supplier;

public abstract class IceBeerFluid extends SimpleFlowingFluid {

    // --- Registration Setup (Move this to a dedicated FluidsRegistry class later) ---
    // It's better practice to have a central registry, but we can define it here temporarily
    // Make sure you have initialized ITEMS in your ItemsRegistry class similarly
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(AlcoCraftPlus.MOD_ID, Registries.FLUID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(AlcoCraftPlus.MOD_ID, Registries.BLOCK);
    // Use the existing ItemsRegistry DeferredRegister if possible, otherwise create one here
    // public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(AlcoCraftPlus.MOD_ID, Registries.ITEM);

    // --- Fluid Properties ---
    // Define the visual and physical properties of your fluid
    // Textures will be looked for at:
    // assets/alcocraftplus/textures/fluid/ice_beer_still.png
    // assets/alcocraftplus/textures/fluid/ice_beer_flow.png
    // assets/alcocraftplus/textures/fluid/ice_beer_overlay.png (Optional, often same as still)
    public static final Identifier STILL_TEXTURE = new Identifier(AlcoCraftPlus.MOD_ID, "fluid/ice_beer_still");
    public static final Identifier FLOWING_TEXTURE = new Identifier(AlcoCraftPlus.MOD_ID, "fluid/ice_beer_flow");
    public static final Identifier OVERLAY_TEXTURE = new Identifier(AlcoCraftPlus.MOD_ID, "fluid/ice_beer_overlay"); // Or use STILL_TEXTURE

    // Base properties using Architectury's builder
    // Adjust color, sounds, etc. as needed
    public static final SimpleArchitecturyFluidAttributes ATTRIBUTES = SimpleArchitecturyFluidAttributes.builder()
            .density(1000) // Like water
            .viscosity(1000) // Like water
            .temperature(280) // Slightly cooler than water (300)
            .luminosity(0) // Not glowing
            .sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY) // Standard bucket sounds
            .overlay(OVERLAY_TEXTURE) // Optional overlay texture
            .tintColor(0xFFB0E0E6) // Light blueish color for ice beer (ARGB format)
            .translationKey("fluid.alcocraftplus.ice_beer"); // For lang file: "Ice Beer"

    // --- Fluid Instances ---
    public static final RegistrySupplier<FlowingFluid> STILL = FLUIDS.register(
            "ice_beer", // Fluid registry name
            () -> new Still(ATTRIBUTES
                    .texture(STILL_TEXTURE) // Set still texture
                    .build(IceBeerFluid.STILL, IceBeerFluid.FLOWING) // Build attributes linking still/flowing
            )
    );
    public static final RegistrySupplier<FlowingFluid> FLOWING = FLUIDS.register(
            "ice_beer_flowing", // Fluid registry name
            () -> new Flowing(ATTRIBUTES
                    .texture(FLOWING_TEXTURE) // Set flowing texture
                    .build(IceBeerFluid.STILL, IceBeerFluid.FLOWING) // Build attributes linking still/flowing
            )
    );

    // --- Fluid Block ---
    // Needs to be registered as a Block
    public static final RegistrySupplier<LiquidBlock> BLOCK = BLOCKS.register(
            "ice_beer_block", // Block registry name
            () -> new LiquidBlock(
                    STILL, // Reference the still fluid supplier
                    BlockBehaviour.Properties.of().mapColor(MapColor.WATER).replaceable().noCollission().strength(100.0F).noLootTable() // Standard liquid properties
            )
    );

    // --- Bucket Item ---
    // Needs to be registered as an Item (ideally in ItemsRegistry)
    // Using ItemsRegistry.ITEMS assuming it exists and is initialized
    public static final RegistrySupplier<BucketItem> BUCKET = ItemsRegistry.ITEMS.register(
            "ice_beer_bucket", // Item registry name
            () -> new ArchitecturyBucketItem(
                    STILL, // Reference the still fluid supplier
                    new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1) // Standard bucket properties
            )
    );
    // If ItemsRegistry.ITEMS doesn't exist or isn't accessible, use the temporary ITEMS register above:
    /*
    public static final RegistrySupplier<BucketItem> BUCKET = ITEMS.register(
             "ice_beer_bucket", // Item registry name
             () -> new ArchitecturyBucketItem(
                     STILL, // Reference the still fluid supplier
                     new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1) // Standard bucket properties
             )
     );
    */


    // --- Abstract Fluid Class Methods ---
    // These methods link the fluid instances to their corresponding block and bucket item

    protected IceBeerFluid(Supplier<? extends Fluid> still, Supplier<? extends Fluid> flowing, Supplier<? extends LiquidBlock> block, Supplier<? extends BucketItem> bucket, SimpleArchitecturyFluidAttributes attributes) {
        super(still, flowing, block, bucket, attributes);
    }

    // --- Concrete Fluid Classes ---

    public static class Flowing extends IceBeerFluid {
        public Flowing(SimpleArchitecturyFluidAttributes attributes) {
            super(STILL, FLOWING, BLOCK, BUCKET, attributes);
        }

        @Override
        protected void registerDefaultState(Fluid state, net.minecraft.world.level.block.state.StateDefinition.Builder<Fluid, net.minecraft.world.level.material.FluidState> builder) {
            super.registerDefaultState(state, builder);
            // Add LEVEL property for flowing fluids
            builder.add(LEVEL);
        }

        @Override
        public int getAmount(net.minecraft.world.level.material.FluidState state) {
            // Get fluid level from state
            return state.getValue(LEVEL);
        }

        @Override
        public boolean isSource(net.minecraft.world.level.material.FluidState state) {
            // Flowing fluid is never a source
            return false;
        }
    }

    public static class Still extends IceBeerFluid {
        public Still(SimpleArchitecturyFluidAttributes attributes) {
            super(STILL, FLOWING, BLOCK, BUCKET, attributes);
        }

        @Override
        public int getAmount(net.minecraft.world.level.material.FluidState state) {
            // Source block is always level 8
            return 8;
        }

        @Override
        public boolean isSource(net.minecraft.world.level.material.FluidState state) {
            // Still fluid is always a source
            return true;
        }
    }
}