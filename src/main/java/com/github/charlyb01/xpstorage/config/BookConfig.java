package com.github.charlyb01.xpstorage.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.List;

@Config(name = "books")
public class BookConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    public Book baseBook = new Book();

    public List<BookUpgrade> bookUpgrades = List.of(
            new BookUpgrade(30, 90, 3, Integer.parseInt("a1fbe8", 16), "minecraft:diamond"),
            new BookUpgrade(50, 95, 5, Integer.parseInt("5a575a", 16), "minecraft:netherite_ingot"),
            new BookUpgrade(100, 100, 10, Integer.parseInt("e0e277", 16), "minecraft:nether_star")
    );

    public static class Book {
        @ConfigEntry.BoundedDiscrete(min = 10, max = 100)
        public int capacity = 15;
        @ConfigEntry.BoundedDiscrete(min = 50, max = 100)
        public int xpFromUsing = 85;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
        public int xpFromBrewing = 1;
        @ConfigEntry.ColorPicker
        public int barColor = Integer.parseInt("1c53a8", 16);
    }

    public static class BookUpgrade {
        @ConfigEntry.BoundedDiscrete(min = 10, max = 100)
        public int capacity;
        @ConfigEntry.BoundedDiscrete(min = 50, max = 100)
        public int xpFromUsing;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
        public int xpFromBrewing;
        @ConfigEntry.ColorPicker
        public int barColor;
        public String item;

        public BookUpgrade(final int capacity, final int xpFromUsing, final int xpFromBrewing, final int barColor,
                           final String item) {
            this.capacity = capacity;
            this.xpFromUsing = xpFromUsing;
            this.xpFromBrewing = xpFromBrewing;
            this.barColor = barColor;
            this.item = item;
        }

        public BookUpgrade() {
            this(10, 100, 1, Integer.parseInt("ffffff", 16), "minecraft:diamond");
        }
    }
}
