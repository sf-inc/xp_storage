package com.github.charlyb01.xpstorage.cardinal;

import com.github.charlyb01.xpstorage.Utils;
import net.minecraft.nbt.NbtCompound;

class XpAmountComponent implements ExperienceComponent {
    private int level = 0;
    private int amount = 0;

    @Override
    public int getAmount() { return this.amount; }

    @Override
    public int getLevel() { return this.level; }

    @Override
    public void setAmount(final int amount) {
        this.amount = amount;
        this.level = Utils.getLevelFromExperience(amount);
    }

    @Override
    public void setLevel(final int level) {
        this.amount = Utils.getExperienceToLevel(level);
        this.level = level;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.amount = tag.getInt("xp_amount");
        this.level = tag.getInt("xp_level");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("xp_amount", this.amount);
        tag.putInt("xp_level", this.level);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof XpAmountComponent &&
                ((XpAmountComponent) obj).amount == this.amount;
    }
}
