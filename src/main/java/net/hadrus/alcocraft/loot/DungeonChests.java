package net.hadrus.alcocraft.loot;

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

public class DungeonChests extends LootModifier {

    public static final Codec<DungeonChests> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
        LOOT_CONDITIONS_CODEC.fieldOf("conditions").forGetter(DungeonChests::getConditions),
        ForgeRegistries.ITEMS.getCodec().fieldOf("addition").forGetter(DungeonChests::getAddition)
    ).apply(instance, DungeonChests::new));

    private final LootItemCondition[] conditions;
    private final Item addition;

    protected DungeonChests(LootItemCondition[] conditionsIn, Item addition) {
        super(conditionsIn);
        this.conditions = conditionsIn;
        this.addition = addition;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (context.getRandom().nextFloat() < 0.5f) {
            generatedLoot.add(new ItemStack(addition, 1));
        }
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