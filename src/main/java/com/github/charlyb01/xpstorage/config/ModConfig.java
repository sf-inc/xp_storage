package com.github.charlyb01.xpstorage.config;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

@Config(name = "xp_storage")
public class ModConfig implements ConfigData {
    @ConfigEntry.BoundedDiscrete(min = 10, max = 50)
    @ConfigEntry.Gui.RequiresRestart
    public int MAX_LEVEL_I = 30;
    @ConfigEntry.BoundedDiscrete(min = 20, max = 100)
    @ConfigEntry.Gui.RequiresRestart
    public int MAX_LEVEL_II = 42;
    @ConfigEntry.BoundedDiscrete(min = 30, max = 200)
    @ConfigEntry.Gui.RequiresRestart
    public int MAX_LEVEL_III = 69;
    @ConfigEntry.BoundedDiscrete(min = 50, max = 100)
    public int XP_FROM_BOOK_USE = 90;

    @ConfigEntry.BoundedDiscrete(min = 10, max = 1000)
    public int XP_FROM_BOOK_BREW = 100;
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int LOWER_BOUND_RANDOM = 85;
    @ConfigEntry.BoundedDiscrete(min = 100, max = 200)
    public int UPPER_BOUND_RANDOM = 130;

    public static ModConfig get() {
        return AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }
}
