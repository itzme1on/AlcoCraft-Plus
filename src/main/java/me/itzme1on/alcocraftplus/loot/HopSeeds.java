package me.itzme1on.alcocraftplus.loot;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HopSeeds extends LootModifier {
    private final Item addition;

    protected HopSeeds(LootItemCondition[] conditionsIn, Item addition) {
        super(conditionsIn);
        this.addition = addition;
    }

    @NotNull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        if (context.getRandom().nextFloat() < 0.1f) {
            generatedLoot.add(new ItemStack(addition, 1));
        }

        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<HopSeeds> {
        @Override
        public HopSeeds read(ResourceLocation location, JsonObject object, LootItemCondition[] aiLootCondition) {
            Item addition = ForgeRegistries.ITEMS.getValue(new ResourceLocation(GsonHelper.getAsString(object, "addition")));

            return new HopSeeds(aiLootCondition, addition);
        }

        @Override
        public JsonObject write(HopSeeds instance) {
            JsonObject jsonObject = makeConditions(instance.conditions);
            jsonObject.addProperty("addition", ForgeRegistries.ITEMS.getKey(instance.addition).toString());

            return jsonObject;
        }
    }
}
