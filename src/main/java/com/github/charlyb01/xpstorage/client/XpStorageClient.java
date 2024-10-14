package com.github.charlyb01.xpstorage.client;

import com.github.charlyb01.xpstorage.component.MyComponents;
import com.github.charlyb01.xpstorage.component.XpAmountData;
import com.github.charlyb01.xpstorage.config.ExperienceTooltip;
import com.github.charlyb01.xpstorage.config.ModConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
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
            }
        });
    }
}
