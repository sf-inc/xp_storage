package com.github.charlyb01.xpstorage;

import com.github.charlyb01.xpstorage.config.ModConfig;
import com.github.charlyb01.xpstorage.recipe.RecipeRegistry;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Xpstorage implements ModInitializer {
    public static final String MOD_ID = "xp_storage";
    public static boolean areConfigsInit = false;

    public static final Item CRYSTALLIZED_LAPIS = new Item(new Item.Settings());
    public static final XpBook xp_book = new XpBook();

    @Override
    public void onInitialize() {
        if (!areConfigsInit) {
            AutoConfig.register(ModConfig.class, PartitioningSerializer.wrap(GsonConfigSerializer::new));
            areConfigsInit = true;
        }

        RecipeRegistry.init();

        Registry.register(Registries.ITEM, id("crystallized_lapis"), CRYSTALLIZED_LAPIS);
        Registry.register(Registries.ITEM, id("xp_book"), xp_book);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.addAfter(Items.LAPIS_LAZULI, CRYSTALLIZED_LAPIS);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(xp_book));
    }

    public static Identifier id(final String path) {
        return Identifier.of(MOD_ID, path);
    }
}
