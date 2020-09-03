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

    public static TypedActionResult<ItemStack> onUse(World world, PlayerEntity user, Hand hand, int maxExperience) {
        ItemStack stack = (user.getMainHandStack().getItem() instanceof XpBook)?user.getMainHandStack():user.getOffHandStack();
        int remainingPlace = stack.getDamage();
        int playerExperience = 0;

        for (int level = 0; level < user.experienceLevel; level++) {
            playerExperience += getLevelExperience(level);
        }
        playerExperience += user.experienceProgress*getLevelExperience(user.experienceLevel);
        if(world.isClient) {
            if( (user.isSneaking() && remainingPlace < maxExperience)
                    || (!user.isSneaking() && playerExperience > 0 && remainingPlace > 0) ) {

                user.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
            }
        }

        else {
            // Empty / Fill
            if (user.isSneaking()) {
                user.addExperience(maxExperience-remainingPlace);
                stack.setDamage(maxExperience);
            } else {
                // Check max value
                if (remainingPlace < playerExperience) {
                    user.addExperience(-remainingPlace);
                    stack.setDamage(0);
                } else {
                    stack.setDamage(remainingPlace-playerExperience);
                    user.addExperience(-playerExperience);
                }
            }
        }

        return new TypedActionResult<>(ActionResult.SUCCESS, user.getStackInHand(hand));
    }
}
