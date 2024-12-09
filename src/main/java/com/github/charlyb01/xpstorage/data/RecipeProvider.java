package com.github.charlyb01.xpstorage.data;

import com.github.charlyb01.xpstorage.XpStorage;
import com.github.charlyb01.xpstorage.item.ItemRegistry;
import com.github.charlyb01.xpstorage.recipe.XpBookDeprecateRecipeJsonBuilder;
import com.github.charlyb01.xpstorage.recipe.XpBookUpgradeRecipeJsonBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.predicate.NumberRange;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class RecipeProvider extends FabricRecipeProvider {
    public RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registries, RecipeExporter exporter) {
        return new RecipeGenerator(registries, exporter) {
            @Override
            public void generate() {
                this.createShaped(RecipeCategory.MISC, ItemRegistry.CRYSTALLIZED_LAPIS, 2)
                        .pattern(" l ")
                        .pattern("lal")
                        .pattern(" l ")
                        .input('l', Items.LAPIS_LAZULI)
                        .input('a', Items.AMETHYST_SHARD)
                        .criterion(RecipeGenerator.hasItem(Items.LAPIS_LAZULI),
                                this.conditionsFromItem(NumberRange.IntRange.atLeast(4), Items.LAPIS_LAZULI))
                        .offerTo(this.exporter);

                this.createShaped(RecipeCategory.TOOLS, ItemRegistry.XP_BOOK)
                        .pattern(" c ")
                        .pattern("cbc")
                        .pattern(" c ")
                        .input('c', ItemRegistry.CRYSTALLIZED_LAPIS)
                        .input('b', Items.BOOK)
                        .criterion(RecipeGenerator.hasItem(ItemRegistry.CRYSTALLIZED_LAPIS),
                                this.conditionsFromItem(ItemRegistry.CRYSTALLIZED_LAPIS))
                        .offerTo(this.exporter);

                this.createShaped(RecipeCategory.MISC, ItemRegistry.XP_BOOK_UPGRADE)
                        .pattern("lll")
                        .pattern("ldl")
                        .pattern("lll")
                        .input('l', ItemRegistry.CRYSTALLIZED_LAPIS)
                        .input('d', Items.DIAMOND)
                        .criterion(RecipeGenerator.hasItem(ItemRegistry.CRYSTALLIZED_LAPIS),
                                this.conditionsFromItem(ItemRegistry.CRYSTALLIZED_LAPIS))
                        .offerTo(this.exporter);

                this.createShaped(RecipeCategory.MISC, ItemRegistry.XP_BOOK_UPGRADE, 2)
                        .pattern(" l ")
                        .pattern("lul")
                        .pattern(" l ")
                        .input('l', ItemRegistry.CRYSTALLIZED_LAPIS)
                        .input('u', ItemRegistry.XP_BOOK_UPGRADE)
                        .criterion(RecipeGenerator.hasItem(ItemRegistry.CRYSTALLIZED_LAPIS),
                                this.conditionsFromItem(ItemRegistry.CRYSTALLIZED_LAPIS))
                        .offerTo(this.exporter, getItemPath(ItemRegistry.XP_BOOK_UPGRADE) + "_duplication");

                this.offerXpBookUpgradeRecipe(Items.DIAMOND, 0, 30, 90, 3, Integer.parseInt("a1fbe8", 16));
                this.offerXpBookUpgradeRecipe(Items.NETHERITE_INGOT, 1, 50, 95, 5, Integer.parseInt("5a575a", 16));
                this.offerXpBookUpgradeRecipe(Items.NETHER_STAR, 2, 100, 100, 10, Integer.parseInt("e0e277", 16));

                this.offerXpBookDeprecateRecipe(ItemRegistry.XP_BOOK);
                this.offerXpBookDeprecateRecipe(ItemRegistry.XP_BOOK2);
                this.offerXpBookDeprecateRecipe(ItemRegistry.XP_BOOK3);
            }

            public void offerXpBookUpgradeRecipe(Item ingredient, int baseLevel, int resultCapacity,
                                                 int resultXpFromUsing, int resultXpFromBrewing, int resultBarColor) {
                XpBookUpgradeRecipeJsonBuilder
                        .create(
                                RecipeCategory.MISC,
                                Ingredient.ofItems(ItemRegistry.XP_BOOK_UPGRADE),
                                Ingredient.ofItems(ItemRegistry.XP_BOOK),
                                Ingredient.ofItems(ingredient),
                                baseLevel,
                                resultCapacity,
                                resultXpFromUsing,
                                resultXpFromBrewing,
                                resultBarColor
                        )
                        .criterion("has_xp_book_upgrade_ingredient", this.conditionsFromItem(ingredient))
                        .offerTo(this.exporter, RegistryKey.of(RegistryKeys.RECIPE, XpStorage.id(getItemPath(ingredient) + "_xp_book_upgrade")));
            }

            public void offerXpBookDeprecateRecipe(Item ingredient) {
                XpBookDeprecateRecipeJsonBuilder
                        .create(
                                RecipeCategory.MISC,
                                Ingredient.ofItems(ingredient)
                        )
                        .criterion("has_deprecated_xp_book", this.conditionsFromItem(ingredient))
                        .offerTo(this.exporter, RegistryKey.of(RegistryKeys.RECIPE, XpStorage.id(getItemPath(ingredient) + "_deprecate")));
            }
        };
    }

    @Override
    public String getName() {
        return "Recipes";
    }
}
