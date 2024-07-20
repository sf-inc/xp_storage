package com.github.charlyb01.xpstorage.component;

import com.github.charlyb01.xpstorage.Xpstorage;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.projectile.thrown.ExperienceBottleEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.item.ItemComponentInitializer;
import org.ladysnake.cca.api.v3.item.ItemComponentMigrationRegistry;

public final class MyComponents implements EntityComponentInitializer, ItemComponentInitializer {
    private static final Identifier XP_COMPONENT_ID = Identifier.of(Xpstorage.MOD_ID, "xp_component");
    public static final ComponentKey<ExperienceComponent> XP_COMPONENT_CC =
            ComponentRegistry.getOrCreate(XP_COMPONENT_ID, ExperienceComponent.class);

    public static final ComponentType<XpAmountData> XP_COMPONENT = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            XP_COMPONENT_ID,
            ComponentType.<XpAmountData>builder().codec(XpAmountData.CODEC).build()
    );

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(ExperienceBottleEntity.class, XP_COMPONENT_CC, entity -> new XpAmountComponent());
    }

    @Override
    public void registerItemComponentMigrations(ItemComponentMigrationRegistry registry) {
        registry.registerMigration(XP_COMPONENT_ID, XP_COMPONENT);
    }
}
