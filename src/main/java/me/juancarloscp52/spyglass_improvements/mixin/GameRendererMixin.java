package me.juancarloscp52.spyglass_improvements.mixin;

import me.juancarloscp52.spyglass_improvements.client.SpyglassImprovementsClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Shadow private float fovMultiplier;
    private float multiplier = SpyglassImprovementsClient.MULTIPLIER;

    @Inject(method = "updateFovMultiplier", at = @At("RETURN"))
    private void applyZoom(CallbackInfo ci){
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if(player != null && player.isUsingSpyglass() && MinecraftClient.getInstance().options.getPerspective().isFirstPerson()){
            multiplier = MathHelper.clampedLerp(
                    multiplier,
                    SpyglassImprovementsClient.MULTIPLIER,
                    0.3f);
            fovMultiplier = multiplier;
        }
    }
}
