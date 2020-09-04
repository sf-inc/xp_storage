package com.github.charlyb01.xpstorage;


public class XpBookI extends XpBook {
    private final static int maxLevel = 30;
    private final static int maxExperience = Utils.getExperienceToLevel(maxLevel);

    public XpBookI() {
        super(new Settings().maxDamage(maxExperience));
    }

    @Override
    protected int getMaxExperience() {
        return maxExperience;
    }
}
