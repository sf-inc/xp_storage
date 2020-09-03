package com.github.charlyb01.xpstorage;


public class XpBookIII extends XpBook {
    private final static int maxExperience = 12433;    // First 69 lvl

    public XpBookIII() {
        super(new Settings().maxDamage(maxExperience));
    }

    @Override
    protected int getMaxExperience() {
        return maxExperience;
    }
}
