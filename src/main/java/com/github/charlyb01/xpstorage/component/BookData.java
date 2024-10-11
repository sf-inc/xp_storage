package com.github.charlyb01.xpstorage.component;

import com.github.charlyb01.xpstorage.config.ModConfig;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record BookData(int level, int capacity, int xpFromUsing, int xpFromBrewing, int barColor) {
    public static final Codec<BookData> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Codec.INT.fieldOf("level").forGetter(BookData::level),
            Codec.INT.fieldOf("capacity").forGetter(BookData::capacity),
            Codec.INT.fieldOf("xpFromUsing").forGetter(BookData::xpFromUsing),
            Codec.INT.fieldOf("xpFromBrewing").forGetter(BookData::xpFromBrewing),
            Codec.INT.fieldOf("barColor").forGetter(BookData::barColor)
    ).apply(builder, BookData::new));

    public static BookData getDefault() {
        return new BookData(0,
                ModConfig.get().books.baseBook.capacity,
                ModConfig.get().books.baseBook.xpFromUsing,
                ModConfig.get().books.baseBook.xpFromBrewing,
                ModConfig.get().books.baseBook.barColor);
    }
}
