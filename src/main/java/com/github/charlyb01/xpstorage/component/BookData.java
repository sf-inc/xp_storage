package com.github.charlyb01.xpstorage.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record BookData(int level) {
    public static final BookData ZERO = new BookData(0);

    public static final Codec<BookData> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Codec.INT.fieldOf("level").forGetter(BookData::level)
    ).apply(builder, BookData::new));
}
