package com.github.charlyb01.xpstorage;


import net.minecraft.util.Rarity;

public class XpBookII extends XpBook {
    private final static int maxExperience = 3334;    // First 42 lvl

    public XpBookII() {
        super(new Settings().maxDamage(maxExperience).rarity(Rarity.UNCOMMON).fireproof());
    }

    @Override
    protected int getMaxExperience() {
        return maxExperience;
    }
}
