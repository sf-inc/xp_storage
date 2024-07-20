package com.github.charlyb01.xpstorage.mixin;

import com.github.charlyb01.xpstorage.XpBook;
import com.github.charlyb01.xpstorage.component.MyComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(CraftingScreenHandler.class)
public class CraftingScreenHandlerMixin {

    @ModifyVariable(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/CraftingResultInventory;setStack(ILnet/minecraft/item/ItemStack;)V"), ordinal = 0)
    private static ItemStack transferExperience(ItemStack itemStack, ScreenHandler handler, World world, PlayerEntity player,
                                                RecipeInputInventory craftingInventory, CraftingResultInventory resultInventory) {
        if (itemStack.getItem() instanceof XpBook &&
                craftingInventory.getStack(4).getItem() instanceof XpBook) {

            final int experience = MyComponents.XP_COMPONENT_CC.get(craftingInventory.getStack(4)).getAmount();
            MyComponents.XP_COMPONENT_CC.get(itemStack).setAmount(experience);
        }
        return itemStack;
    }
}
