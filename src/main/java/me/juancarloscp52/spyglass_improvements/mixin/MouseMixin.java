package me.juancarloscp52.spyglass_improvements.mixin;

import me.juancarloscp52.spyglass_improvements.client.SpyglassImprovementsClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.SmoothUtil;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public abstract class MouseMixin {

    @Shadow @Final private MinecraftClient client;
    @Shadow private double cursorDeltaX;
    @Shadow private double cursorDeltaY;
    @Shadow @Final private SmoothUtil cursorXSmoother;
    @Shadow @Final private SmoothUtil cursorYSmoother;

    @Inject(method = "onMouseScroll",at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;scrollInHotbar(D)V"),cancellable = true)
    private void onScroll(long window, double horizontal, double vertical, CallbackInfo ci){
        float d = (float) ((MinecraftClient.getInstance().options.discreteMouseScroll ? Math.signum(vertical) : vertical) * MinecraftClient.getInstance().options.mouseWheelSensitivity);
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if(player != null && player.isUsingSpyglass() && MinecraftClient.getInstance().options.getPerspective().isFirstPerson()){
            SpyglassImprovementsClient.MULTIPLIER = MathHelper.clamp(SpyglassImprovementsClient.MULTIPLIER-(d* SpyglassImprovementsClient.getInstance().settings.multiplierDelta), .1f,.8f);
            player.playSound(SoundEvents.ITEM_SPYGLASS_STOP_USING, 1.0f, 1.0f+(1*(1- SpyglassImprovementsClient.MULTIPLIER)*(1- SpyglassImprovementsClient.MULTIPLIER)));
            ci.cancel();
        }
    }

    @Inject(method = "updateMouse",at = @At("HEAD"),cancellable = true)
    public void onMouseUpdate(CallbackInfo ci){
        double displacementX,displacementY;
        if (null != client.player && client.options.getPerspective().isFirstPerson() && client.player.isUsingSpyglass()){
            cursorXSmoother.clear();
            cursorYSmoother.clear();
            double sensitivity = client.options.mouseSensitivity * .6 + .2;
            double baseSensitivity = (sensitivity * sensitivity * sensitivity) * 8.0;
            double spyglassSensitivity = baseSensitivity * SpyglassImprovementsClient.MULTIPLIER;
            displacementX = cursorDeltaX * spyglassSensitivity;
            displacementY = cursorDeltaY * spyglassSensitivity;
            cursorDeltaX = .0;
            cursorDeltaY = .0 ;
            int mouseDirection = client.options.invertYMouse? -1:1;
            client.getTutorialManager().onUpdateMouse(displacementX, displacementY);
            if (client.player != null) {
                client.player.changeLookDirection(displacementX, displacementY * mouseDirection);
            }
            ci.cancel();
        }

    }
}
