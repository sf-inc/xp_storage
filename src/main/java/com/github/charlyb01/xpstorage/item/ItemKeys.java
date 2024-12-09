package com.github.charlyb01.xpstorage.item;

import com.github.charlyb01.xpstorage.XpStorage;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public interface ItemKeys {
    RegistryKey<Item> CRYSTALLIZED_LAPIS_KEY = register("crystallized_lapis");
    RegistryKey<Item> XP_BOOK_UPGRADE_KEY = register("xp_book_upgrade_smithing_template");
    RegistryKey<Item> XP_BOOK_KEY = register("xp_book");
    RegistryKey<Item> XP_BOOK2_KEY = register("xp_book2");
    RegistryKey<Item> XP_BOOK3_KEY = register("xp_book3");

    private static RegistryKey<Item> register(String path) {
        return RegistryKey.of(RegistryKeys.ITEM, XpStorage.id(path));
    }
}
