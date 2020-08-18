package com.github.charlyb01.xpstorage;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Xpstorage implements ModInitializer {

    public static final XpItem xp_item = new XpItem();

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier("xp_storage", "xp_book"), xp_item);
    }
}
