package me.juancarloscp52.spyglass_improvements.mixin;

import me.juancarloscp52.spyglass_improvements.client.SpyglassImprovementsClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.SmoothDouble;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public abstract class MouseMixin {

    @Shadow @Final private Minecraft minecraft;

    @Shadow @Final private SmoothDouble smoothTurnX;

    @Shadow @Final private SmoothDouble smoothTurnY;

    @Shadow private double accumulatedDX;

    @Shadow private double accumulatedDY;

    @Inject(method = "onScroll",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;swapPaint(D)V"),cancellable = true)
    private void onScroll(long window, double horizontal, double vertical, CallbackInfo ci){
        float d = (float) ((Minecraft.getInstance().options.discreteMouseScroll ? Math.signum(vertical) : vertical) * Minecraft.getInstance().options.mouseWheelSensitivity);
        LocalPlayer player = Minecraft.getInstance().player;
        if(player != null && player.isScoping() && Minecraft.getInstance().options.getCameraType().isFirstPerson()){
            SpyglassImprovementsClient.MULTIPLIER = Mth.clamp(SpyglassImprovementsClient.MULTIPLIER-(d* SpyglassImprovementsClient.getInstance().settings.multiplierDelta), .1f,.8f);
            player.playSound(SoundEvents.SPYGLASS_STOP_USING, 1.0f, 1.0f+(1*(1- SpyglassImprovementsClient.MULTIPLIER)*(1- SpyglassImprovementsClient.MULTIPLIER)));
            ci.cancel();
        }
    }

    @Inject(method = "turnPlayer",at = @At("HEAD"),cancellable = true)
    public void onMouseUpdate(CallbackInfo ci){
        double displacementX,displacementY;
        if (null != minecraft.player && minecraft.options.getCameraType().isFirstPerson() && minecraft.player.isScoping()){
            smoothTurnX.reset();
            smoothTurnY.reset();
            double sensitivity = minecraft.options.sensitivity * .6 + .2;
            double baseSensitivity = (sensitivity * sensitivity * sensitivity) * 8.0;
            double spyglassSensitivity = baseSensitivity * SpyglassImprovementsClient.MULTIPLIER;
            displacementX = accumulatedDX * spyglassSensitivity;
            displacementY = accumulatedDY * spyglassSensitivity;
            accumulatedDX = .0;
            accumulatedDY = .0 ;
            int mouseDirection = minecraft.options.invertYMouse? -1:1;
            minecraft.getTutorial().onMouse(displacementX, displacementY);
            if (minecraft.player != null) {
                minecraft.player.turn(displacementX, displacementY * mouseDirection);
            }
            ci.cancel();
        }

    }
}
