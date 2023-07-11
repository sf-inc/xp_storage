package com.github.charlyb01.xpstorage;

import com.github.charlyb01.xpstorage.cardinal.MyComponents;
import com.github.charlyb01.xpstorage.config.ModConfig;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class XpBook extends Item {
    private final int maxLevel;
    private final int maxExperience;
    private final int xpFromUsing;
    private final int colorBar;

    public XpBook(final int maxLevel, final int xpFromUsing, final int colorBar, final boolean isFireproof, final Rarity rarity) {
        super(isFireproof ? new Item.Settings().maxCount(1).rarity(rarity).fireproof()
                : new Item.Settings().maxCount(1).rarity(rarity));

        this.maxLevel = maxLevel;
        this.maxExperience = Utils.getExperienceToLevel(maxLevel);
        this.xpFromUsing = xpFromUsing;
        this.colorBar = colorBar;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        final int bookLevel = MyComponents.XP_COMPONENT.get(stack).getLevel();
        tooltip.add(Text.translatable("item.xp_storage.xp_books.tooltip", bookLevel, this.maxLevel)
                .formatted(Formatting.GRAY));

        if (ModConfig.get().cosmetic.bookTooltip) {
            final int bookExperience = MyComponents.XP_COMPONENT.get(stack).getAmount();
            tooltip.add(Text.translatable("item.xp_storage.xp_books.advanced_tooltip", bookExperience, this.maxExperience)
                    .formatted(Formatting.GRAY).formatted(Formatting.ITALIC));
        }
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        final int bookExperience = MyComponents.XP_COMPONENT.get(stack).getAmount();
        return (bookExperience / (float) this.maxExperience) * 100 >= ModConfig.get().cosmetic.glint;
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return this.colorBar;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        final int bookExperience = MyComponents.XP_COMPONENT.get(stack).getAmount();
        return Math.round((bookExperience * 13) / (float) this.maxExperience);
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return MyComponents.XP_COMPONENT.get(stack).getAmount() > 0;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = (user.getMainHandStack().getItem() instanceof XpBook) ? user.getMainHandStack() : user.getOffHandStack();
        final int bookExperience = MyComponents.XP_COMPONENT.get(stack).getAmount();
        final int playerExperience = user.totalExperience;

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
                MyComponents.XP_COMPONENT.get(stack).setAmount(0);
            } else {
                // Check max value
                if (this.maxExperience - bookExperience < playerExperience) {
                    user.addExperience(bookExperience - this.maxExperience);
                    MyComponents.XP_COMPONENT.get(stack).setAmount(this.maxExperience);
                } else {
                    MyComponents.XP_COMPONENT.get(stack).setAmount(bookExperience + playerExperience);
                    user.addExperience(-playerExperience);
                }
            }
        }

        return new TypedActionResult<>(ActionResult.SUCCESS, user.getStackInHand(hand));
    }
}
