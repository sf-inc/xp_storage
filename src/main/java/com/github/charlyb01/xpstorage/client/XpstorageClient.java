package com.github.charlyb01.xpstorage.client;

import com.github.charlyb01.xpstorage.cardinal.MyComponents;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.item.Items;
import net.minecraft.text.TranslatableText;

@Environment(EnvType.CLIENT)
public class XpstorageClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ItemTooltipCallback.EVENT.register((stack, context, lines) -> {
            if (stack.getItem() == Items.EXPERIENCE_BOTTLE) {
                int xpAmount = MyComponents.XP_AMOUNT.get(stack).getValue();
                if (xpAmount > 0) {
                    lines.add(new TranslatableText("item.xp_storage.experience_bottle.tooltip.strange"));
                    lines.add(new TranslatableText("item.xp_storage.experience_bottle.tooltip.amount", xpAmount));
                } else {
                    lines.add(new TranslatableText("item.xp_storage.experience_bottle.tooltip.classic"));
                }
            }
        });
    }
}
