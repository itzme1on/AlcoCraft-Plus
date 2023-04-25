package me.itzme1on.alcocraftplus.items;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class MugItem extends ItemNameBlockItem {
    public MugItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    @Override
    public SoundEvent getDrinkingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    @Override
    public UseAnim getUseAnimation(@NotNull ItemStack itemStack) {
        return UseAnim.DRINK;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack itemStack) {
        return 32;
    }

    @Override
    public ItemStack finishUsingItem(@NotNull ItemStack item, @NotNull Level world, @NotNull LivingEntity entity) {
        if (entity instanceof Player player) {
            if (player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty())
                player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(AlcoItems.MUG.get()));
            else if (!player.getInventory().add(new ItemStack(AlcoItems.MUG.get())))
                player.drop(new ItemStack(AlcoItems.MUG.get()), false);
        }

        return super.finishUsingItem(item, world, entity);
    }
}
