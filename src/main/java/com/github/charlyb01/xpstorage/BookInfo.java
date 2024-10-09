package com.github.charlyb01.xpstorage;

import net.minecraft.item.ItemStack;

public interface BookInfo {
    int getBookLevel(ItemStack stack);
    int getMaxXpLevel(ItemStack stack);
    int getMaxXpAmount(ItemStack stack);
    int getXpFromUsing(ItemStack stack);
}
