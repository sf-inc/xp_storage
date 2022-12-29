package com.github.charlyb01.xpstorage;

public class Utils {

    public static int getLevelExperience(final int experienceLevel) {
        if (experienceLevel >= 30) {
            return 112 + (experienceLevel - 30) * 9;
        } else {
            return experienceLevel >= 15 ? 37 + (experienceLevel - 15) * 5 : 7 + experienceLevel * 2;
        }
    }

    public static int getExperienceFromLevelToLevel(final int fromLevel, final int toLevel) {
        int experience = 0;
        for (int i = fromLevel; i < toLevel; i++) {
            experience += getLevelExperience(i);
        }
        return experience;
    }

    public static int getExperienceToLevel(int toLevel) {
        return getExperienceFromLevelToLevel(0, toLevel);
    }
}
