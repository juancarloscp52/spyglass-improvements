package me.juancarloscp52.spyglass_improvements.mixin;

import me.juancarloscp52.spyglass_improvements.SpyglassImprovements;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {

    @Shadow private double accumulatedDX;
    @Shadow private double accumulatedDY;

    // Modify X sensitivity only when using the spyglass.
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
    }

    private double getSpyglassSensitivity(double accumulatedDY) {
        Minecraft client = Minecraft.getInstance();
        double sensitivity = client.options.sensitivity * .6 + .2;
        double baseSensitivity = (sensitivity * sensitivity * sensitivity) * 8.0;
        double spyglassSensitivity = baseSensitivity * SpyglassImprovements.MULTIPLIER;
        return accumulatedDY *spyglassSensitivity;
    }

}
