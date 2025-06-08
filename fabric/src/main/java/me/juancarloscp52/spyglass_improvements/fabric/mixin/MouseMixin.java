package me.juancarloscp52.spyglass_improvements.fabric.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import me.juancarloscp52.spyglass_improvements.client.MouseEvents;
import net.minecraft.client.MouseHandler;
import net.minecraft.util.SmoothDouble;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public abstract class MouseMixin {

    @Shadow @Final private SmoothDouble smoothTurnX;

    @Shadow @Final private SmoothDouble smoothTurnY;

    @Shadow private double accumulatedDX;

    @Shadow private double accumulatedDY;

    @Unique
    MouseEvents spyglass_improvements$mouseEvents = new MouseEvents();

    @Inject(method = "onScroll",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;swapPaint(D)V"),cancellable = true)
    private void onScroll(long window, double horizontal, double vertical, CallbackInfo ci){
        spyglass_improvements$mouseEvents.onScroll(vertical,ci);
    }

    @ModifyVariable(method = "turnPlayer", at=@At("STORE"), ordinal = 2)
    public double modifyDisplacementX(double value, @Local(ordinal = 1) double d){
        return spyglass_improvements$mouseEvents.onDisplacementX(value, d, smoothTurnX, accumulatedDX);
    }

    @ModifyVariable(method = "turnPlayer", at=@At("STORE"), ordinal = 3)
    public double modifyDisplacementY(double value, @Local(ordinal = 1) double d){
        return spyglass_improvements$mouseEvents.onDisplacementY(value, d, smoothTurnY, accumulatedDY);
    }

    @WrapWithCondition(method = "turnPlayer",at = @At(value = "INVOKE", target = "Lnet/minecraft/util/SmoothDouble;reset()V", ordinal = 0))
    public boolean cancelSmoothXReset(SmoothDouble instance){
        return false;
    }
    @WrapWithCondition(method = "turnPlayer",at = @At(value = "INVOKE", target = "Lnet/minecraft/util/SmoothDouble;reset()V", ordinal = 1))
    public boolean cancelSmoothYReset(SmoothDouble instance){
        return false;
    }
}
