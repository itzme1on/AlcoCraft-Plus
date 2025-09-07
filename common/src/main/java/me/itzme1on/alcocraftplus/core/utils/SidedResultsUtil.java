package me.itzme1on.alcocraftplus.core.utils;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.level.Level;

@SuppressWarnings("unused")
public final class SidedResultsUtil {
    private SidedResultsUtil() {
    }

    /**
     * Equivalent to InteractionResult.sidedSuccess(true)
     * or ItemInteractionResult.sidedSuccess(true).
     * <p>
     * Client side -> SUCCESS (plays hand animation)
     * <p>
     * Server side -> CONSUME (event is consumed, not passed further)
     */
    public static InteractionResult blockSidedSuccess(Level level) {
        return blockSidedSuccess(level.isClientSide);
    }

    /**
     * Equivalent to InteractionResult.sidedSuccess(true)
     * or ItemInteractionResult.sidedSuccess(true).
     * <p>
     * Client side -> SUCCESS
     * <p>
     * Server side -> CONSUME
     */
    public static InteractionResult blockSidedSuccess(boolean clientSide) {
        return clientSide ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
    }

    /**
     * Equivalent to InteractionResult.sidedSuccess(false)
     * or ItemInteractionResult.sidedSuccess(false).
     * <p>
     * Client side -> PASS (no hand animation)
     * <p>
     * Server side -> SUCCESS (event handled)
     */
    public static ItemInteractionResult itemSidedSuccess(Level level) {
        return itemSidedSuccess(level.isClientSide);
    }

    /**
     * Equivalent to InteractionResult.sidedSuccess(false)
     * or ItemInteractionResult.sidedSuccess(false).
     * <p>
     * Client side -> PASS
     * <p>
     * Server side -> SUCCESS
     */
    public static ItemInteractionResult itemSidedSuccess(boolean clientSide) {
        return ItemInteractionResult.sidedSuccess(false);
    }

    /**
     * Equivalent to InteractionResult.sidedSuccess(true)
     * or ItemInteractionResult.sidedSuccess(true) — immediate success with hand animation.
     * <p>
     * Client side -> SUCCESS (plays hand animation)
     * <p>
     * Server side -> CONSUME
     */
    public static ItemInteractionResult itemConsume(Level level) {
        return itemConsume(level.isClientSide);
    }

    /**
     * Equivalent to InteractionResult.sidedSuccess(true)
     * or ItemInteractionResult.sidedSuccess(true).
     * <p>
     * Client side -> SUCCESS
     * <p>
     * Server side -> CONSUME
     */
    public static ItemInteractionResult itemConsume(boolean clientSide) {
        return ItemInteractionResult.sidedSuccess(true);
    }

    /**
     * Equivalent to InteractionResult.PASS.
     * <p>
     * Means: do not handle here, pass to default block interaction.
     */
    public static InteractionResult pass() {
        return InteractionResult.PASS;
    }

    /**
     * Equivalent to InteractionResult.FAIL.
     * <p>
     * Means: completely deny the interaction (no animation, no processing).
     */
    public static InteractionResult fail() {
        return InteractionResult.FAIL;
    }

    /**
     * Equivalent to InteractionResult.CONSUME.
     * <p>
     * Means: the interaction is accepted and processed,
     * but no client-side hand animation is triggered.
     * Typically used for "long use" actions (e.g. drinking, charging, drawing a bow).
     */
    public static InteractionResult consume() {
        return InteractionResult.CONSUME;
    }

    /**
     * Equivalent to InteractionResult.SUCCESS.
     * <p>
     * Means: the interaction is successful and the client should play a hand animation.
     * Often used for immediate actions (e.g. placing blocks, opening containers).
     */
    public static InteractionResult success() {
        return InteractionResult.SUCCESS;
    }

    /**
     * Equivalent to ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION.
     * <p>
     * Means: do not handle here, pass to default block interaction.
     */
    public static ItemInteractionResult passToDefault() {
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
}
