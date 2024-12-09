package com.github.charlyb01.xpstorage.recipe;

import com.github.charlyb01.xpstorage.component.BookData;
import com.github.charlyb01.xpstorage.component.MyComponents;
import com.github.charlyb01.xpstorage.component.XpAmountData;
import com.github.charlyb01.xpstorage.item.ItemRegistry;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.IngredientPlacement;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

public class XpBookDeprecateRecipe implements CraftingRecipe {
    final Ingredient base;

    private static final int BASE_LEVEL = 15;
    private static final BookData UPGRADE_1 = new BookData(1, 30, 90, 3, Integer.parseInt("a1fbe8", 16));
    private static final BookData UPGRADE_2 = new BookData(2, 50, 95, 5, Integer.parseInt("5a575a", 16));
    private static final BookData UPGRADE_3 = new BookData(3, 100, 100, 10, Integer.parseInt("e0e277", 16));

    public XpBookDeprecateRecipe(Ingredient base) {
        this.base = base;
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        if (input.size() != 1) return false;

        ItemStack stack = input.getStackInSlot(0);
       XpAmountData xpAmountData = stack.getOrDefault(MyComponents.XP_COMPONENT, XpAmountData.EMPTY);
        return xpAmountData.level() > BASE_LEVEL;
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        ItemStack inputStack = input.getStackInSlot(0);
        ItemStack result = new ItemStack(ItemRegistry.XP_BOOK);

        XpAmountData xpAmountData = inputStack.getOrDefault(MyComponents.XP_COMPONENT, XpAmountData.EMPTY);
        int level = xpAmountData.level();
        result.set(MyComponents.XP_COMPONENT, xpAmountData);

        if (level > UPGRADE_3.capacity()) {
            result.set(MyComponents.BOOK_COMPONENT, new BookData(4, level, 100, 10, Integer.parseInt("ffffff", 16)));
        } else if (level > UPGRADE_2.capacity() || inputStack.isOf(ItemRegistry.XP_BOOK3)) {
            result.set(MyComponents.BOOK_COMPONENT, UPGRADE_3);
        } else if (level > UPGRADE_1.capacity() || inputStack.isOf(ItemRegistry.XP_BOOK2)) {
            result.set(MyComponents.BOOK_COMPONENT, UPGRADE_2);
        } else {
            result.set(MyComponents.BOOK_COMPONENT, UPGRADE_1);
        }

        return result;
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        return IngredientPlacement.NONE;
    }

    @Override
    public RecipeSerializer<XpBookDeprecateRecipe> getSerializer() {
        return RecipeRegistry.XP_BOOK_DEPRECATE;
    }

    @Override
    public CraftingRecipeCategory getCategory() {
        return CraftingRecipeCategory.MISC;
    }

    public static class Serializer implements RecipeSerializer<XpBookDeprecateRecipe> {
        private static final MapCodec<XpBookDeprecateRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Ingredient.CODEC.fieldOf("base").forGetter(recipe -> recipe.base)
                        )
                        .apply(instance, XpBookDeprecateRecipe::new)
        );
        public static final PacketCodec<RegistryByteBuf, XpBookDeprecateRecipe> PACKET_CODEC = PacketCodec.tuple(
                Ingredient.PACKET_CODEC,
                recipe -> recipe.base,
                XpBookDeprecateRecipe::new
        );

        @Override
        public MapCodec<XpBookDeprecateRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, XpBookDeprecateRecipe> packetCodec() {
            return PACKET_CODEC;
        }
    }
}
