package com.github.charlyb01.xpstorage.mixin.enchant;

import com.github.charlyb01.xpstorage.XpBook;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// BREAKABLE enchantment target
@Mixin(targets = "net/minecraft/enchantment/EnchantmentTarget$2")
public class EnchantmentTargetMixin {
    @Inject(method = "isAcceptableItem", at = @At("HEAD"), cancellable = true)
    private void cantEnchantXPBookItem(Item item, CallbackInfoReturnable<Boolean> cir) {
        if (item instanceof XpBook) {
            cir.setReturnValue(false);
        }
    }
}
