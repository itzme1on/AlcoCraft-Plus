package me.itzme1on.alcocraftplus.core.items;

import me.itzme1on.alcocraftplus.core.registries.ItemsRegistry;
import me.itzme1on.alcocraftplus.core.utils.SidedResultsUtil;
import me.itzme1on.alcocraftplus.core.utils.TooltipUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class MugItem extends BlockItem {
    public MugItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, TooltipDisplay tooltipDisplay,
                                Consumer<Component> consumer, TooltipFlag tooltipFlag) {
        boolean isShiftHeld = Minecraft.getInstance().hasShiftDown();

        TooltipUtil.appendItemEffectTooltips(itemStack, consumer);

        Component keyShiftBind = TooltipUtil.styledKeyFromMapping(Minecraft.getInstance().options.keyShift, isShiftHeld);
        Component keyUseBind = TooltipUtil.styledKeyFromMapping(Minecraft.getInstance().options.keyUse, isShiftHeld);
        Component keyShift = Component.translatable("alcocraftplus.tooltip.key_shift")
                .withStyle(ChatFormatting.GRAY);

        if (!isShiftHeld) {
            consumer.accept(Component.translatable("alcocraftplus.tooltip.mug.default", keyShift)
                    .withStyle(ChatFormatting.DARK_GRAY));
        } else {
            consumer.accept(Component.translatable("alcocraftplus.tooltip.mug.place.line1", keyShiftBind)
                    .withStyle(ChatFormatting.DARK_GRAY));
            consumer.accept(Component.empty());
            consumer.accept(Component.translatable("alcocraftplus.tooltip.mug.place.line2", keyUseBind)
                    .withStyle(ChatFormatting.DARK_GRAY));
        }
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();

        if (player != null && !player.isShiftKeyDown()) return SidedResultsUtil.pass();

        return super.useOn(context);
    }

    @Override
    public @NotNull InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (player.isShiftKeyDown())
            return super.use(level, player, hand);

        if (stack.is(ItemsRegistry.MUG.get()))
            return SidedResultsUtil.pass();

        player.startUsingItem(hand);

        return SidedResultsUtil.consume();
    }

    @Override
    public @NotNull ItemUseAnimation getUseAnimation(ItemStack itemStack) {
        return ItemUseAnimation.DRINK;
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
