package com.github.charlyb01.xpstorage.recipe;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public class XpBookDeprecateRecipeBuilder {
    private final RecipeCategory category;
    private final Ingredient base;
    private final Map<String, AdvancementCriterion<?>> criteria = new LinkedHashMap<>();

    public XpBookDeprecateRecipeBuilder(RecipeCategory category, Ingredient base) {
        this.category = category;
        this.base = base;
    }

    public static XpBookDeprecateRecipeBuilder create(RecipeCategory category, Ingredient base) {
        return new XpBookDeprecateRecipeBuilder(category, base);
    }

    public XpBookDeprecateRecipeBuilder criterion(String name, AdvancementCriterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    public void offerTo(RecipeExporter exporter, Identifier recipeId) {
        this.validate(recipeId);
        Advancement.Builder builder = exporter.getAdvancementBuilder()
                .criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId))
                .criteriaMerger(AdvancementRequirements.CriterionMerger.OR);
        this.criteria.forEach(builder::criterion);
        XpBookDeprecateRecipe deprecateRecipe = new XpBookDeprecateRecipe(this.base);
        exporter.accept(recipeId, deprecateRecipe, builder.build(recipeId.withPrefixedPath("recipes/" + this.category.getName() + "/")));
    }

    private void validate(Identifier recipeId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + recipeId);
        }
    }
}
