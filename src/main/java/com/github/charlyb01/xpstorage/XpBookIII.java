package com.github.charlyb01.xpstorage;


import net.minecraft.util.Rarity;

public class XpBookIII extends XpBook {
    private final static int maxExperience = 12433;    // First 69 lvl

    public XpBookIII() {
        super(new Settings().maxDamage(maxExperience).rarity(Rarity.RARE).fireproof());
    }

    @Override
    protected int getMaxExperience() {
        return maxExperience;
    }
}
