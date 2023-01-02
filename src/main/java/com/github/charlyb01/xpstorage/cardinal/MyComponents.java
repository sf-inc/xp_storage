package com.github.charlyb01.xpstorage.cardinal;

import com.github.charlyb01.xpstorage.Xpstorage;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.item.ItemComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.item.ItemComponentInitializer;
import net.minecraft.entity.projectile.thrown.ExperienceBottleEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public final class MyComponents implements EntityComponentInitializer, ItemComponentInitializer {
    public static final ComponentKey<ExperienceComponent> XP_COMPONENT =
            ComponentRegistry.getOrCreate(new Identifier("xp_storage", "xp_component"), ExperienceComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(ExperienceBottleEntity.class, XP_COMPONENT, entity -> new XpAmountComponent());
    }

    @Override
    public void registerItemComponentFactories(ItemComponentFactoryRegistry registry) {
        registry.register(Items.EXPERIENCE_BOTTLE, XP_COMPONENT, XpAmountItemComponent::new);
        registry.register(Xpstorage.xp_book1, XP_COMPONENT, XpAmountItemComponent::new);
        registry.register(Xpstorage.xp_book2, XP_COMPONENT, XpAmountItemComponent::new);
        registry.register(Xpstorage.xp_book3, XP_COMPONENT, XpAmountItemComponent::new);
    }
}
