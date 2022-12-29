package com.github.charlyb01.xpstorage.cardinal;

import dev.onyxstudios.cca.api.v3.item.ItemComponent;
import net.minecraft.item.ItemStack;

class XpAmountItemComponent extends ItemComponent implements IntComponent {
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
    public boolean equals(Object obj) {
        return obj instanceof XpAmountItemComponent &&
                ((XpAmountItemComponent) obj).getValue() == this.getValue();
    }
}
