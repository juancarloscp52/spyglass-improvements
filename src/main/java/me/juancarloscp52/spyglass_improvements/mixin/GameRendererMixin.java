package me.juancarloscp52.spyglass_improvements.mixin;

import me.juancarloscp52.spyglass_improvements.client.SpyglassImprovementsClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Inject(method = "updateFovMultiplier", at = @At("RETURN"))
    private void applyZoom(CallbackInfo ci){
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if(player != null && player.isUsingSpyglass() && MinecraftClient.getInstance().options.getPerspective().isFirstPerson()){
            setFovMultiplier(SpyglassImprovementsClient.MULTIPLIER);
        }
    }

    @Accessor("fovMultiplier")
    public abstract void setFovMultiplier(float multiplier);
}
