package com.github.charlyb01.xpstorage.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "books")
public class BookConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    public Book baseBook = new Book();

    public static class Book {
        @ConfigEntry.Gui.RequiresRestart
        @ConfigEntry.BoundedDiscrete(min = 10, max = 100)
        public int capacity = 15;
        @ConfigEntry.Gui.RequiresRestart
        @ConfigEntry.BoundedDiscrete(min = 50, max = 100)
        public int xpFromUsing = 85;
        @ConfigEntry.Gui.RequiresRestart
        @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
        public int xpFromBrewing = 1;
        @ConfigEntry.Gui.RequiresRestart
        @ConfigEntry.ColorPicker
        public int barColor = Integer.parseInt("1c53a8", 16);
    }
}
