package com.github.charlyb01.xpstorage.client;

import com.github.charlyb01.xpstorage.component.BookData;
import com.github.charlyb01.xpstorage.component.MyComponents;
import com.github.charlyb01.xpstorage.component.XpAmountData;
import com.github.charlyb01.xpstorage.config.ExperienceTooltip;
import com.github.charlyb01.xpstorage.config.ModConfig;
import com.github.charlyb01.xpstorage.item.ItemRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

@Environment(EnvType.CLIENT)
public class XpStorageClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> {
            if (stack.isOf(Items.EXPERIENCE_BOTTLE)) {
                int level = stack.getOrDefault(MyComponents.XP_COMPONENT, XpAmountData.EMPTY).level();
                int amount = stack.getOrDefault(MyComponents.XP_COMPONENT, XpAmountData.EMPTY).amount();
                if (amount <= 0) return;

                lines.add(Text.translatable("item.xp_storage.tooltip")
                            .formatted(Formatting.GRAY));
                if (!ModConfig.get().cosmetic.bottleTooltip.equals(ExperienceTooltip.POINT)) {
                    lines.add(Text.translatable("item.xp_storage.xp_bottle.tooltip.level", level)
                            .formatted(Formatting.BLUE));
                }
                if (!ModConfig.get().cosmetic.bottleTooltip.equals(ExperienceTooltip.LEVEL)) {
                    lines.add(Text.translatable("item.xp_storage.xp_bottle.tooltip.point", amount)
                            .formatted(Formatting.BLUE));
                }
            } else if (isDeprecated(stack)) {
                lines.add(Text.translatable("item.xp_storage.tooltip.deprecated")
                        .formatted(Formatting.RED));
                lines.add(Text.translatable("item.xp_storage.tooltip.deprecated_how")
                        .formatted(Formatting.DARK_RED, Formatting.ITALIC));
            }
        });
    }

    private static boolean isDeprecated(ItemStack stack) {
        if (!stack.isOf(ItemRegistry.XP_BOOK)
                && !stack.isOf(ItemRegistry.XP_BOOK2)
                && !stack.isOf(ItemRegistry.XP_BOOK3)) {
            return false;
        }

        XpAmountData xpAmountData = stack.get(MyComponents.XP_COMPONENT);
        if (xpAmountData == null) return false;

        BookData bookData = stack.get(MyComponents.BOOK_COMPONENT);
        return  bookData == null || bookData.capacity() < xpAmountData.level();
    }
}
