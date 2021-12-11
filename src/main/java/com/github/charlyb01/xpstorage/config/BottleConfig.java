package com.github.charlyb01.xpstorage.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "bottles")
public class BottleConfig implements ConfigData {
    @ConfigEntry.BoundedDiscrete(min = 10, max = 1000)
    public int xpFromBrewing = 100;

    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int lowerBoundRandom = 85;

    @ConfigEntry.BoundedDiscrete(min = 100, max = 200)
    public int upperBoundRandom = 130;
}
