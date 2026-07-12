package me.juancarloscp52.spyglass_improvements.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import me.juancarloscp52.spyglass_improvements.client.SpyglassImprovementsClient;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Hud.class)
public class InGameHudMixin {

    // Set the spyglass overlay depending on the selected one.
    @ModifyArg(method = "extractSpyglassOverlay",at = @At(value = "INVOKE",target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIII)V"),index = 1)
    public Identifier setTexture(Identifier identifier){
        return switch (SpyglassImprovementsClient.getInstance().settings.overlay) {
            case 1 -> Identifier.fromNamespaceAndPath("spyglass_improvements", "textures/spyglass_scope_clear.png");
            case 2 -> Identifier.fromNamespaceAndPath("spyglass_improvements", "textures/spyglass_scope_circle.png");
            default -> identifier;
        };
    }
    // toggle renderCrosshair depending on settings
    @Inject(method = "extractCrosshair", at = @At("HEAD"), cancellable = true)
    public void renderCrosshair(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci){
        if(!SpyglassImprovementsClient.getInstance().settings.showCrossHair && Minecraft.getInstance().player!=null && Minecraft.getInstance().player.isScoping())
            ci.cancel();
    }

    // Toggle overlay.
    @WrapWithCondition(method = "extractCameraOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Hud;extractSpyglassOverlay(Lnet/minecraft/client/gui/GuiGraphicsExtractor;F)V"))
    public boolean DoNotRenderIfNoneOverlay(Hud instance, GuiGraphicsExtractor graphics, float scale){ // No overlay.
        return SpyglassImprovementsClient.getInstance().settings.overlay != 3;
    }

}
