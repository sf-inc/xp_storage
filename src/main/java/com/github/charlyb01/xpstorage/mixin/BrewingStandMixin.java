package com.github.charlyb01.xpstorage.mixin;

import com.github.charlyb01.xpstorage.XpBook;
import com.github.charlyb01.xpstorage.cardinal.MyComponents;
import com.github.charlyb01.xpstorage.config.ConstantsConfig;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrewingStandBlockEntity.class)
public abstract class BrewingStandMixin extends LockableContainerBlockEntity {
    @Shadow
    private DefaultedList<ItemStack> inventory;

    protected BrewingStandMixin(BlockEntityType<?> blockEntityType) {
        super(blockEntityType);
    }

    @Inject(method = "canCraft", at = @At("HEAD"), cancellable = true)
    private void canUseXpBooks(CallbackInfoReturnable<Boolean> cir) {
        ItemStack itemStack = this.inventory.get(3);
        if (!itemStack.isEmpty()
                && itemStack.getItem() instanceof XpBook
                && itemStack.getDamage() > 0) {
            for(int i = 0; i < 3; ++i) {
                ItemStack itemStack2 = this.inventory.get(i);
                if (!itemStack2.isEmpty()
                        && itemStack2.getTag() != null
                        && itemStack2.getTag().toString().contains("minecraft:mundane")) {
                    cir.setReturnValue(true);
                }
            }
        }
    }

    @Inject(method = "craft", at = @At("HEAD"), cancellable = true)
    private void craftXpBottles(CallbackInfo ci) {
        ItemStack xpBook = this.inventory.get(3);
        if (xpBook.getItem() instanceof XpBook) {
            for (int i = 0; i < 3; ++i) {
                if (!this.inventory.get(i).isEmpty()
                        && this.inventory.get(i).getTag() != null
                        && this.inventory.get(i).getTag().toString().contains("minecraft:mundane")) {
                    ItemStack xpBottle = new ItemStack(Items.EXPERIENCE_BOTTLE);
                    int experience = Math.min(ConstantsConfig.XP_FROM_BOOK, xpBook.getDamage());
                    xpBook.setDamage(xpBook.getDamage() - experience);

                    MyComponents.XP_AMOUNT.get(xpBottle).setRandomValue(experience);
                    this.inventory.set(i, xpBottle);
                }
            }

            this.inventory.set(3, xpBook);
            if (this.world != null)
                this.world.syncWorldEvent(1035, this.getPos(), 0);
            ci.cancel();
        }
    }
}
