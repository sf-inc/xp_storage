package com.github.charlyb01.xpstorage;

import com.github.charlyb01.xpstorage.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Xpstorage implements ModInitializer {

    public static XpBook xp_book1;
    public static XpBook xp_book2;
    public static XpBook xp_book3;

    @Override
    public void onInitialize() {
        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);

        xp_book1 = new XpBook(ModConfig.get().MAX_LEVEL_I, Utils.getExperienceToLevel(ModConfig.get().MAX_LEVEL_I));
        xp_book2 = new XpBook(ModConfig.get().MAX_LEVEL_II, Utils.getExperienceToLevel(ModConfig.get().MAX_LEVEL_II));
        xp_book3 = new XpBook(ModConfig.get().MAX_LEVEL_III, Utils.getExperienceToLevel(ModConfig.get().MAX_LEVEL_III));

        Registry.register(Registry.ITEM, new Identifier("xp_storage", "xp_book"), xp_book1);
        Registry.register(Registry.ITEM, new Identifier("xp_storage", "xp_book2"), xp_book2);
        Registry.register(Registry.ITEM, new Identifier("xp_storage", "xp_book3"), xp_book3);
    }
}
