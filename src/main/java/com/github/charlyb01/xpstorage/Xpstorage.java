package com.github.charlyb01.xpstorage;

import com.github.charlyb01.xpstorage.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Xpstorage implements ModInitializer {

    public static XpBook xp_book1;
    public static XpBook xp_book2;
    public static XpBook xp_book3;

    @Override
    public void onInitialize() {
        AutoConfig.register(ModConfig.class, PartitioningSerializer.wrap(GsonConfigSerializer::new));

        xp_book1 = new XpBook(ModConfig.get().books.book1.maxLevel, Utils.getExperienceToLevel(ModConfig.get().books.book1.maxLevel));
        xp_book2 = new XpBook(ModConfig.get().books.book2.maxLevel, Utils.getExperienceToLevel(ModConfig.get().books.book2.maxLevel));
        xp_book3 = new XpBook(ModConfig.get().books.book3.maxLevel, Utils.getExperienceToLevel(ModConfig.get().books.book3.maxLevel));

        Registry.register(Registry.ITEM, new Identifier("xp_storage", "xp_book"), xp_book1);
        if (ModConfig.get().books.nbBooks > 1)
            Registry.register(Registry.ITEM, new Identifier("xp_storage", "xp_book2"), xp_book2);
        if (ModConfig.get().books.nbBooks > 2)
            Registry.register(Registry.ITEM, new Identifier("xp_storage", "xp_book3"), xp_book3);
    }
}
