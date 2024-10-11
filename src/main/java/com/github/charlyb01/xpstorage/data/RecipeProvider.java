package com.github.charlyb01.xpstorage.data;

import com.github.charlyb01.xpstorage.XpStorage;
import com.github.charlyb01.xpstorage.item.ItemRegistry;
import com.github.charlyb01.xpstorage.recipe.XpBookUpgradeRecipeBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.predicate.NumberRange;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class RecipeProvider extends FabricRecipeProvider {
    public RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ItemRegistry.CRYSTALLIZED_LAPIS, 2)
                .pattern(" l ")
                .pattern("lal")
                .pattern(" l ")
                .input('l', Items.LAPIS_LAZULI)
                .input('a', Items.AMETHYST_SHARD)
                .criterion(FabricRecipeProvider.hasItem(Items.LAPIS_LAZULI),
                        FabricRecipeProvider.conditionsFromItem(NumberRange.IntRange.atLeast(4), Items.LAPIS_LAZULI))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ItemRegistry.XP_BOOK)
                .pattern(" c ")
                .pattern("cbc")
                .pattern(" c ")
                .input('c', ItemRegistry.CRYSTALLIZED_LAPIS)
                .input('b', Items.BOOK)
                .criterion(FabricRecipeProvider.hasItem(ItemRegistry.CRYSTALLIZED_LAPIS),
                        FabricRecipeProvider.conditionsFromItem(ItemRegistry.CRYSTALLIZED_LAPIS))
                .offerTo(exporter);

        offerXpBookUpgradeRecipe(exporter, Items.DIAMOND, 0, 30, 90, 3, Integer.parseInt("a1fbe8", 16));
        offerXpBookUpgradeRecipe(exporter, Items.NETHERITE_INGOT, 1, 50, 95, 5, Integer.parseInt("5a575a", 16));
        offerXpBookUpgradeRecipe(exporter, Items.NETHER_STAR, 2, 100, 100, 10, Integer.parseInt("e0e277", 16));
    }

    public static void offerXpBookUpgradeRecipe(RecipeExporter exporter, Item ingredient, int baseLevel, int resultCapacity,
                                                int resultXpFromUsing, int resultXpFromBrewing, int resultBarColor) {
        XpBookUpgradeRecipeBuilder
                .create(
                        RecipeCategory.MISC,
                        Ingredient.ofItems(Items.LEATHER), // Placeholder
                        Ingredient.ofItems(ItemRegistry.XP_BOOK),
                        Ingredient.ofItems(ingredient),
                        baseLevel,
                        resultCapacity,
                        resultXpFromUsing,
                        resultXpFromBrewing,
                        resultBarColor
                )
                .criterion("has_xp_book_upgrade_ingredient", conditionsFromItem(ingredient))
                .offerTo(exporter, XpStorage.id(getItemPath(ingredient) + "_xp_book_upgrade"));
    }
}
