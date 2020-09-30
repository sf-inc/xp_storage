package com.github.charlyb01.xpstorage;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Xpstorage implements ModInitializer {

    public static final XpBookI xp_book = new XpBookI();
    public static final XpBookII xp_book2 = new XpBookII();
    public static final XpBookIII xp_book3 = new XpBookIII();

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier("xp_storage", "xp_book"), xp_book);
        Registry.register(Registry.ITEM, new Identifier("xp_storage", "xp_book2"), xp_book2);
        Registry.register(Registry.ITEM, new Identifier("xp_storage", "xp_book3"), xp_book3);
    }
}
