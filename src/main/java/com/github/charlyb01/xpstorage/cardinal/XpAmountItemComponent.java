package com.github.charlyb01.xpstorage.cardinal;

import com.github.charlyb01.xpstorage.Utils;
import dev.onyxstudios.cca.api.v3.item.ItemComponent;
import net.minecraft.item.ItemStack;

class XpAmountItemComponent extends ItemComponent implements ExperienceComponent {
    public XpAmountItemComponent(ItemStack stack) {
        super(stack);
    }

    @Override
    public int getAmount() {
        return this.getInt("xp_amount");
    }

    @Override
    public int getLevel() {
        return this.getInt("xp_level");
    }

    @Override
    public void setAmount(final int amount) {
        this.putInt("xp_amount", amount);
        this.putInt("xp_level", Utils.getLevelFromExperience(amount));
    }

    @Override
    public void setLevel(final int level) {
        this.putInt("xp_amount", Utils.getExperienceToLevel(level));
        this.putInt("xp_level", level);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof XpAmountItemComponent &&
                ((XpAmountItemComponent) obj).getAmount() == this.getAmount();
    }
}
