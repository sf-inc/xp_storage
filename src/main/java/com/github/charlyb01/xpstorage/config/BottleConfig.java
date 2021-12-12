package com.github.charlyb01.xpstorage.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "bottles")
public class BottleConfig implements ConfigData {
    public boolean enableBrewing = true;

    @ConfigEntry.Gui.CollapsibleObject
    public Book book1 = new Book(100, 95, 110);

    @ConfigEntry.Gui.CollapsibleObject
    public Book book2 = new Book(250, 90, 120);

    @ConfigEntry.Gui.CollapsibleObject
    public Book book3 = new Book(500, 85, 130);

    public static class Book {
        @ConfigEntry.BoundedDiscrete(min = 10, max = 1000)
        public int xpFromBrewing;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int lowerBoundRandom;

        @ConfigEntry.BoundedDiscrete(min = 100, max = 200)
        public int upperBoundRandom;

        public Book(final int xpFromBrewing, final int lowerBoundRandom, final int upperBoundRandom) {
            this.xpFromBrewing = xpFromBrewing;
            this.lowerBoundRandom = lowerBoundRandom;
            this.upperBoundRandom = upperBoundRandom;
        }
    }

}
