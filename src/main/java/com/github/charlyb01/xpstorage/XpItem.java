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

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) {
        stack.setDamage(maxExperience);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getMainHandStack();
        int remainingPlace = stack.getDamage();
        int playerExperience = user.totalExperience;

        user.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);

        if(!world.isClient) {
            // Empty / Fill
            if (user.isSneaking()) {
                user.addExperience(maxExperience-remainingPlace);
                stack.setDamage(maxExperience);
            } else {
                user.totalExperience = 0;
                user.experienceProgress = 0;
                user.experienceLevel = 0;

                // Check max value
                if (remainingPlace < playerExperience) {
                    stack.setDamage(0);
                    user.addExperience(playerExperience-remainingPlace);
                } else {
                    stack.setDamage(remainingPlace-playerExperience);
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
