package com.github.charlyb01.xpstorage.cardinal;

import net.minecraft.nbt.NbtCompound;

class XpAmountComponent implements IntComponent {
    private int value = 0;

    @Override
    public int getValue() { return this.value; }

    @Override
    public void setValue(final int value) {
        this.value = value;
    }

    @Override
    public void readFromNbt(NbtCompound tag) { this.value = tag.getInt("xp_amount"); }

    @Override
    public void writeToNbt(NbtCompound tag) { tag.putInt("xp_amount", this.value); }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof XpAmountComponent &&
                ((XpAmountComponent) obj).value == this.value;
    }
}
