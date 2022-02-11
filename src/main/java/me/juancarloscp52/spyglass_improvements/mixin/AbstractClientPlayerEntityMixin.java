package me.juancarloscp52.spyglass_improvements.mixin;

import com.mojang.authlib.GameProfile;
import me.juancarloscp52.spyglass_improvements.client.SpyglassImprovementsClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin extends PlayerEntity {


    public AbstractClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }

    @Inject(method = "getFovMultiplier", at = @At("RETURN"), cancellable = true)
    public void fovMultiplier(CallbackInfoReturnable<Float> cir){
        if(MinecraftClient.getInstance().options.getPerspective().isFirstPerson() && isUsingSpyglass())
            cir.setReturnValue(SpyglassImprovementsClient.MULTIPLIER);
    }

}
