package com.github.charlyb01.xpstorage;


public class XpBookI extends XpBook {
    private final static int maxExperience = 1395;    // First 30 lvl

    public XpBookI() {
        super(new Settings().maxDamage(maxExperience));
    }

    @Override
    protected int getMaxExperience() {
        return maxExperience;
    }
}
