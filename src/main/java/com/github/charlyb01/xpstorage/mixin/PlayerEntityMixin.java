package com.github.charlyb01.xpstorage.mixin;

import com.github.charlyb01.xpstorage.Utils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Shadow public int experienceLevel;
    @Shadow public float experienceProgress;
    @Shadow public int totalExperience;

    @Shadow public abstract void addScore(int score);
    @Shadow public abstract void addExperienceLevels(int levels);
    @Shadow public abstract int getNextLevelExperience();

    @Inject(method = "addExperience", at = @At("HEAD"), cancellable = true)
    private void correctAddExperience(int experience, CallbackInfo ci) {
        this.addScore(experience);
        this.totalExperience = MathHelper.clamp(this.totalExperience + experience, 0, Integer.MAX_VALUE);

        int playerExperience = Utils.getPlayerExperience((PlayerEntity) (Object) this);
        playerExperience += experience;

        this.experienceLevel = 0;
        this.addExperienceLevels(Utils.getLevelFromExperience(playerExperience));
        int deltaExperience = playerExperience - Utils.getExperienceToLevel(this.experienceLevel);
        this.experienceProgress = deltaExperience / (float)this.getNextLevelExperience();

        ci.cancel();
    }
}
