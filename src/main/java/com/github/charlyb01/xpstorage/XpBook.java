package com.github.charlyb01.xpstorage;

import com.github.charlyb01.xpstorage.config.ModConfig;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class XpBook extends Item {
    private final int maxLevel;
    private final int maxExperience;

    public XpBook(final int maxLevel, final int maxExperience) {
        super(new Item.Settings()
                .group(ItemGroup.MISC)
                .maxDamage(maxExperience));

        this.maxLevel = maxLevel;
        this.maxExperience = maxExperience;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("item.xp_storage.xp_books.tooltip", maxLevel));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.getDamage() == maxExperience;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = (user.getMainHandStack().getItem() instanceof XpBook) ? user.getMainHandStack() : user.getOffHandStack();
        int bookExperience = stack.getDamage();
        int playerExperience = Utils.getExperienceToLevel(user.experienceLevel);
        playerExperience += user.experienceProgress * Utils.getLevelExperience(user.experienceLevel);

        if (world.isClient) {
            // Play sound
            if ((user.isSneaking() && bookExperience > 0)
                    || (!user.isSneaking() && playerExperience > 0 && bookExperience < maxExperience)) {

                user.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
            }
        } else {
            // Empty / Fill
            if (user.isSneaking()) {
                int retrievedExperience = (int) (bookExperience * (ModConfig.get().XP_FROM_BOOK_USE / 100.0F));
                user.addExperience(retrievedExperience);
                stack.setDamage(0);
            } else {
                // Check max value
                if (maxExperience - bookExperience < playerExperience) {
                    user.addExperience(bookExperience - maxExperience);
                    stack.setDamage(maxExperience);
                } else {
                    stack.setDamage(bookExperience + playerExperience);
                    user.addExperience(-playerExperience);
                    user.experienceProgress = 0.0F;
                }
            }
        }

        return new TypedActionResult<>(ActionResult.SUCCESS, user.getStackInHand(hand));
    }
}
