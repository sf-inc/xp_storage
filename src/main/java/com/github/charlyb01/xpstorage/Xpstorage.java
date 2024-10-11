package com.github.charlyb01.xpstorage;

import com.github.charlyb01.xpstorage.config.ModConfig;
import com.github.charlyb01.xpstorage.item.ItemRegistry;
import com.github.charlyb01.xpstorage.recipe.RecipeRegistry;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class Xpstorage implements ModInitializer {
    public static final String MOD_ID = "xp_storage";
    public static boolean areConfigsInit = false;

    @Override
    public void onInitialize() {
        if (!areConfigsInit) {
            AutoConfig.register(ModConfig.class, PartitioningSerializer.wrap(GsonConfigSerializer::new));
            areConfigsInit = true;
        }

        ItemRegistry.init();
        RecipeRegistry.init();
    }

    public static Identifier id(final String path) {
        return Identifier.of(MOD_ID, path);
    }
}
