package me.juancarloscp52.spyglass_improvements.mixin;

import com.mojang.authlib.GameProfile;
import me.juancarloscp52.spyglass_improvements.client.SpyglassImprovementsClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerEntityMixin extends Player {


    public AbstractClientPlayerEntityMixin(Level level, BlockPos blockPos, float f, GameProfile gameProfile) {
        super(level, blockPos, f, gameProfile);
    }

    // Modify scoping FOV to the spyglass custom zoom level.
    @Inject(method = "getFieldOfViewModifier", at = @At("RETURN"), cancellable = true)
    public void fovMultiplier(CallbackInfoReturnable<Float> cir){
        if(Minecraft.getInstance().options.getCameraType().isFirstPerson() && isScoping())
            cir.setReturnValue(SpyglassImprovementsClient.MULTIPLIER);
    }

}
