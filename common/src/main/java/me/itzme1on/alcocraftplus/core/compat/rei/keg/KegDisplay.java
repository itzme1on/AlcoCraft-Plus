package me.itzme1on.alcocraftplus.core.compat.rei.keg;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.core.recipes.KegRecipes;
import me.itzme1on.alcocraftplus.core.utils.IdentifierUtil;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class KegDisplay extends BasicDisplay {
    public static final CategoryIdentifier<KegDisplay> CATEGORY = CategoryIdentifier.of(AlcoCraftPlus.MOD_ID, "beer_brewing");
    public static final ResourceLocation SERIALIZER_ID = IdentifierUtil.of("keg");
    public static final MapCodec<KegDisplay> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            EntryIngredient.codec().listOf().fieldOf("inputs").forGetter(KegDisplay::getInputEntries),
            EntryIngredient.codec().listOf().fieldOf("outputs").forGetter(KegDisplay::getOutputEntries)
    ).apply(instance, KegDisplay::new));

    private static final StreamCodec<RegistryFriendlyByteBuf, List<EntryIngredient>> LIST_CODEC =
            EntryIngredient.streamCodec().apply(ByteBufCodecs.list());

    public static final StreamCodec<RegistryFriendlyByteBuf, KegDisplay> STREAM_CODEC = StreamCodec.composite(
            LIST_CODEC, KegDisplay::getInputEntries,
            LIST_CODEC, KegDisplay::getOutputEntries,
            KegDisplay::new
    );

    public static final DisplaySerializer<KegDisplay> SERIALIZER = DisplaySerializer.of(CODEC, STREAM_CODEC);

    public KegDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public static KegDisplay of(KegRecipes recipe) {
        List<EntryIngredient> inputs = EntryIngredients.ofIngredients(recipe.getIngredients());
        List<EntryIngredient> outputs = Collections.singletonList(EntryIngredients.of(recipe.getResultItem()));

        return new KegDisplay(inputs, outputs);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return CATEGORY;
    }

    @Override
    public @Nullable DisplaySerializer<? extends Display> getSerializer() {
        return SERIALIZER;
    }
}
