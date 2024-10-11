package com.github.charlyb01.xpstorage.recipe;

import com.github.charlyb01.xpstorage.XpStorage;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class RecipeRegistry {
    public static final RecipeSerializer<XpBookUpgradeRecipe> XP_BOOK_UPGRADE = new XpBookUpgradeRecipe.Serializer();

    public static void init() {
        Registry.register(Registries.RECIPE_SERIALIZER, XpStorage.id("xp_book_upgrade"), XP_BOOK_UPGRADE);
    }
}
