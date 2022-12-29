package com.github.charlyb01.xpstorage.mixin.brewing;

import com.github.charlyb01.xpstorage.config.ModConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.screen.BrewingStandScreenHandler$PotionSlot")
public class BrewingPotionSlotMixin {
    @Inject(method = "matches", at = @At("HEAD"), cancellable = true)
    private static void makeXpBottlesMatches(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (ModConfig.get().bottles.enableBrewing && stack.isOf(Items.EXPERIENCE_BOTTLE)) {
            cir.setReturnValue(true);
        }
    }
}
