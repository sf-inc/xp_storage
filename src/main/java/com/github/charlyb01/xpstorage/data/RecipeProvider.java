package com.github.charlyb01.xpstorage.data;

import com.github.charlyb01.xpstorage.Xpstorage;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.predicate.NumberRange;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class RecipeProvider extends FabricRecipeProvider {
    public RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Xpstorage.CRYSTALLIZED_LAPIS, 2)
                .pattern(" l ")
                .pattern("lal")
                .pattern(" l ")
                .input('l', Items.LAPIS_LAZULI)
                .input('a', Items.AMETHYST_SHARD)
                .criterion(FabricRecipeProvider.hasItem(Items.LAPIS_LAZULI),
                        FabricRecipeProvider.conditionsFromItem(NumberRange.IntRange.atLeast(4), Items.LAPIS_LAZULI))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Xpstorage.xp_book)
                .pattern(" d ")
                .pattern("cbc")
                .pattern(" c ")
                .input('c', Xpstorage.CRYSTALLIZED_LAPIS)
                .input('b', Items.BOOK)
                .input('d', Items.DIAMOND)
                .criterion(FabricRecipeProvider.hasItem(Xpstorage.CRYSTALLIZED_LAPIS),
                        FabricRecipeProvider.conditionsFromItem(Xpstorage.CRYSTALLIZED_LAPIS))
                .offerTo(exporter);
    }
}
