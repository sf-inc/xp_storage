package com.github.charlyb01.xpstorage.mixin;

import com.github.charlyb01.xpstorage.XpBook;
import com.github.charlyb01.xpstorage.Xpstorage;
import com.github.charlyb01.xpstorage.cardinal.MyComponents;
import com.github.charlyb01.xpstorage.config.ModConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrewingStandBlockEntity.class)
public abstract class BrewingStandMixin extends LockableContainerBlockEntity {

    protected BrewingStandMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @Inject(method = "canCraft", at = @At("HEAD"), cancellable = true)
    private static void canUseXpBooks(DefaultedList<ItemStack> slots, CallbackInfoReturnable<Boolean> cir) {
        ItemStack itemStack = slots.get(3);
        if (!itemStack.isEmpty()
                && itemStack.getItem() instanceof XpBook
                && itemStack.getDamage() > 0) {
            for(int i = 0; i < 3; ++i) {
                ItemStack itemStack2 = slots.get(i);
                if (!itemStack2.isEmpty()
                        && itemStack2.getNbt() != null
                        && itemStack2.getNbt().toString().contains("minecraft:mundane")) {
                    cir.setReturnValue(true);
                }
            }
        }
    }

    @Inject(method = "craft", at = @At("HEAD"), cancellable = true)
    private static void craftXpBottles(World world, BlockPos pos, DefaultedList<ItemStack> slots, CallbackInfo ci) {
        ItemStack xpBook = slots.get(3);
        if (xpBook.getItem() instanceof XpBook) {
            int xpFromBrewing = ModConfig.get().bottles.book1.xpFromBrewing;
            int lowerBoundRandom = ModConfig.get().bottles.book1.lowerBoundRandom;
            int upperBoundRandom = ModConfig.get().bottles.book1.upperBoundRandom;

            if (xpBook.isOf(Xpstorage.xp_book2)) {
                xpFromBrewing = ModConfig.get().bottles.book2.xpFromBrewing;
                lowerBoundRandom = ModConfig.get().bottles.book2.lowerBoundRandom;
                upperBoundRandom = ModConfig.get().bottles.book2.upperBoundRandom;
            } else if (xpBook.isOf(Xpstorage.xp_book3)) {
                xpFromBrewing = ModConfig.get().bottles.book3.xpFromBrewing;
                lowerBoundRandom = ModConfig.get().bottles.book3.lowerBoundRandom;
                upperBoundRandom = ModConfig.get().bottles.book3.upperBoundRandom;
            }

            for (int i = 0; i < 3; ++i) {
                if (!slots.get(i).isEmpty()
                        && slots.get(i).getNbt() != null
                        && slots.get(i).getNbt().toString().contains("minecraft:mundane")) {
                    ItemStack xpBottle = new ItemStack(Items.EXPERIENCE_BOTTLE);
                    int experience = Math.min(xpFromBrewing, xpBook.getDamage());
                    xpBook.setDamage(xpBook.getDamage() - experience);

                    MyComponents.XP_AMOUNT.get(xpBottle).setRandomValue(experience, world.getRandom(),
                            lowerBoundRandom, upperBoundRandom);
                    slots.set(i, xpBottle);
                }
            }

            slots.set(3, xpBook);
            if (world != null)
                world.syncWorldEvent(1035, pos, 0);
            ci.cancel();
        }
    }
}
