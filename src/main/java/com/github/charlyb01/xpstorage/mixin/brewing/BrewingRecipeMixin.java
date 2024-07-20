package com.github.charlyb01.xpstorage.mixin.brewing;

import com.github.charlyb01.xpstorage.XpBook;
import com.github.charlyb01.xpstorage.config.ModConfig;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.BrewingRecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BrewingRecipeRegistry.class)
public class BrewingRecipeMixin {
    @ModifyReturnValue(method = "isValidIngredient", at = @At("RETURN"))
    private boolean registerXpBookIngredient(boolean original, ItemStack stack) {
        return original || ModConfig.get().bottles.enableBrewing && stack.getItem() instanceof XpBook;
    }
}
