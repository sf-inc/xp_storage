package com.github.charlyb01.xpstorage.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "books")
public class BookConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    public Book book = new Book(30, 85);

    public static class Book {
        @ConfigEntry.BoundedDiscrete(min = 10, max = 100)
        @ConfigEntry.Gui.RequiresRestart
        public int capacity;

        @ConfigEntry.BoundedDiscrete(min = 50, max = 100)
        @ConfigEntry.Gui.RequiresRestart
        public int xpFromUsing;

        public Book(final int capacity, final int xpFromUsing) {
            this.capacity = capacity;
            this.xpFromUsing = xpFromUsing;
        }
    }
}
