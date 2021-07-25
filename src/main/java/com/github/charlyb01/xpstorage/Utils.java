package com.github.charlyb01.xpstorage;

public class Utils {

    public static int getLevelExperience(int experienceLevel) {
        if (experienceLevel >= 30) {
            return 112 + (experienceLevel - 30) * 9;
        } else {
            return experienceLevel >= 15 ? 37 + (experienceLevel - 15) * 5 : 7 + experienceLevel * 2;
        }
    }

    public static int getExperienceToLevel(int level) {
        int experience = 0;
        for (int i = 0; i < level; i++) {
            experience += getLevelExperience(i);
        }
        return experience;
    }
}
