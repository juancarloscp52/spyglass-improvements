package me.juancarloscp52.spyglass_improvements.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import me.juancarloscp52.spyglass_improvements.client.SpyglassImprovementsClient;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class InGameHudMixin {

    // Set the spyglass overlay depending on the selected one.
    @ModifyArg(method = "renderSpyglassOverlay",at = @At(value = "INVOKE",target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V"),index = 1)
    public ResourceLocation setTexture(ResourceLocation resourceLocation){
        return switch (SpyglassImprovementsClient.getInstance().settings.overlay) {
            case 1 -> ResourceLocation.fromNamespaceAndPath("spyglass_improvements", "textures/spyglass_scope_clear.png");
            case 2 -> ResourceLocation.fromNamespaceAndPath("spyglass_improvements", "textures/spyglass_scope_circle.png");
            default -> resourceLocation;
        };
    }
    // toggle renderCrosshair depending on settings
    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    public void renderCrosshair(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci){
        if(!SpyglassImprovementsClient.getInstance().settings.showCrossHair && Minecraft.getInstance().player!=null && Minecraft.getInstance().player.isScoping())
            ci.cancel();
    }

    // Toggle overlay.
    @WrapWithCondition(method = "renderCameraOverlays(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/DeltaTracker;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderSpyglassOverlay(Lnet/minecraft/client/gui/GuiGraphics;F)V"))
    public boolean DoNotRenderIfNoneOverlay(Gui instance, GuiGraphics guiGraphics, float f){ // No overlay.
        return SpyglassImprovementsClient.getInstance().settings.overlay != 3;
    }

}
