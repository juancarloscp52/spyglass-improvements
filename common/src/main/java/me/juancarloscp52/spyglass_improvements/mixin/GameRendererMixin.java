package me.juancarloscp52.spyglass_improvements.mixin;

import me.juancarloscp52.spyglass_improvements.client.SpyglassImprovementsClient;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @ModifyConstant(method = "tickFov", constant = @Constant(floatValue = 0.1f))
    private float modifyMaximumFov(float constant){
        return SpyglassImprovementsClient.getInstance().settings. extraZoom ? .0001f : constant;
    }
}
