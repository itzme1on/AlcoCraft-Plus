package me.itzme1on.alcocraftplus.core.items;

import me.itzme1on.alcocraftplus.core.registries.ItemsRegistry;
import me.itzme1on.alcocraftplus.core.utils.TooltipUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MugItem extends BlockItem {
    public MugItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipLines, TooltipFlag tooltipFlag) {
        boolean isShiftHeld = Screen.hasShiftDown();

        TooltipUtil.appendItemEffectTooltips(stack, tooltipLines);

        Minecraft minecraft = Minecraft.getInstance();
        Component keyShiftBind = TooltipUtil.styledKeyFromMapping(minecraft.options.keyShift, isShiftHeld);
        Component keyUseBind = TooltipUtil.styledKeyFromMapping(minecraft.options.keyUse, isShiftHeld);
        Component keyShift = Component.translatable("alcocraftplus.tooltip.key_shift")
                .withStyle(ChatFormatting.GRAY);

        if (!isShiftHeld) {
            tooltipLines.add(Component.translatable("alcocraftplus.tooltip.mug.default", keyShift)
                    .withStyle(ChatFormatting.DARK_GRAY));
        } else {
            tooltipLines.add(Component.translatable("alcocraftplus.tooltip.mug.place.line1", keyShiftBind)
                    .withStyle(ChatFormatting.DARK_GRAY));
            tooltipLines.add(Component.empty());
            tooltipLines.add(Component.translatable("alcocraftplus.tooltip.mug.place.line2", keyUseBind)
                    .withStyle(ChatFormatting.DARK_GRAY));
        }
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();

        if (player != null && !player.isShiftKeyDown()) {
            return InteractionResult.PASS;
        }

        return super.useOn(context);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (player.isShiftKeyDown()) {
            return super.use(level, player, hand);
        }

        if (stack.is(ItemsRegistry.MUG.get())) {
            return InteractionResultHolder.pass(stack);
        }

        player.startUsingItem(hand);

        return InteractionResultHolder.consume(stack);
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
        ItemStack result = super.finishUsingItem(stack, level, entity);
        ItemStack emptyMug = new ItemStack(ItemsRegistry.MUG.get());

        if (entity instanceof Player player && !player.getAbilities().instabuild) {
            if (result.isEmpty()) {
                return emptyMug;
            } else if (!player.getInventory().add(emptyMug)) {
                player.drop(emptyMug, false);
            }
        }

        return result;
    }
}
