package com.github.charlyb01.xpstorage.mixin;

import com.github.charlyb01.xpstorage.component.MyComponents;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.thrown.ExperienceBottleEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ExperienceBottleEntity.class)
public abstract class XpBottleEntityMixin extends ThrownItemEntity {
    public XpBottleEntityMixin(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    @WrapOperation(method = "onCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ExperienceOrbEntity;spawn(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/Vec3d;I)V"))
    private void changeXpAmount(ServerWorld world, Vec3d pos, int randomAmount, Operation<Void> original) {
        final int xpAmount = MyComponents.XP_COMPONENT_CC.get(this).getAmount();
        original.call(world, pos, xpAmount > 0 ? xpAmount : randomAmount);
    }
}
