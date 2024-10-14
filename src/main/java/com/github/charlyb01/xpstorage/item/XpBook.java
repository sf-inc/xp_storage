package com.github.charlyb01.xpstorage.item;

import com.github.charlyb01.xpstorage.Utils;
import com.github.charlyb01.xpstorage.component.BookData;
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

public class XpBook extends Item {
    public XpBook() {
        super(new Item.Settings()
                .maxCount(1)
                .component(MyComponents.BOOK_COMPONENT, BookData.getDefault())
                .component(MyComponents.XP_COMPONENT, XpAmountData.EMPTY)
        );
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
        XpAmountData xpAmountData = stack.getOrDefault(MyComponents.XP_COMPONENT, XpAmountData.EMPTY);
        final int bookLevel = xpAmountData.level();
        tooltip.add(Text.translatable("item.xp_storage.xp_books.tooltip", bookLevel, getMaxXpLevel(stack))
                .formatted(Formatting.GRAY));

        if (ModConfig.get().cosmetic.bookTooltip) {
            final int bookExperience = xpAmountData.amount();
            tooltip.add(Text.translatable("item.xp_storage.xp_books.advanced_tooltip", bookExperience, getMaxXpAmount(stack))
                    .formatted(Formatting.GRAY).formatted(Formatting.ITALIC));
        }
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        XpAmountData xpAmountData = stack.getOrDefault(MyComponents.XP_COMPONENT, XpAmountData.EMPTY);
        final int bookExperience = xpAmountData.amount();
        return (bookExperience / (float) getMaxXpAmount(stack)) * 100 >= ModConfig.get().cosmetic.glint;
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return stack.getOrDefault(MyComponents.BOOK_COMPONENT, BookData.getDefault()).barColor();
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        XpAmountData xpAmountData = stack.getOrDefault(MyComponents.XP_COMPONENT, XpAmountData.EMPTY);
        final int bookExperience = xpAmountData.amount();
        return Math.round((bookExperience * 13) / (float) getMaxXpAmount(stack));
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

        final int maxExperience = getMaxXpAmount(stack);
        final int maxLevel = getMaxXpLevel(stack);

        if (world.isClient) {
            // Play sound when filling
            if (!user.isSneaking() && playerExperience > 0 && bookExperience < maxExperience) {
                user.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
            }
        } else {
            // Empty / Fill
            if (user.isSneaking()) {
                final int retrievedExperience = Math.round(bookExperience * (getXpFromUsing(stack) / 100.0F));
                ExperienceOrbEntity.spawn((ServerWorld) world, user.getPos(), retrievedExperience);
                stack.set(MyComponents.XP_COMPONENT, XpAmountData.EMPTY);
            } else {
                // Check max value
                if (maxExperience - bookExperience < playerExperience) {
                    user.addExperience(bookExperience - maxExperience);
                    stack.set(MyComponents.XP_COMPONENT, new XpAmountData(maxExperience, maxLevel));
                } else {
                    int amount = bookExperience + playerExperience;
                    stack.set(MyComponents.XP_COMPONENT, new XpAmountData(amount, Utils.getLevelFromExperience(amount)));
                    user.addExperience(-playerExperience);
                }
            }
        }

        return new TypedActionResult<>(ActionResult.SUCCESS, user.getStackInHand(hand));
    }

    public static int getBookLevel(ItemStack stack) {
        return stack.getOrDefault(MyComponents.BOOK_COMPONENT, BookData.getDefault()).level();
    }

    public static int getMaxXpLevel(ItemStack stack) {
        return stack.getOrDefault(MyComponents.BOOK_COMPONENT, BookData.getDefault()).capacity();
    }

    public static int getMaxXpAmount(ItemStack stack) {
        return Utils.getExperienceToLevel(getMaxXpLevel(stack));
    }

    public static int getXpFromUsing(ItemStack stack) {
        return stack.getOrDefault(MyComponents.BOOK_COMPONENT, BookData.getDefault()).xpFromUsing();
    }

    public static int getXpFromBrewing(ItemStack stack) {
        return stack.getOrDefault(MyComponents.BOOK_COMPONENT, BookData.getDefault()).xpFromBrewing();
    }
}
