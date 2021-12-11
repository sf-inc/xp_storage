package com.github.charlyb01.xpstorage.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@Config(name = "xp_storage")
public class ModConfig extends PartitioningSerializer.GlobalData {
    @ConfigEntry.Category("books")
    @ConfigEntry.Gui.TransitiveObject
    public BookConfig books = new BookConfig();

    @ConfigEntry.Category("bottles")
    @ConfigEntry.Gui.TransitiveObject
    public BottleConfig bottles = new BottleConfig();

    public static ModConfig get() {
        return AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }
}
