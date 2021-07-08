package com.github.charlyb01.xpstorage;

import com.github.charlyb01.xpstorage.config.ModConfig;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

public class XpBookIII extends XpBook {
    private final static int maxLevel = ModConfig.get().MAX_LEVEL_III;
    private final static int maxExperience = Utils.getExperienceToLevel(maxLevel);

    public XpBookIII() {
        super(new Item.Settings().maxDamage(maxExperience).rarity(Rarity.RARE).fireproof());
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
