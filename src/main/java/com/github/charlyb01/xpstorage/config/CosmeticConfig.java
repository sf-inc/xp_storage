package com.github.charlyb01.xpstorage.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "cosmetic")
public class CosmeticConfig implements ConfigData {
    public boolean bookTooltip = false;

    public boolean bottleTooltip = true;

    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int glint = 100;

    @ConfigEntry.ColorPicker
    @ConfigEntry.Gui.RequiresRestart
    public int colorBar = Integer.parseInt("a1fbe8", 16);
}
