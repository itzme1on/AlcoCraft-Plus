package me.itzme1on.alcocraftplus.core.utils;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;

public class SidedResultsUtil {
    private SidedResultsUtil() {
    }

    /**
     * Equivalent to old InteractionResult.sidedSuccess(true)
     * or ItemInteractionResult.sidedSuccess(true).
     * <p>
     * Client side -> SUCCESS (plays hand animation)
     * Server side -> CONSUME (event is consumed, not passed further)
     */
    public static InteractionResult blockSidedSuccess(Level level) {
        return blockSidedSuccess(level.isClientSide());
    }

    /**
     * Equivalent to old InteractionResult.sidedSuccess(true)
     * or ItemInteractionResult.sidedSuccess(true).
     * <p>
     * Client side -> SUCCESS
     * Server side -> CONSUME
     */
    public static InteractionResult blockSidedSuccess(boolean clientSide) {
        return clientSide ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
    }

    /**
     * Equivalent to old InteractionResult.sidedSuccess(false)
     * or ItemInteractionResult.sidedSuccess(false).
     * <p>
     * Client side -> PASS (no hand animation)
     * Server side -> SUCCESS (event handled)
     */
    public static InteractionResult itemSidedSuccess(Level level) {
        return itemSidedSuccess(level.isClientSide());
    }

    /**
     * Equivalent to old InteractionResult.sidedSuccess(false)
     * or ItemInteractionResult.sidedSuccess(false).
     * <p>
     * Client side -> PASS
     * Server side -> SUCCESS
     */
    public static InteractionResult itemSidedSuccess(boolean clientSide) {
        return clientSide ? InteractionResult.PASS : InteractionResult.SUCCESS;
    }

    /**
     * Equivalent to old InteractionResult.PASS
     * or ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION.
     * <p>
     * Means: do not handle here, pass to default block interaction.
     */
    public static InteractionResult pass() {
        return InteractionResult.PASS;
    }

    /**
     * Equivalent to old InteractionResult.FAIL.
     * <p>
     * Means: completely deny the interaction (no animation, no processing).
     */
    public static InteractionResult fail() {
        return InteractionResult.FAIL;
    }

    /**
     * Equivalent to old InteractionResult.CONSUME.
     * <p>
     * Means: the interaction is accepted and processed,
     * but no client-side hand animation is triggered.
     * Typically used for "long use" actions (e.g. drinking, charging, drawing a bow).
     */
    public static InteractionResult consume() {
        return InteractionResult.CONSUME;
    }

    /**
     * Equivalent to old InteractionResult.SUCCESS.
     * <p>
     * Means: the interaction is successful and the client should play a hand animation.
     * Often used for immediate actions (e.g. placing blocks, opening containers).
     */
    public static InteractionResult success() {
        return InteractionResult.SUCCESS;
    }
}
