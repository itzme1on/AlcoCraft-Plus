package me.itzme1on.alcocraftplus.fabric.mixin;

import me.itzme1on.alcocraftplus.core.blocks.keg.Keg;
import me.itzme1on.alcocraftplus.core.blocks.keg.KegEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers; // Use Containers for dropping items (works >= 1.19.4, fallback ItemScatterer if needed)
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keg.class) // Target the common Keg class
public abstract class KegMixin {

    // Inject code into the playerWillDestroy method
    @Inject(method = "playerWillDestroy", at = @At("HEAD")) // Inject at the beginning of the method
    private void alcocraftplus$fabricDropInventory(Level level, BlockPos pos, BlockState state, Player player, CallbackInfo ci) {
        // Only run on server and if player is not in creative mode (redundant check, but safe)
        if (!level.isClientSide() && !player.isCreative()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof KegEntity kegEntity) {
                // Use Fabric/Vanilla's way to drop inventory contents
                Containers.dropContents(level, pos, kegEntity.getInventory());
                // For older MC versions (<1.19.4) or if Containers doesn't work as expected,
                // you might use ItemScatterer:
                // ItemScatterer.spawn(level, pos, kegEntity.getInventory());

                // Important: Clear the inventory inside the entity AFTER dropping
                // to prevent potential duplication if the method continues or is called again.
                kegEntity.getInventory().clear();
            }
        }
    }
}