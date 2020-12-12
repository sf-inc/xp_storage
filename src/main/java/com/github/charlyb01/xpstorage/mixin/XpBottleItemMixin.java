package com.github.charlyb01.xpstorage.mixin;

import com.github.charlyb01.xpstorage.cardinal.MyComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ExperienceBottleEntity;
import net.minecraft.item.ExperienceBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ExperienceBottleItem.class)
public class XpBottleItemMixin extends Item {
    public XpBottleItemMixin(Settings settings) {
        super(settings);
    }

    @ModifyVariable(method = "use", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"))
    private ExperienceBottleEntity setXpAmountEntity(ExperienceBottleEntity experienceBottleEntity, World world,
                                               PlayerEntity user, Hand hand) {
        ItemStack stack = (user.getMainHandStack().getItem() instanceof ExperienceBottleItem)
                ?user.getMainHandStack():user.getOffHandStack();
        int xpAmount = MyComponents.XP_AMOUNT.get(stack).getValue();
        if (xpAmount > 0) {
            MyComponents.XP_AMOUNT.get(experienceBottleEntity).setValue(xpAmount);
        }
        return experienceBottleEntity;
    }
}
