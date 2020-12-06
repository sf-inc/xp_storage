package com.github.charlyb01.xpstorage;


import com.github.charlyb01.xpstorage.config.ConstantsConfig;
import net.minecraft.util.Rarity;

public class XpBookII extends XpBook {
    private final static int maxLevel = ConstantsConfig.MAX_LEVEL_II;
    private final static int maxExperience = Utils.getExperienceToLevel(maxLevel);

    public XpBookII() {
        super(new Settings().maxDamage(maxExperience).rarity(Rarity.UNCOMMON).fireproof());
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
