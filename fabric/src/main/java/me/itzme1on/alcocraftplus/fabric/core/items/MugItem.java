package me.itzme1on.alcocraftplus.fabric.core.items;

import me.itzme1on.alcocraftplus.fabric.core.registries.ItemsRegistry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class MugItem extends BlockItem {
    public MugItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public @NotNull SoundEvent getEatingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    @Override
    public @NotNull SoundEvent getDrinkingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (entity instanceof Player player) {
            if (player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
                player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ItemsRegistry.MUG.get()));
            } else if (!player.getInventory().add(new ItemStack(ItemsRegistry.MUG.get()))) {
                player.drop(new ItemStack(ItemsRegistry.MUG.get()), false);
            }
        }

        return super.finishUsingItem(stack, level, entity);
    }
}
