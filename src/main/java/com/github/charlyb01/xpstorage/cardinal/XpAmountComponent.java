package com.github.charlyb01.xpstorage.cardinal;

import com.github.charlyb01.xpstorage.config.ConstantsConfig;
import net.minecraft.nbt.CompoundTag;

class XpAmountComponent implements RandIntComponent {
    private int value = 0;

    @Override
    public int getValue() { return this.value; }

    @Override
    public void setRandomValue(final int bookAmount) {
        float random = (ConstantsConfig.UPPER_BOUND_RANDOM - ConstantsConfig.LOWER_BOUND_RANDOM) / 100.f;
        random = (int) (random * bookAmount) + 1;
        this.value = (int) (bookAmount * (ConstantsConfig.LOWER_BOUND_RANDOM / 100.f));
        this.value = this.value + (int) (Math.random() * random);
    }

    @Override
    public void readFromNbt(CompoundTag tag) { this.value = tag.getInt("xp_amount"); }

    @Override
    public void writeToNbt(CompoundTag tag) { tag.putInt("xp_amount", this.value); }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof XpAmountComponent &&
                ((XpAmountComponent) obj).value == this.value;
    }
}
