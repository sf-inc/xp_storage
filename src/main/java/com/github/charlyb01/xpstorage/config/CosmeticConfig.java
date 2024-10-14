package com.github.charlyb01.xpstorage.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "cosmetic")
public class CosmeticConfig implements ConfigData {
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public ExperienceTooltip bookTooltip = ExperienceTooltip.LEVEL;
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public ExperienceTooltip bottleTooltip = ExperienceTooltip.LEVEL;

    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int glint = 100;
}
