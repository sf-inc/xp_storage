package com.github.charlyb01.xpstorage.component;

import com.github.charlyb01.xpstorage.Utils;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.Component;

class XpAmountComponent implements Component, ExperienceComponent {
    private int level = 0;
    private int amount = 0;

    @Override
    public int getAmount() { return this.amount; }

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
    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        this.amount = tag.getInt("xp_amount");
        this.level = tag.getInt("xp_level");
    }

    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        tag.putInt("xp_amount", this.amount);
        tag.putInt("xp_level", this.level);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof XpAmountComponent &&
                ((XpAmountComponent) obj).amount == this.amount;
    }
}
