package com.github.charlyb01.xpstorage;

import com.github.charlyb01.xpstorage.config.ModConfig;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Xpstorage implements ModInitializer {

    public static XpBookI xp_book;
    public static XpBookII xp_book2;
    public static XpBookIII xp_book3;

    @Override
    public void onInitialize() {
        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);

        xp_book = new XpBookI();
        xp_book2 = new XpBookII();
        xp_book3 = new XpBookIII();

        Registry.register(Registry.ITEM, new Identifier("xp_storage", "xp_book"), xp_book);
        Registry.register(Registry.ITEM, new Identifier("xp_storage", "xp_book2"), xp_book2);
        Registry.register(Registry.ITEM, new Identifier("xp_storage", "xp_book3"), xp_book3);
    }
}
