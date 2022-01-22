package me.juancarloscp52.spyglass_improvements.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import me.juancarloscp52.spyglass_improvements.client.SpyglassImprovementsClient;
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
        if(SpyglassImprovementsClient.getInstance().settings.overlay == 1){ //Clear overlay
            RenderSystem.setShaderTexture(i, new Identifier("spyglass-improvements", "textures/spyglass_scope_clear.png"));
        }else{
            RenderSystem.setShaderTexture(i, identifier);
        }
    }
    @Inject(method = "renderSpyglassOverlay", at = @At("HEAD"), cancellable = true)
    public void noRender(float scale, CallbackInfo ci){ // No overlay.
        if(SpyglassImprovementsClient.getInstance().settings.overlay == 2)
            ci.cancel();
    }

}
