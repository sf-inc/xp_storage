package com.github.charlyb01.xpstorage.item;

import com.github.charlyb01.xpstorage.XpBook;
import com.github.charlyb01.xpstorage.XpStorage;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ItemRegistry {
    public static final Item CRYSTALLIZED_LAPIS = new Item(new Item.Settings());
    public static final XpBook XP_BOOK = new XpBook();

    public static void init() {
        Registry.register(Registries.ITEM, XpStorage.id("crystallized_lapis"), CRYSTALLIZED_LAPIS);
        Registry.register(Registries.ITEM, XpStorage.id("xp_book"), XP_BOOK);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries ->
                entries.addAfter(Items.LAPIS_LAZULI, CRYSTALLIZED_LAPIS));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(XP_BOOK));
    }
}
