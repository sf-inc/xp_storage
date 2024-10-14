package com.github.charlyb01.xpstorage.mixin.brewing;

import com.github.charlyb01.xpstorage.Utils;
import com.github.charlyb01.xpstorage.item.XpBook;
import com.github.charlyb01.xpstorage.component.MyComponents;
import com.github.charlyb01.xpstorage.component.XpAmountData;
import com.github.charlyb01.xpstorage.config.ModConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.BrewingRecipeRegistry;
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
    private static void canUseXpBooks(BrewingRecipeRegistry brewingRecipeRegistry, DefaultedList<ItemStack> slots, CallbackInfoReturnable<Boolean> cir) {
        ItemStack ingredient = slots.get(3);
        if (ingredient.isEmpty() || !(ingredient.getItem() instanceof XpBook)
                || !ingredient.contains(MyComponents.XP_COMPONENT))
            return;

        final int bookExperience = ingredient.get(MyComponents.XP_COMPONENT).amount();
        int levelIncrement = XpBook.getXpFromBrewing(ingredient);

        for (int i = 0; i < 3; ++i) {
            ItemStack potion = slots.get(i);
            // Due to a minecraft edge case, you can stack potion with quick move.
            // getMaxItemCount is not taken into account when item is stackable.
            // The fix would be trivial, but not with mixin
            if (potion.isEmpty() || potion.getCount() > 1)
                continue;

            if (potion.isOf(Items.EXPERIENCE_BOTTLE) && potion.contains(MyComponents.XP_COMPONENT)) {
                final int currentLevel = potion.get(MyComponents.XP_COMPONENT).level();
                final int nextLevel = currentLevel + levelIncrement;
                final int xpForNextLevel = Utils.getExperienceFromLevelToLevel(currentLevel, nextLevel);
                if (bookExperience >= xpForNextLevel && nextLevel <= ModConfig.get().bottles.maxLevel) {
                    cir.setReturnValue(true);
                }
            } else if (Utils.isMundanePotion(potion)) {
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
        if (ingredient.isEmpty() || !(ingredient.getItem() instanceof XpBook)
                || !ingredient.contains(MyComponents.XP_COMPONENT))
            return;

        int levelIncrement = XpBook.getXpFromBrewing(ingredient);

        for (int i = 0; i < 3; ++i) {
            ItemStack potion = slots.get(i);
            // See above
            if (potion.isEmpty() || potion.getCount() > 1)
                continue;

            final int bookExperience = ingredient.getOrDefault(MyComponents.XP_COMPONENT, XpAmountData.EMPTY).amount();

            if (potion.isOf(Items.EXPERIENCE_BOTTLE) && potion.contains(MyComponents.XP_COMPONENT)) {
                final int currentLevel = potion.get(MyComponents.XP_COMPONENT).level();
                final int nextLevel = currentLevel + levelIncrement;
                final int xpForNextLevel = Utils.getExperienceFromLevelToLevel(currentLevel, nextLevel);
                if (bookExperience >= xpForNextLevel && nextLevel <= ModConfig.get().bottles.maxLevel) {
                    potion.set(MyComponents.XP_COMPONENT, new XpAmountData(Utils.getExperienceToLevel(nextLevel), nextLevel));
                    int amount = bookExperience - xpForNextLevel;
                    ingredient.set(MyComponents.XP_COMPONENT, new XpAmountData(amount, Utils.getLevelFromExperience(amount)));
                }
            } else if (Utils.isMundanePotion(potion)) {
                final int xpForFirstLevels = Utils.getExperienceToLevel(levelIncrement);
                if (bookExperience >= xpForFirstLevels && levelIncrement <= ModConfig.get().bottles.maxLevel) {
                    ItemStack xpBottle = new ItemStack(Items.EXPERIENCE_BOTTLE);
                    slots.set(i, xpBottle);
                    xpBottle.set(MyComponents.XP_COMPONENT, new XpAmountData(xpForFirstLevels, levelIncrement));
                    int amount = bookExperience - xpForFirstLevels;
                    ingredient.set(MyComponents.XP_COMPONENT, new XpAmountData(amount, Utils.getLevelFromExperience(amount)));
                }
            }
        }

        if (world != null) {
            world.syncWorldEvent(1035, pos, 0);
        }
        ci.cancel();
    }
}
