package com.github.charlyb01.xpstorage;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class Utils {

    private static int getLevelExperience(int experienceLevel) {
        if (experienceLevel >= 30) {
            return 112 + (experienceLevel - 30) * 9;
        } else {
            return experienceLevel >= 15 ? 37 + (experienceLevel - 15) * 5 : 7 + experienceLevel * 2;
        }
    }

    public static int getExperienceToLevel(int level) {
        int experience = 0;
        for (int i = 0; i < level; i++) {
            experience += getLevelExperience(i);
        }
        return experience;
    }

    public static TypedActionResult<ItemStack> onUse(World world, PlayerEntity user, Hand hand, int maxExperience) {
        ItemStack stack = (user.getMainHandStack().getItem() instanceof XpBook)?user.getMainHandStack():user.getOffHandStack();
        int bookExperience = stack.getDamage();
        int playerExperience = getExperienceToLevel(user.experienceLevel);
        playerExperience += user.experienceProgress*getLevelExperience(user.experienceLevel);

        if(world.isClient) {
            if( (user.isSneaking() && bookExperience > 0)
                    || (!user.isSneaking() && playerExperience > 0 && bookExperience < maxExperience) ) {

                user.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
            }
        }

        else {
            // Empty / Fill
            if (user.isSneaking()) {
                user.addExperience(bookExperience);
                stack.setDamage(0);
            } else {
                // Check max value
                if (maxExperience-bookExperience < playerExperience) {
                    user.addExperience(bookExperience-maxExperience);
                    stack.setDamage(maxExperience);
                } else {
                    stack.setDamage(bookExperience+playerExperience);
                    user.addExperience(-playerExperience);
                }
            }
        }

        return new TypedActionResult<>(ActionResult.SUCCESS, user.getStackInHand(hand));
    }
}
