package me.juancarloscp52.spyglass_improvements.mixin;

import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CameraType.class)
public class CameraTypeMixin {

    // Allow using spyglass while in third person.
    @Inject(method = "isFirstPerson",at = @At("RETURN"), cancellable = true)
    public void forceFirstPerson(CallbackInfoReturnable<Boolean> cir){
        if(Minecraft.getInstance().player!=null)
            cir.setReturnValue(cir.getReturnValue() || Minecraft.getInstance().player.isScoping());
    }

}
