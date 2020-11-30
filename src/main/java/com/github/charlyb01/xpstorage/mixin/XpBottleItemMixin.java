package com.github.charlyb01.xpstorage.mixin;

import com.github.charlyb01.xpstorage.imixin.XpBottleEntityIMixin;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ExperienceBottleEntity;
import net.minecraft.item.ExperienceBottleItem;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ExperienceBottleItem.class)
public class XpBottleItemMixin {
    @Unique
    int xpAmount = 0;

    @ModifyVariable(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"))
    private ExperienceBottleEntity setXpAmount(ExperienceBottleEntity experienceBottleEntity, World world, PlayerEntity user, Hand hand) {
        if (xpAmount > 0)
            ((XpBottleEntityIMixin)experienceBottleEntity).setAmount(xpAmount);
        return experienceBottleEntity;
    }
}
