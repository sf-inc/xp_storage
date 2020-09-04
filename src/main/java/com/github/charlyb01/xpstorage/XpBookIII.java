package com.github.charlyb01.xpstorage;


import net.minecraft.util.Rarity;

public class XpBookIII extends XpBook {
    private final static int maxLevel = 69;
    private final static int maxExperience = Utils.getExperienceToLevel(maxLevel);

    public XpBookIII() {
        super(new Settings().maxDamage(maxExperience).rarity(Rarity.RARE).fireproof());
    }

    @Override
    protected int getMaxExperience() {
        return maxExperience;
    }
}
