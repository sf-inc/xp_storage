package com.github.charlyb01.xpstorage.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "cosmetic")
public class CosmeticConfig implements ConfigData {
    public boolean bookTooltip = false;

    public boolean bottleTooltip = true;

    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int glint = 33;

    @ConfigEntry.ColorPicker
    public int colorBar1 = Integer.parseInt("9518d3", 16);

    @ConfigEntry.ColorPicker
    public int colorBar2 = Integer.parseInt("ab94bb", 16);

    @ConfigEntry.ColorPicker
    public int colorBar3 = Integer.parseInt("a4d5cd", 16);
}
