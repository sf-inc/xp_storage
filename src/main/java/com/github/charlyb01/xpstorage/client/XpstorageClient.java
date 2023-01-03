package com.github.charlyb01.xpstorage.client;

import com.github.charlyb01.xpstorage.cardinal.MyComponents;
import com.github.charlyb01.xpstorage.config.ModConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

@Environment(EnvType.CLIENT)
public class XpstorageClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ItemTooltipCallback.EVENT.register((stack, context, lines) -> {
            if (stack.getItem() == Items.EXPERIENCE_BOTTLE) {
                int xpAmount = MyComponents.XP_COMPONENT.get(stack).getLevel();
                if (xpAmount > 0) {
                    lines.add(Text.translatable("item.xp_storage.experience_bottle.tooltip.amount", xpAmount)
                            .formatted(Formatting.GRAY));
                } else if (ModConfig.get().bottles.enableBrewing && ModConfig.get().cosmetic.bottleTooltip) {
                    lines.add(Text.translatable("item.xp_storage.experience_bottle.tooltip.classic")
                            .formatted(Formatting.GRAY));
                }
            }
        });
    }
}
