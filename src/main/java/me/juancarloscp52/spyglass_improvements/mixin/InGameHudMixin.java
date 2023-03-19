package me.juancarloscp52.spyglass_improvements.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import me.juancarloscp52.spyglass_improvements.client.SpyglassImprovementsClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class InGameHudMixin {

    // Set the spyglass overlay depending on the selected one.
    @Redirect(method = "renderSpyglassOverlay",at = @At(value = "INVOKE",target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V"))
    public void setTexture(int i, ResourceLocation identifier){
        switch (SpyglassImprovementsClient.getInstance().settings.overlay) {
            case 1 -> RenderSystem.setShaderTexture(i, new ResourceLocation("spyglass-improvements", "textures/spyglass_scope_clear.png"));
            case 2 -> RenderSystem.setShaderTexture(i, new ResourceLocation("spyglass-improvements", "textures/spyglass_scope_circle.png"));
            default -> RenderSystem.setShaderTexture(i, identifier);
        }

    }
    // toggle renderCrosshair depending on settings
    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    public void renderCrosshair(PoseStack poseStack, CallbackInfo ci){
        if(!SpyglassImprovementsClient.getInstance().settings.showCrossHair && Minecraft.getInstance().player.isScoping())
            ci.cancel();
    }

    // Toggle overlay.
    @Inject(method = "renderSpyglassOverlay", at = @At("HEAD"), cancellable = true)
    public void noRender(PoseStack poseStack, float f, CallbackInfo ci){ // No overlay.
        if(SpyglassImprovementsClient.getInstance().settings.overlay == 3)
            ci.cancel();
    }

}
