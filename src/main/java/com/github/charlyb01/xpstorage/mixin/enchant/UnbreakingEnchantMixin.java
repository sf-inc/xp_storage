package com.github.charlyb01.xpstorage.mixin.enchant;

import com.github.charlyb01.xpstorage.XpBook;
import net.minecraft.enchantment.UnbreakingEnchantment;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Should not be useful as we already mixin the right EnchantmentTarget!
 * But the code of this Enchantment is bad and override the type, that checks the same conditions.
 */
@Mixin(UnbreakingEnchantment.class)
public class UnbreakingEnchantMixin {
    @Inject(method = "isAcceptableItem", at = @At("HEAD"), cancellable = true)
    private void cantEnchantXPBookStack(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.getItem() instanceof XpBook) {
            cir.setReturnValue(false);
        }
    }
}
