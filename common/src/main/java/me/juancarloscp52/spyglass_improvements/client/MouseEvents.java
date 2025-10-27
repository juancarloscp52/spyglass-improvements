package me.juancarloscp52.spyglass_improvements.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.SmoothDouble;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class MouseEvents {

    Minecraft minecraft = Minecraft.getInstance();

    public void onScroll(double vertical, CallbackInfo ci){
        float d = (float) ((Minecraft.getInstance().options.discreteMouseScroll().get() ? Math.signum(vertical) : vertical) * Minecraft.getInstance().options.mouseWheelSensitivity().get());
        LocalPlayer player = Minecraft.getInstance().player;
        if(player != null && player.isScoping() && Minecraft.getInstance().options.getCameraType().isFirstPerson()){
            float step = SpyglassImprovementsClient.MULTIPLIER*SpyglassImprovementsClient.getInstance().settings.multiplierDelta;
            SpyglassImprovementsClient.MULTIPLIER = Mth.clamp(SpyglassImprovementsClient.MULTIPLIER-(d* step), .0001f,.8f);            player.playSound(SoundEvents.SPYGLASS_STOP_USING, 1.0f, 1.0f+(1*(1- SpyglassImprovementsClient.MULTIPLIER)*(1- SpyglassImprovementsClient.MULTIPLIER)));
            ci.cancel();
        }
    }

    public double onDisplacementX(double value, double d, SmoothDouble smoothTurnX, double accumulatedDX){
        if (spyglass_improvements$isPlayerScoping()){
            double displacementX;
            double sensitivity = this.minecraft.options.sensitivity().get() * .6 + .2;
            double baseSensitivity = (sensitivity * sensitivity * sensitivity) * 8.0;
            double spyglassSensitivity = baseSensitivity * SpyglassImprovementsClient.MULTIPLIER;
            double smoothSensitivity= baseSensitivity * Mth.clamp(SpyglassImprovementsClient.MULTIPLIER*3,0.3f,0.85f);

            if(SpyglassImprovementsClient.getInstance().settings.smoothCamera){
                displacementX = smoothTurnX.getNewDeltaValue(accumulatedDX * smoothSensitivity, d * smoothSensitivity);
            }else{
                smoothTurnX.reset();
                displacementX = accumulatedDX * spyglassSensitivity;
            }

            return displacementX;
        }
        return value;
    }

    public double onDisplacementY(double value, double d, SmoothDouble smoothTurnY, double accumulatedDY){
        if (spyglass_improvements$isPlayerScoping()){
            double displacementY;
            double sensitivity = this.minecraft.options.sensitivity().get() * .6 + .2;
            double baseSensitivity = (sensitivity * sensitivity * sensitivity) * 8.0;
            double spyglassSensitivity = baseSensitivity * SpyglassImprovementsClient.MULTIPLIER;
            double smoothSensitivity= baseSensitivity * Mth.clamp(SpyglassImprovementsClient.MULTIPLIER*3,0.3f,0.85f);

            if(SpyglassImprovementsClient.getInstance().settings.smoothCamera){
                displacementY = smoothTurnY.getNewDeltaValue(accumulatedDY * smoothSensitivity, d * smoothSensitivity);
            }else{
                smoothTurnY.reset();
                displacementY = accumulatedDY * spyglassSensitivity;
            }

            return displacementY;
        }
        return value;
    }

    @Unique
    private boolean spyglass_improvements$isPlayerScoping(){
        return !this.minecraft.options.smoothCamera && null != minecraft.player && minecraft.options.getCameraType().isFirstPerson() && minecraft.player.isScoping();
    }
}
