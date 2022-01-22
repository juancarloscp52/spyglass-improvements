package me.juancarloscp52.enhancedspyglass.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import me.juancarloscp52.enhancedspyglass.client.EnhancedSpyglassClient;
import me.juancarloscp52.enhancedspyglass.client.Settings;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Redirect(method = "renderSpyglassOverlay",at = @At(value = "INVOKE",target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V"))
    public void setTexture(int i, Identifier identifier){
        if(EnhancedSpyglassClient.getInstance().settings.overlay == 1){ //Clear overlay
            RenderSystem.setShaderTexture(i, new Identifier("enhanced-spyglass", "textures/spyglass_scope_clear.png"));
        }else{
            RenderSystem.setShaderTexture(i, identifier);
        }
    }
    @Inject(method = "renderSpyglassOverlay", at = @At("HEAD"), cancellable = true)
    public void noRender(float scale, CallbackInfo ci){ // No overlay.
        if(EnhancedSpyglassClient.getInstance().settings.overlay == 2)
            ci.cancel();
    }

}
