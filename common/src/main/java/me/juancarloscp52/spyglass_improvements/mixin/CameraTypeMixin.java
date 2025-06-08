package me.juancarloscp52.spyglass_improvements.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CameraType.class)
public class CameraTypeMixin {

    // Allow using spyglass while in third person.
    @ModifyReturnValue(method = "isFirstPerson",at = @At("RETURN"))
    public boolean forceFirstPerson(boolean original){
        if(Minecraft.getInstance().player!=null)
            return original || Minecraft.getInstance().player.isScoping();
        return original;
    }

}
