package com.github.charlyb01.xpstorage.recipe;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKey;

import java.util.LinkedHashMap;
import java.util.Map;

public class XpBookDeprecateRecipeJsonBuilder {
    private final RecipeCategory category;
    private final Ingredient base;
    private final Map<String, AdvancementCriterion<?>> criteria = new LinkedHashMap<>();

    public XpBookDeprecateRecipeJsonBuilder(RecipeCategory category, Ingredient base) {
        this.category = category;
        this.base = base;
    }

    public static XpBookDeprecateRecipeJsonBuilder create(RecipeCategory category, Ingredient base) {
        return new XpBookDeprecateRecipeJsonBuilder(category, base);
    }

    public XpBookDeprecateRecipeJsonBuilder criterion(String name, AdvancementCriterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    public void offerTo(RecipeExporter exporter, RegistryKey<Recipe<?>> recipeKey) {
        this.validate(recipeKey);
        Advancement.Builder builder = exporter.getAdvancementBuilder()
                .criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeKey))
                .rewards(AdvancementRewards.Builder.recipe(recipeKey))
                .criteriaMerger(AdvancementRequirements.CriterionMerger.OR);
        this.criteria.forEach(builder::criterion);
        XpBookDeprecateRecipe deprecateRecipe = new XpBookDeprecateRecipe(this.base);
        exporter.accept(recipeKey, deprecateRecipe, builder.build(recipeKey.getValue().withPrefixedPath("recipes/" + this.category.getName() + "/")));
    }

    private void validate(RegistryKey<Recipe<?>> recipeKey) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + recipeKey.getValue());
        }
    }
}
