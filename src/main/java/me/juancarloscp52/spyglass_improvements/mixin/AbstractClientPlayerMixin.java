package me.juancarloscp52.spyglass_improvements.mixin;

import me.juancarloscp52.spyglass_improvements.events.ScopeFOVModifierEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public class AbstractClientPlayerMixin{

    @Inject(method = "getFieldOfViewModifier",at=@At(value = "RETURN"),cancellable = true)
    public void onScopingFov(CallbackInfoReturnable<Float> cir){
        Minecraft client = Minecraft.getInstance();
        if (null != client.player && client.options.getCameraType().isFirstPerson() && client.player.isScoping()){
            ScopeFOVModifierEvent scopeFOVModifierEvent = new ScopeFOVModifierEvent(cir.getReturnValue());
            MinecraftForge.EVENT_BUS.post(scopeFOVModifierEvent);
            cir.setReturnValue((float)scopeFOVModifierEvent.getNewFov());
        }
    }
}
