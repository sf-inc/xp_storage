package com.github.charlyb01.xpstorage.recipe;

import com.github.charlyb01.xpstorage.XpBook;
import com.github.charlyb01.xpstorage.Xpstorage;
import com.github.charlyb01.xpstorage.component.BookData;
import com.github.charlyb01.xpstorage.component.MyComponents;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SmithingRecipe;
import net.minecraft.recipe.input.SmithingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

import java.util.stream.Stream;

public class XpBookUpgradeRecipe implements SmithingRecipe {
    final Ingredient template;
    final Ingredient base;
    final Ingredient addition;
    private final int baseLevel;
    private final int resultCapacity;
    private final int resultXpFromUsing;
    private final int resultXpFromBrewing;
    private final int resultBarColor;

    public XpBookUpgradeRecipe(Ingredient template, Ingredient base, Ingredient addition, int baseLevel,
                               int resultCapacity, int resultXpFromUsing, int resultXpFromBrewing, int resultBarColor) {
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
    public boolean matches(SmithingRecipeInput input, World world) {
        return this.testBase(input.base())
                && this.testTemplate(input.template())
                && this.testAddition(input.addition());
    }

    @Override
    public ItemStack craft(SmithingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        ItemStack base = input.base();
        if (this.base.test(base)) {
            int level = XpBook.getBookLevel(base);
            ItemStack result = base.copyWithCount(1);
            result.set(MyComponents.BOOK_COMPONENT, new BookData(level + 1, this.resultCapacity,
                    this.resultXpFromUsing, this.resultXpFromBrewing, this.resultBarColor));
            return result;
        }

        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return new ItemStack(Xpstorage.xp_book);
    }

    @Override
    public boolean testTemplate(ItemStack stack) {
        return this.template.test(stack);
    }

    @Override
    public boolean testBase(ItemStack stack) {
        if (!this.base.test(stack)) return false;

        int level = stack.getOrDefault(MyComponents.BOOK_COMPONENT, BookData.getDefault()).level();
        return level == this.baseLevel;
    }

    @Override
    public boolean testAddition(ItemStack stack) {
        return this.addition.test(stack);
    }

    @Override
    public boolean isEmpty() {
        return Stream.of(this.template, this.base, this.addition).anyMatch(Ingredient::isEmpty);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegistry.XP_BOOK_UPGRADE;
    }

    public static class Serializer implements RecipeSerializer<XpBookUpgradeRecipe> {
        private static final MapCodec<XpBookUpgradeRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Ingredient.ALLOW_EMPTY_CODEC.fieldOf("template").forGetter(recipe -> recipe.template),
                                Ingredient.ALLOW_EMPTY_CODEC.fieldOf("base").forGetter(recipe -> recipe.base),
                                Ingredient.ALLOW_EMPTY_CODEC.fieldOf("addition").forGetter(recipe -> recipe.addition),
                                Codec.INT.fieldOf("baseLevel").forGetter(recipe -> recipe.baseLevel),
                                Codec.INT.fieldOf("resultCapacity").forGetter(recipe -> recipe.resultCapacity),
                                Codec.INT.fieldOf("resultXpFromUsing").forGetter(recipe -> recipe.resultXpFromUsing),
                                Codec.INT.fieldOf("resultXpFromBrewing").forGetter(recipe -> recipe.resultXpFromBrewing),
                                Codec.INT.fieldOf("resultBarColor").forGetter(recipe -> recipe.resultBarColor)
                        )
                        .apply(instance, XpBookUpgradeRecipe::new)
        );
        public static final PacketCodec<RegistryByteBuf, XpBookUpgradeRecipe> PACKET_CODEC = PacketCodec.ofStatic(
                XpBookUpgradeRecipe.Serializer::write, XpBookUpgradeRecipe.Serializer::read
        );

        @Override
        public MapCodec<XpBookUpgradeRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, XpBookUpgradeRecipe> packetCodec() {
            return PACKET_CODEC;
        }

        private static XpBookUpgradeRecipe read(RegistryByteBuf buf) {
            Ingredient template = Ingredient.PACKET_CODEC.decode(buf);
            Ingredient base = Ingredient.PACKET_CODEC.decode(buf);
            Ingredient addition = Ingredient.PACKET_CODEC.decode(buf);
            int baseLevel = PacketCodecs.INTEGER.decode(buf);
            int capacity = PacketCodecs.INTEGER.decode(buf);
            int xpFromUsing = PacketCodecs.INTEGER.decode(buf);
            int xpFromBrewing = PacketCodecs.INTEGER.decode(buf);
            int barColor = PacketCodecs.INTEGER.decode(buf);
            return new XpBookUpgradeRecipe(template, base, addition, baseLevel, capacity, xpFromUsing, xpFromBrewing, barColor);
        }

        private static void write(RegistryByteBuf buf, XpBookUpgradeRecipe recipe) {
            Ingredient.PACKET_CODEC.encode(buf, recipe.template);
            Ingredient.PACKET_CODEC.encode(buf, recipe.base);
            Ingredient.PACKET_CODEC.encode(buf, recipe.addition);
            PacketCodecs.INTEGER.encode(buf, recipe.baseLevel);
            PacketCodecs.INTEGER.encode(buf, recipe.resultCapacity);
            PacketCodecs.INTEGER.encode(buf, recipe.resultXpFromUsing);
            PacketCodecs.INTEGER.encode(buf, recipe.resultXpFromBrewing);
            PacketCodecs.INTEGER.encode(buf, recipe.resultBarColor);
        }
    }
}
