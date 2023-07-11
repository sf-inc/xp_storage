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
    public int colorBar1 = Integer.parseInt("a1fbe8", 16);

    @ConfigEntry.ColorPicker
    @ConfigEntry.Gui.RequiresRestart
    public int colorBar2 = Integer.parseInt("5a575a", 16);

    @ConfigEntry.ColorPicker
    @ConfigEntry.Gui.RequiresRestart
    public int colorBar3 = Integer.parseInt("e0e277", 16);
}
