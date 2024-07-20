package com.github.charlyb01.xpstorage;

import com.github.charlyb01.xpstorage.component.MyComponents;
import com.github.charlyb01.xpstorage.component.XpAmountData;
import com.github.charlyb01.xpstorage.config.ModConfig;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.List;

public class XpBook extends Item implements BookInfo {
    private final int maxLevel;
    private final int maxExperience;
    private final int xpFromUsing;
    private final int colorBar;

    public XpBook(final int maxLevel, final int xpFromUsing, final int colorBar, final boolean isFireproof, final Rarity rarity) {
        super(isFireproof ? new Item.Settings().maxCount(1).rarity(rarity).component(MyComponents.XP_COMPONENT, XpAmountData.EMPTY).fireproof()
                : new Item.Settings().maxCount(1).rarity(rarity).component(MyComponents.XP_COMPONENT, XpAmountData.EMPTY));

        this.maxLevel = maxLevel;
        this.maxExperience = Utils.getExperienceToLevel(maxLevel);
        this.xpFromUsing = xpFromUsing;
        this.colorBar = colorBar;
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
        XpAmountData xpAmountData = stack.getOrDefault(MyComponents.XP_COMPONENT, XpAmountData.EMPTY);
        final int bookLevel = xpAmountData.level();
        tooltip.add(Text.translatable("item.xp_storage.xp_books.tooltip", bookLevel, this.maxLevel)
                .formatted(Formatting.GRAY));

        if (ModConfig.get().cosmetic.bookTooltip) {
            final int bookExperience = xpAmountData.amount();
            tooltip.add(Text.translatable("item.xp_storage.xp_books.advanced_tooltip", bookExperience, this.maxExperience)
                    .formatted(Formatting.GRAY).formatted(Formatting.ITALIC));
        }
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        XpAmountData xpAmountData = stack.getOrDefault(MyComponents.XP_COMPONENT, XpAmountData.EMPTY);
        final int bookExperience = xpAmountData.amount();
        return (bookExperience / (float) this.maxExperience) * 100 >= ModConfig.get().cosmetic.glint;
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return this.colorBar;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        XpAmountData xpAmountData = stack.getOrDefault(MyComponents.XP_COMPONENT, XpAmountData.EMPTY);
        final int bookExperience = xpAmountData.amount();
        return Math.round((bookExperience * 13) / (float) this.maxExperience);
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        XpAmountData xpAmountData = stack.getOrDefault(MyComponents.XP_COMPONENT, XpAmountData.EMPTY);
        return xpAmountData.amount() > 0;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = (user.getMainHandStack().getItem() instanceof XpBook) ? user.getMainHandStack() : user.getOffHandStack();
        XpAmountData xpAmountData = stack.getOrDefault(MyComponents.XP_COMPONENT, XpAmountData.EMPTY);
        final int bookExperience = xpAmountData.amount();
        int playerExperience = Utils.getPlayerExperience(user);

        if (world.isClient) {
            // Play sound when filling
            if (!user.isSneaking() && playerExperience > 0 && bookExperience < this.maxExperience) {
                user.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
            }
        } else {
            // Empty / Fill
            if (user.isSneaking()) {
                final int retrievedExperience = Math.round(bookExperience * (this.xpFromUsing / 100.0F));
                ExperienceOrbEntity.spawn((ServerWorld) world, user.getPos(), retrievedExperience);
                stack.set(MyComponents.XP_COMPONENT, XpAmountData.EMPTY);
            } else {
                // Check max value
                if (this.maxExperience - bookExperience < playerExperience) {
                    user.addExperience(bookExperience - this.maxExperience);
                    stack.set(MyComponents.XP_COMPONENT, new XpAmountData(this.maxExperience, this.maxLevel));
                } else {
                    int amount = bookExperience + playerExperience;
                    stack.set(MyComponents.XP_COMPONENT, new XpAmountData(amount, Utils.getLevelFromExperience(amount)));
                    user.addExperience(-playerExperience);
                }
            }
        }

        return new TypedActionResult<>(ActionResult.SUCCESS, user.getStackInHand(hand));
    }

    @Override
    public int getMaxLevel() {
        return this.maxLevel;
    }

    @Override
    public int getMaxExperience() {
        return this.maxExperience;
    }

    @Override
    public int getXpFromUsing() {
        return this.xpFromUsing;
    }

    @Override
    public int getColorBar() {
        return this.colorBar;
    }
}
