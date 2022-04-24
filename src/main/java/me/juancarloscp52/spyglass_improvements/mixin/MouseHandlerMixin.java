package me.juancarloscp52.spyglass_improvements.mixin;

import com.mojang.blaze3d.Blaze3D;
import me.juancarloscp52.spyglass_improvements.client.SpyglassImprovementsClient;
import me.juancarloscp52.spyglass_improvements.config.SpyglassImprovementsConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.util.Mth;
import net.minecraft.util.SmoothDouble;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {

    @Shadow private double accumulatedDX;
    @Shadow private double accumulatedDY;

    /*// Modify X sensitivity only when using the spyglass.
    @ModifyVariable(method = "turnPlayer",slice = @Slice(
            from = @At(value = "INVOKE", target = "Lnet/minecraft/util/SmoothDouble;reset()V", ordinal = 1),
            to = @At(value = "INVOKE", target = "Lnet/minecraft/util/SmoothDouble;reset()V", ordinal = 2)
    ), at = @At(value = "STORE"), name = "d2")
    public double modifyX(double value){
        return getSpyglassSensitivity(accumulatedDX);
    }

    // Modify Y sensitivity only when using the spyglass.
    @ModifyVariable(method = "turnPlayer",slice = @Slice(
            from = @At(value = "INVOKE", target = "Lnet/minecraft/util/SmoothDouble;reset()V", ordinal = 1),
            to = @At(value = "INVOKE", target = "Lnet/minecraft/util/SmoothDouble;reset()V", ordinal = 2)
    ), at = @At(value = "STORE"), name = "d3")
    public double modifyY(double value){
        return getSpyglassSensitivity(accumulatedDY);
    }*/

    @Shadow @Final private Minecraft minecraft;

    @Shadow private double lastMouseEventTime;

    @Shadow @Final private SmoothDouble smoothTurnX;

    @Shadow @Final private SmoothDouble smoothTurnY;

    @Inject(method = "turnPlayer",at = @At("HEAD"),cancellable = true)
    public void onMouseUpdate(CallbackInfo ci){
        if (null != minecraft.player && minecraft.options.getCameraType().isFirstPerson() && minecraft.player.isScoping()){

            double d = Blaze3D.getTime();
            double e = d - this.lastMouseEventTime;
            this.lastMouseEventTime = d;
            double displacementX,displacementY;

            double sensitivity = minecraft.options.sensitivity * .6 + .2;
            double baseSensitivity = (sensitivity * sensitivity * sensitivity) * 8.0;
            double spyglassSensitivity = baseSensitivity * SpyglassImprovementsClient.MULTIPLIER;
            double smoothSensitivity= baseSensitivity * Mth.clamp(SpyglassImprovementsClient.MULTIPLIER*3,0.3f,0.85f);


            if(SpyglassImprovementsConfig.smoothCamera.get()){
                displacementX = this.smoothTurnX.getNewDeltaValue(this.accumulatedDX * smoothSensitivity, e * smoothSensitivity);
                displacementY = this.smoothTurnY.getNewDeltaValue(this.accumulatedDY * smoothSensitivity, e * smoothSensitivity);
            }else{
                smoothTurnX.reset();
                smoothTurnY.reset();
                displacementX = accumulatedDX * spyglassSensitivity;
                displacementY = accumulatedDY * spyglassSensitivity;
            }

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

    private double getSpyglassSensitivity(double accumulatedDY) {
        Minecraft client = Minecraft.getInstance();
        double sensitivity = client.options.sensitivity * .6 + .2;
        double baseSensitivity = (sensitivity * sensitivity * sensitivity) * 8.0;
        double spyglassSensitivity = baseSensitivity * SpyglassImprovementsClient.MULTIPLIER;
        return accumulatedDY *spyglassSensitivity;
    }

}
