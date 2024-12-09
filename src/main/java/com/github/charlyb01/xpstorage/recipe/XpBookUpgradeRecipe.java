package com.github.charlyb01.xpstorage.recipe;

import com.github.charlyb01.xpstorage.item.XpBook;
import com.github.charlyb01.xpstorage.component.BookData;
import com.github.charlyb01.xpstorage.component.MyComponents;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.SmithingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class XpBookUpgradeRecipe implements SmithingRecipe {
    final Optional<Ingredient> template;
    final Optional<Ingredient> base;
    final Optional<Ingredient> addition;
    private final int baseLevel;
    private final int resultCapacity;
    private final int resultXpFromUsing;
    private final int resultXpFromBrewing;
    private final int resultBarColor;
    @Nullable
    private IngredientPlacement ingredientPlacement;

    public XpBookUpgradeRecipe(Optional<Ingredient> template, Optional<Ingredient> base, Optional<Ingredient> addition,
                               int baseLevel, int resultCapacity, int resultXpFromUsing, int resultXpFromBrewing,
                               int resultBarColor) {
        this.template = template;
        this.base = base;
        this.addition = addition;
        this.baseLevel = baseLevel;
        this.resultCapacity = resultCapacity;
        this.resultXpFromUsing = resultXpFromUsing;
        this.resultXpFromBrewing = resultXpFromBrewing;
        this.resultBarColor = resultBarColor;
    }

    @Override
    public boolean matches(SmithingRecipeInput smithingRecipeInput, World world) {
        return this.testBase(smithingRecipeInput.base())
                && Ingredient.matches(this.template(), smithingRecipeInput.template())
                && Ingredient.matches(this.addition(), smithingRecipeInput.addition());
    }

    @Override
    public ItemStack craft(SmithingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        ItemStack base = input.base();
        int level = XpBook.getBookLevel(base);
        ItemStack result = base.copyWithCount(1);
        result.set(MyComponents.BOOK_COMPONENT, new BookData(level + 1, this.resultCapacity,
                this.resultXpFromUsing, this.resultXpFromBrewing, this.resultBarColor));
        return result;
    }

    public boolean testBase(ItemStack stack) {
        if (!Ingredient.matches(this.base, stack)) return false;

        int level = stack.getOrDefault(MyComponents.BOOK_COMPONENT, BookData.getDefault()).level();
        return level == this.baseLevel;
    }

    @Override
    public Optional<Ingredient> template() {
        return this.template;
    }

    @Override
    public Optional<Ingredient> base() {
        return this.base;
    }

    @Override
    public Optional<Ingredient> addition() {
        return this.addition;
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        if (this.ingredientPlacement == null) {
            this.ingredientPlacement = IngredientPlacement.forMultipleSlots(List.of(this.template, this.base, this.addition));
        }

        return this.ingredientPlacement;
    }

    @Override
    public RecipeSerializer<XpBookUpgradeRecipe> getSerializer() {
        return RecipeRegistry.XP_BOOK_UPGRADE;
    }

    public static class Serializer implements RecipeSerializer<XpBookUpgradeRecipe> {
        private static final MapCodec<XpBookUpgradeRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Ingredient.CODEC.optionalFieldOf("template").forGetter(recipe -> recipe.template),
                                Ingredient.CODEC.optionalFieldOf("base").forGetter(recipe -> recipe.base),
                                Ingredient.CODEC.optionalFieldOf("addition").forGetter(recipe -> recipe.addition),
                                Codec.INT.fieldOf("baseLevel").forGetter(recipe -> recipe.baseLevel),
                                Codec.INT.fieldOf("resultCapacity").forGetter(recipe -> recipe.resultCapacity),
                                Codec.INT.fieldOf("resultXpFromUsing").forGetter(recipe -> recipe.resultXpFromUsing),
                                Codec.INT.fieldOf("resultXpFromBrewing").forGetter(recipe -> recipe.resultXpFromBrewing),
                                Codec.INT.fieldOf("resultBarColor").forGetter(recipe -> recipe.resultBarColor)
                        )
                        .apply(instance, XpBookUpgradeRecipe::new)
        );
        public static final PacketCodec<RegistryByteBuf, XpBookUpgradeRecipe> PACKET_CODEC = PacketCodec.tuple(
                Ingredient.OPTIONAL_PACKET_CODEC,
                recipe -> recipe.template,
                Ingredient.OPTIONAL_PACKET_CODEC,
                recipe -> recipe.base,
                Ingredient.OPTIONAL_PACKET_CODEC,
                recipe -> recipe.addition,
                PacketCodecs.INTEGER,
                recipe -> recipe.baseLevel,
                PacketCodecs.INTEGER,
                recipe -> recipe.resultCapacity,
                PacketCodecs.INTEGER,
                recipe -> recipe.resultXpFromUsing,
                PacketCodecs.INTEGER,
                recipe -> recipe.resultXpFromBrewing,
                PacketCodecs.INTEGER,
                recipe -> recipe.resultBarColor,
                XpBookUpgradeRecipe::new
        );

        @Override
        public MapCodec<XpBookUpgradeRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, XpBookUpgradeRecipe> packetCodec() {
            return PACKET_CODEC;
        }
    }
}
