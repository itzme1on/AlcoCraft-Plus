package me.itzme1on.alcocraftplus.loot;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.List;

public class DungeonChests extends LootModifier {
    private final Item addition;

    protected DungeonChests(LootItemCondition[] conditionsIn, Item addition) {
        super(conditionsIn);
        this.addition = addition;
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        if (context.getRandom().nextFloat() < 0.5f)
            generatedLoot.add(new ItemStack(addition, 1));

        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<DungeonChests> {
        @Override
        public DungeonChests read(ResourceLocation location, JsonObject object, LootItemCondition[] aiLootCondition) {
            Item addition = ForgeRegistries.ITEMS.getValue(new ResourceLocation(object.get("addition").getAsString()));

            return new DungeonChests(aiLootCondition, addition);
        }

        @Override
        public JsonObject write(DungeonChests instance) {
            JsonObject jsonObject = makeConditions(instance.conditions);
            jsonObject.addProperty("addition", ForgeRegistries.ITEMS.getKey(instance.addition).toString());

            return jsonObject;
        }
    }
}
