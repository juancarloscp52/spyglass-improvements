package me.juancarloscp52.spyglass_improvements.mixin;

import me.juancarloscp52.spyglass_improvements.SpyglassImprovements;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerEntityMixin {

    @Inject(method = "isScoping", at=@At("RETURN"),cancellable = true)
    public void forceSpyglass(CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(cir.getReturnValue() || SpyglassImprovements.FORCE_SPYGLASS);
    }

}
