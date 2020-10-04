package com.github.charlyb01.xpstorage;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class XpBook extends Item {
    protected XpBook(Settings settings) {
        super(settings.group(ItemGroup.MISC));
    }

    protected abstract int getMaxLevel();
    public abstract int getMaxExperience();

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("item.xp_storage.xp_books.tooltip", getMaxLevel()));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.getDamage() == getMaxExperience();
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return Utils.onUse(world, user, hand, getMaxExperience());
    }
}
