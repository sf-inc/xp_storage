package com.github.charlyb01.xpstorage.item;

import com.github.charlyb01.xpstorage.XpStorage;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.List;

public class ItemRegistry {
    private static final Formatting TITLE_FORMATTING = Formatting.GRAY;
    private static final Formatting DESCRIPTION_FORMATTING = Formatting.BLUE;
    private static final Text XP_BOOK_UPGRADE_APPLIES_TO_TEXT = Text.translatable(
                    Util.createTranslationKey("item", XpStorage.id("smithing_template.xp_book_upgrade.applies_to"))
            )
            .formatted(DESCRIPTION_FORMATTING);
    private static final Text XP_BOOK_UPGRADE_INGREDIENTS_TEXT = Text.translatable(
                    Util.createTranslationKey("item", XpStorage.id("smithing_template.xp_book_upgrade.ingredients"))
            )
            .formatted(DESCRIPTION_FORMATTING);
    private static final Text XP_BOOK_UPGRADE_TEXT = Text.translatable(Util.createTranslationKey("upgrade", XpStorage.id("xp_book_upgrade")))
            .formatted(TITLE_FORMATTING);
    private static final Text XP_BOOK_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT = Text.translatable(
            Util.createTranslationKey("item", XpStorage.id("smithing_template.xp_book_upgrade.base_slot_description"))
    );
    private static final Text XP_BOOK_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT = Text.translatable(
            Util.createTranslationKey("item", XpStorage.id("smithing_template.xp_book_upgrade.additions_slot_description"))
    );

    public static final Item CRYSTALLIZED_LAPIS = new Item(new Item.Settings());
    public static final Item XP_BOOK_UPGRADE = new SmithingTemplateItem(
            XP_BOOK_UPGRADE_APPLIES_TO_TEXT,
            XP_BOOK_UPGRADE_INGREDIENTS_TEXT,
            XP_BOOK_UPGRADE_TEXT,
            XP_BOOK_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT,
            XP_BOOK_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT,
            List.of(XpStorage.id(("item/empty_slot_book"))),
            List.of(Identifier.ofVanilla("item/empty_slot_diamond"),
                    Identifier.ofVanilla("item/empty_slot_ingot"))
    );
    public static final XpBook XP_BOOK = new XpBook();
    public static final Item XP_BOOK2 = new Item(new Item.Settings());
    public static final Item XP_BOOK3 = new Item(new Item.Settings());

    public static void init() {
        Registry.register(Registries.ITEM, XpStorage.id("crystallized_lapis"), CRYSTALLIZED_LAPIS);
        Registry.register(Registries.ITEM, XpStorage.id("xp_book_upgrade_smithing_template"), XP_BOOK_UPGRADE);
        Registry.register(Registries.ITEM, XpStorage.id("xp_book"), XP_BOOK);
        Registry.register(Registries.ITEM, XpStorage.id("xp_book2"), XP_BOOK2);
        Registry.register(Registries.ITEM, XpStorage.id("xp_book3"), XP_BOOK3);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.addAfter(Items.LAPIS_LAZULI, CRYSTALLIZED_LAPIS);
            entries.addAfter(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, XP_BOOK_UPGRADE);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(XP_BOOK));
    }
}
