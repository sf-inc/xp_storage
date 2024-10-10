package com.github.charlyb01.xpstorage.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.List;

@Config(name = "books")
public class BookConfig implements ConfigData {
    public List<Book> bookList = List.of(
            new Book(30, 85, 1, Integer.parseInt("a1fbe8", 16)),
            new Book(42, 90, 3, Integer.parseInt("5a575a", 16)),
            new Book(69, 95, 10, Integer.parseInt("e0e277", 16))
    );

    public static class Book {
        @ConfigEntry.BoundedDiscrete(min = 10, max = 100)
        public int capacity;

        @ConfigEntry.BoundedDiscrete(min = 50, max = 100)
        public int xpFromUsing;

        @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
        public int xpFromBrewing;

        @ConfigEntry.ColorPicker
        public int barColor;

        public Book(final int capacity, final int xpFromUsing, final int xpFromBrewing, final int barColor) {
            this.capacity = capacity;
            this.xpFromUsing = xpFromUsing;
            this.xpFromBrewing = xpFromBrewing;
            this.barColor = barColor;
        }

        public Book() {
            this(10,100,1,Integer.parseInt("ffffff", 16));
        }
    }
}
