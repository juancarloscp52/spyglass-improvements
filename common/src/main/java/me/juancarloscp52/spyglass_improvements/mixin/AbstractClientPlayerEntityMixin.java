package me.juancarloscp52.spyglass_improvements.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.authlib.GameProfile;
import me.juancarloscp52.spyglass_improvements.client.SpyglassImprovementsClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerEntityMixin extends Player {


    public AbstractClientPlayerEntityMixin(Level level, BlockPos blockPos, float f, GameProfile gameProfile) {
        super(level, blockPos, f, gameProfile);
    }

    // Modify scoping FOV to the spyglass custom zoom level.
    @ModifyReturnValue(method = "getFieldOfViewModifier", at = @At("RETURN"))
    public float fovMultiplier(float original){
        if(Minecraft.getInstance().options.getCameraType().isFirstPerson() && isScoping()){
            return SpyglassImprovementsClient.MULTIPLIER;
        }
        return original;
    }

}
