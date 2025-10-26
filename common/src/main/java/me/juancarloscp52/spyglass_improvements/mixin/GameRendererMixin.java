package me.juancarloscp52.spyglass_improvements.mixin;

import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @ModifyConstant(method = "tickFov", constant = @Constant(floatValue = 0.1f))
    private float modifyMaximumFov(float constant){
        return 0.0001f;
    }
}