package me.itzme1on.alcocraftplus.core.utils;

import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public final class TooltipUtil {
    private TooltipUtil() {
    }

    public static void appendItemEffectTooltips(ItemStack itemStack, Consumer<Component> addLine) {
        record EffectRow(MobEffectInstance instance, float probability, boolean showChance) {
        }

        List<EffectRow> collectedEffects = new ArrayList<>();

        PotionContents potionContents = itemStack.get(DataComponents.POTION_CONTENTS);

        if (potionContents != null) {
            for (MobEffectInstance mobEffectInstance : potionContents.getAllEffects()) {
                collectedEffects.add(new EffectRow(mobEffectInstance, 1.0f, false));
            }
        }

        SuspiciousStewEffects stewEffects = itemStack.get(DataComponents.SUSPICIOUS_STEW_EFFECTS);

        if (stewEffects != null) {
            stewEffects.effects().forEach(stewEntry -> {
                MobEffectInstance mobEffectInstance = new MobEffectInstance(stewEntry.effect(), stewEntry.duration(), 0);
                collectedEffects.add(new EffectRow(mobEffectInstance, 1.0f, false));
            });
        }

        Consumable consumable = itemStack.get(DataComponents.CONSUMABLE);

        if (consumable != null) {
            consumable.onConsumeEffects().forEach(effect -> {
                if (effect instanceof ApplyStatusEffectsConsumeEffect(
                        List<MobEffectInstance> effects, float probability
                )) {
                    for (var eff : effects)
                        collectedEffects.add(new EffectRow(eff, probability, probability < 0.9999f));
                }
            });
        }

        if (collectedEffects.isEmpty()) return;

        collectedEffects.sort(Comparator
                .comparingInt((EffectRow r) -> r.instance.getEffect().value().getCategory() == MobEffectCategory.HARMFUL ? 1 : 0)
                .thenComparingInt(r -> -r.instance.getAmplifier())
                .thenComparing(r -> r.instance.getEffect().value().getDisplayName().getString())
        );

        for (EffectRow row : collectedEffects)
            addLine.accept(formatEffectLine(row.instance, row.probability, row.showChance));

        addLine.accept(Component.empty());
    }

    public static void appendItemEffectTooltips(ItemStack itemStack, List<Component> tooltipLines) {
        appendItemEffectTooltips(itemStack, tooltipLines::add);
    }

    public static Component styledKeyFromMapping(KeyMapping keyMapping, boolean isActive) {
        return keyMapping.getTranslatedKeyMessage()
                .copy()
                .withStyle(isActive ? ChatFormatting.WHITE : ChatFormatting.GRAY);
    }

    private static Component formatEffectLine(MobEffectInstance mobEffectInstance,
                                              float probability,
                                              boolean showChance) {
        MobEffect mobEffect = mobEffectInstance.getEffect().value();
        Component effectDisplayName = mobEffect.getDisplayName();

        int amplifier = mobEffectInstance.getAmplifier();
        int totalTicks = mobEffectInstance.getDuration();

        MutableComponent line = Component.literal("").append(effectDisplayName);

        if (amplifier > 0)
            line.append(Component.literal(" " + toRomanNumeral(amplifier + 1)));

        if (totalTicks > 0)
            line.append(Component.literal(" (" + formatDurationTicksToMMSS(totalTicks) + ")"));

        if (showChance) {
            int pct = Math.round(probability * 100f);

            line.append(Component.literal(" (" + pct + "%)"));
        }

        ChatFormatting color = switch (mobEffect.getCategory()) {
            case BENEFICIAL -> ChatFormatting.BLUE;
            case HARMFUL -> ChatFormatting.RED;
            default -> ChatFormatting.GRAY;
        };

        return line.withStyle(color);
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

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < arabic.length; i++) {
            while (remaining >= arabic[i]) {
                remaining -= arabic[i];

                sb.append(roman[i]);
            }
        }

        return sb.toString();
    }
}
