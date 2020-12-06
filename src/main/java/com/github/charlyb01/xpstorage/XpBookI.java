package com.github.charlyb01.xpstorage;


import com.github.charlyb01.xpstorage.config.ConstantsConfig;

public class XpBookI extends XpBook {
    private final static int maxLevel = ConstantsConfig.MAX_LEVEL_I;
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
