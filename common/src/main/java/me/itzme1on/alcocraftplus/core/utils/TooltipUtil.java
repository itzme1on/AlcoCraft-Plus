package me.itzme1on.alcocraftplus.core.utils;

import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("unused")
public final class TooltipUtil {
    private TooltipUtil() {
    }

    public static void appendItemEffectTooltips(ItemStack itemStack, List<Component> tooltipLines) {
        record EffectRow(MobEffectInstance instance, float probability, boolean showChance) {
        }

        List<EffectRow> collectedEffects = new ArrayList<>();

        for (MobEffectInstance effectInstance : PotionUtils.getMobEffects(itemStack)) {
            collectedEffects.add(new EffectRow(effectInstance, 1.0f, false));
        }

        FoodProperties foodProperties = itemStack.getItem().getFoodProperties();
        if (foodProperties != null) {
            for (Pair<MobEffectInstance, Float> entry : foodProperties.getEffects()) {
                if (entry == null) continue;

                MobEffectInstance effectInstance = entry.getFirst();
                float probability = entry.getSecond() == null ? 1.0f : entry.getSecond();

                if (effectInstance != null) {
                    collectedEffects.add(new EffectRow(effectInstance, probability, probability < 0.999f));
                }
            }
        }

        if (itemStack.is(Items.SUSPICIOUS_STEW) && itemStack.hasTag()) {
            ListTag effectsTag = itemStack.getTag().getList("Effects", 10);

            for (int i = 0; i < effectsTag.size(); i++) {
                CompoundTag compoundTag = effectsTag.getCompound(i);

                if (!compoundTag.contains("EffectId", 1)) continue;

                MobEffect mobEffect = MobEffect.byId(compoundTag.getByte("EffectId") & 0xFF);

                if (mobEffect == null) continue;

                int duration = compoundTag.contains("EffectDuration", 3) ? compoundTag.getInt("EffectDuration") : 160;
                collectedEffects.add(new EffectRow(new MobEffectInstance(mobEffect, duration), 1.0f, false));
            }
        }

        if (collectedEffects.isEmpty()) return;

        collectedEffects.sort(Comparator
                .comparingInt((EffectRow row) -> row.instance.getEffect().getCategory() == MobEffectCategory.HARMFUL ? 1 : 0)
                .thenComparingInt(row -> -row.instance.getAmplifier())
                .thenComparing(row -> row.instance.getEffect().getDisplayName().getString())
        );

        for (EffectRow effectRow : collectedEffects) {
            tooltipLines.add(formatEffectLine(effectRow.instance));
        }

        tooltipLines.add(Component.empty());
    }

    public static Component styledKeyFromMapping(KeyMapping keyMapping, boolean isActive) {
        return keyMapping.getTranslatedKeyMessage()
                .copy()
                .withStyle(isActive ? ChatFormatting.WHITE : ChatFormatting.GRAY);
    }

    public static Component styledKey(Component keyComponent, boolean isActive) {
        return keyComponent.copy()
                .withStyle(isActive ? ChatFormatting.WHITE : ChatFormatting.GRAY);
    }

    private static Component formatEffectLine(MobEffectInstance mobEffectInstance) {
        MobEffect mobEffect = mobEffectInstance.getEffect();
        Component effectDisplayName = mobEffect.getDisplayName();

        int amplifier = mobEffectInstance.getAmplifier();
        int totalTicks = mobEffectInstance.getDuration();

        MutableComponent effectLine = Component.literal("").append(effectDisplayName);

        if (amplifier > 0) {
            effectLine.append(Component.literal(" " + toRomanNumeral(amplifier + 1)));
        }

        if (totalTicks > 0) {
            effectLine.append(Component.literal(" (" + formatDurationTicksToMMSS(totalTicks) + ")"));
        }

        ChatFormatting lineColor = switch (mobEffect.getCategory()) {
            case BENEFICIAL -> ChatFormatting.BLUE;
            case HARMFUL -> ChatFormatting.RED;
            default -> ChatFormatting.GRAY;
        };

        return effectLine.withStyle(lineColor);
    }

    private static String formatDurationTicksToMMSS(int durationTicks) {
        int totalSeconds = Math.max(0, durationTicks / 20);
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }

    private static String toRomanNumeral(int value) {
        int[] arabic = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] roman = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        int remaining = Math.max(1, Math.min(3999, value));

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < arabic.length; i++) {
            while (remaining >= arabic[i]) {
                remaining -= arabic[i];
                result.append(roman[i]);
            }
        }

        return result.toString();
    }
}
