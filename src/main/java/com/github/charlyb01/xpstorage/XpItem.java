package com.github.charlyb01.xpstorage;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class XpItem extends Item {
    private final static int maxExperience = 1395;    // First 30lvl

    public XpItem() {
        super(new Item.Settings().maxDamage(maxExperience).group(ItemGroup.MISC));
    }

    private static int getLevelExperience(int experienceLevel) {
        if (experienceLevel >= 30) {
            return 112 + (experienceLevel - 30) * 9;
        } else {
            return experienceLevel >= 15 ? 37 + (experienceLevel - 15) * 5 : 7 + experienceLevel * 2;
        }
    }

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) {
        stack.setDamage(maxExperience);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getMainHandStack();
        int remainingPlace = stack.getDamage();
        int playerExperience = 0;

        for (int level = 0; level < user.experienceLevel; level++) {
            playerExperience += getLevelExperience(level);
        }
        playerExperience += user.experienceProgress*getLevelExperience(user.experienceLevel);

        if(world.isClient) {
            if( (user.isSneaking() && remainingPlace < maxExperience)
                || (!user.isSneaking() && playerExperience > 0) ) {

                user.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                System.out.println(playerExperience);
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

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.getDamage() < maxExperience;
    }

}
