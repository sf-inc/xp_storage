package com.github.charlyb01.xpstorage.mixin;

import com.github.charlyb01.xpstorage.component.MyComponents;
import com.github.charlyb01.xpstorage.component.XpAmountData;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ExperienceBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ExperienceBottleItem.class)
public class XpBottleItemMixin extends Item {
    public XpBottleItemMixin(Settings settings) {
        super(settings);
    }

    @WrapOperation(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/ProjectileEntity;spawnWithVelocity(Lnet/minecraft/entity/projectile/ProjectileEntity$ProjectileCreator;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/LivingEntity;FFF)Lnet/minecraft/entity/projectile/ProjectileEntity;"))
    private <T extends ProjectileEntity> T setExperienceToEntity(ProjectileEntity.ProjectileCreator<T> creator, ServerWorld world,
                                                                 ItemStack projectileStack, LivingEntity shooter,
                                                                 float roll, float power, float divergence,
                                                                 Operation<T> original) {
        T experienceBottleEntity = original.call(creator, world, projectileStack, shooter, roll, power, divergence);
        final int level = projectileStack.getOrDefault(MyComponents.XP_COMPONENT, XpAmountData.EMPTY).level();
        if (level > 0) {
            MyComponents.XP_COMPONENT_CC.get(experienceBottleEntity).setLevel(level);
        }
        return experienceBottleEntity;
    }
}
