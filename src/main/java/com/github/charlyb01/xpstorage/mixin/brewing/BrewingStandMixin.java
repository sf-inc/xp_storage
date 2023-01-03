package com.github.charlyb01.xpstorage.mixin.brewing;

import com.github.charlyb01.xpstorage.Utils;
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
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
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
        ItemStack ingredient = slots.get(3);
        if (ingredient.isEmpty() || !(ingredient.getItem() instanceof XpBook))
            return;

        final int bookExperience = MyComponents.XP_COMPONENT.get(ingredient).getAmount();
        int levelIncrement = ModConfig.get().bottles.xpFromBrewing1;
        if (ingredient.isOf(Xpstorage.xp_book2)) {
            levelIncrement = ModConfig.get().bottles.xpFromBrewing2;
        } else if (ingredient.isOf(Xpstorage.xp_book3)) {
            levelIncrement = ModConfig.get().bottles.xpFromBrewing3;
        }

        for (int i = 0; i < 3; ++i) {
            ItemStack potion = slots.get(i);
            if (potion.isEmpty())
                continue;

            if (potion.isOf(Items.EXPERIENCE_BOTTLE)) {
                final int currentLevel = MyComponents.XP_COMPONENT.get(potion).getLevel();
                final int nextLevel = currentLevel + levelIncrement;
                final int xpForNextLevel = Utils.getExperienceFromLevelToLevel(currentLevel, nextLevel);
                if (bookExperience >= xpForNextLevel && nextLevel <= ModConfig.get().bottles.maxLevel) {
                    cir.setReturnValue(true);
                }
            } else if (potion.isOf(Items.POTION) && PotionUtil.getPotion(potion).equals(Potions.MUNDANE)) {
                if (bookExperience >= Utils.getExperienceFromLevel(levelIncrement)
                        && levelIncrement <= ModConfig.get().bottles.maxLevel) {
                    cir.setReturnValue(true);
                }
            }
        }
    }

    @Inject(method = "craft", at = @At("HEAD"), cancellable = true)
    private static void craftXpBottles(World world, BlockPos pos, DefaultedList<ItemStack> slots, CallbackInfo ci) {
        ItemStack ingredient = slots.get(3);
        if (ingredient.isEmpty() || !(ingredient.getItem() instanceof XpBook))
            return;

        int levelIncrement = ModConfig.get().bottles.xpFromBrewing1;
        if (ingredient.isOf(Xpstorage.xp_book2)) {
            levelIncrement = ModConfig.get().bottles.xpFromBrewing2;
        } else if (ingredient.isOf(Xpstorage.xp_book3)) {
            levelIncrement = ModConfig.get().bottles.xpFromBrewing3;
        }

        for (int i = 0; i < 3; ++i) {
            ItemStack potion = slots.get(i);
            if (potion.isEmpty())
                continue;

            if (potion.isOf(Items.EXPERIENCE_BOTTLE)) {
                final int currentLevel = MyComponents.XP_COMPONENT.get(potion).getLevel();
                final int nextLevel = currentLevel + levelIncrement;
                final int xpForNextLevel = Utils.getExperienceFromLevelToLevel(currentLevel, nextLevel);
                final int bookExperience = MyComponents.XP_COMPONENT.get(ingredient).getAmount();
                if (bookExperience >= xpForNextLevel && nextLevel <= ModConfig.get().bottles.maxLevel) {
                    MyComponents.XP_COMPONENT.get(potion).setLevel(nextLevel);
                    MyComponents.XP_COMPONENT.get(ingredient).setAmount(bookExperience - xpForNextLevel);
                }
            } else if (potion.isOf(Items.POTION) && PotionUtil.getPotion(potion).equals(Potions.MUNDANE)) {
                final int xpForFirstLevels = Utils.getExperienceToLevel(levelIncrement);
                final int bookExperience = MyComponents.XP_COMPONENT.get(ingredient).getAmount();
                if (bookExperience >= xpForFirstLevels && levelIncrement <= ModConfig.get().bottles.maxLevel) {
                    ItemStack xpBottle = new ItemStack(Items.EXPERIENCE_BOTTLE);
                    slots.set(i, xpBottle);
                    MyComponents.XP_COMPONENT.get(xpBottle).setLevel(levelIncrement);
                    MyComponents.XP_COMPONENT.get(ingredient).setAmount(bookExperience - xpForFirstLevels);
                }
            }
        }

        if (world != null) {
            world.syncWorldEvent(1035, pos, 0);
        }
        ci.cancel();
    }
}
