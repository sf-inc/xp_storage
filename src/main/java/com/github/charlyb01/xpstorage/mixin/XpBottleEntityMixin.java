package com.github.charlyb01.xpstorage.mixin;

import com.github.charlyb01.xpstorage.cardinal.MyComponents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.projectile.thrown.ExperienceBottleEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExperienceBottleEntity.class)
public abstract class XpBottleEntityMixin extends ThrownItemEntity {
    public XpBottleEntityMixin(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "onCollision", at = @At("HEAD"), cancellable = true)
    private void changeXpAmount(HitResult hitResult, CallbackInfo ci) {
        int xpAmount = MyComponents.XP_AMOUNT.get(this).getValue();
        if (xpAmount > 0) {
            if (!this.world.isClient) {
            this.world.syncWorldEvent(2002, this.getBlockPos(), PotionUtil.getColor(Potions.WATER));
            int i = xpAmount;

            while(i > 0) {
            int j = ExperienceOrbEntity.roundToOrbSize(i);
            i -= j;
            this.world.spawnEntity(new ExperienceOrbEntity(this.world, this.getX(), this.getY(), this.getZ(), j));
            }

            this.remove();
            }
            ci.cancel();
        }
    }
}
