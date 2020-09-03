package com.github.charlyb01.xpstorage;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
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
        return Utils.onUse(world, user, hand, maxExperience);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.getDamage() < maxExperience;
    }

}
