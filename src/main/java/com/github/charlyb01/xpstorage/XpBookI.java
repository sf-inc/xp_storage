package com.github.charlyb01.xpstorage;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class XpBookI extends XpBook {
    private final static int maxLevel = 30;
    private final static int maxExperience = Utils.getExperienceToLevel(maxLevel);

    public XpBookI() {
        super(new Settings().maxDamage(maxExperience));
    }

    @Override
    protected int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public int getMaxExperience() {
        return maxExperience;
    }
}
