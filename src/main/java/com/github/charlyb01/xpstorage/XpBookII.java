package com.github.charlyb01.xpstorage;


public class XpBookII extends XpBook {
    private final static int maxExperience = 3334;    // First 42 lvl

    public XpBookII() {
        super(new Settings().maxDamage(maxExperience));
    }

    @Override
    protected int getMaxExperience() {
        return maxExperience;
    }
}
