package com.github.charlyb01.xpstorage;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public abstract class XpBook extends Item {
    protected XpBook(Settings settings) {
        super(settings.group(ItemGroup.MISC));
    }

    protected abstract int getMaxExperience();

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.getDamage() == 0;
    }

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) {
        stack.setDamage(getMaxExperience());
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return Utils.onUse(world, user, hand, getMaxExperience());
    }
}
