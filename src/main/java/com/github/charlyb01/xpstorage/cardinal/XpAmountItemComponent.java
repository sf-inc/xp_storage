package com.github.charlyb01.xpstorage.cardinal;

import com.github.charlyb01.xpstorage.config.ModConfig;
import dev.onyxstudios.cca.api.v3.item.ItemComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

import java.util.Random;

class XpAmountItemComponent extends ItemComponent implements RandIntComponent {
    public XpAmountItemComponent(ItemStack stack) {
        super(stack);
    }

    @Override
    public int getValue() { return this.getInt("xp_amount"); }

    @Override
    public void setValue(final int value) {
        this.putInt("xp_amount", value);
    }

    @Override
    public void setRandomValue(int bookAmount, final Random random) {
        float randomF = (ModConfig.get().UPPER_BOUND_RANDOM - ModConfig.get().LOWER_BOUND_RANDOM) / 100.f;
        int randomI = (int) (randomF * bookAmount) + 1;
        int value = (int) (bookAmount * (ModConfig.get().LOWER_BOUND_RANDOM / 100.f));
        value += random.nextInt(randomI);
        this.setValue(value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof XpAmountItemComponent &&
                ((XpAmountItemComponent) obj).getValue() == this.getValue();
    }
}
