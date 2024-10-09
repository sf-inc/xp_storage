package com.github.charlyb01.xpstorage.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record XpAmountData(int amount, int level) {
    public static final XpAmountData EMPTY = new XpAmountData(0, 0);

    public static final Codec<XpAmountData> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Codec.INT.fieldOf("xp_amount").forGetter(XpAmountData::amount),
            Codec.INT.fieldOf("xp_level").forGetter(XpAmountData::level)
    ).apply(builder, XpAmountData::new));
}
