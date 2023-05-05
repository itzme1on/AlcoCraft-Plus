package me.itzme1on.alcocraftplus.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class HopSeeds extends LootModifier {
    private final LootItemCondition[] conditions;
    private final Item addition;

    public static final Codec<HopSeeds> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
        LOOT_CONDITIONS_CODEC.fieldOf("conditions").forGetter(HopSeeds::getConditions),
        ForgeRegistries.ITEMS.getCodec().fieldOf("addition").forGetter(HopSeeds::getAddition)
    ).apply(instance, HopSeeds::new));

    /**
     * /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     */
    protected HopSeeds(LootItemCondition[] conditionsIn, Item addition) {
        super(conditionsIn);
        this.conditions = conditionsIn;
        this.addition = addition;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (context.getRandom().nextFloat() < 0.1f)
            generatedLoot.add(new ItemStack(addition, 1));

        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

    public Item getAddition() {
        return addition;
    }

    public LootItemCondition[] getConditions() {
        return conditions;
    }
}