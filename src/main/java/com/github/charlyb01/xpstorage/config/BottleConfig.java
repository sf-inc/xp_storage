package com.github.charlyb01.xpstorage.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "bottles")
public class BottleConfig implements ConfigData {
    public boolean enableBrewing = true;

    @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
    public int maxLevel = 30;

    @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
    public int xpFromBrewing = 1;

    @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
    public int xpFromBrewing2 = 3;

    @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
    public int xpFromBrewing3 = 10;
}
