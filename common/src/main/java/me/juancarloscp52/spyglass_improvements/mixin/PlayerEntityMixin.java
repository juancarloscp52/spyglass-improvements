package me.juancarloscp52.spyglass_improvements.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.juancarloscp52.spyglass_improvements.client.SpyglassImprovementsClient;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public class PlayerEntityMixin {

    @ModifyReturnValue(method = "isScoping", at=@At("RETURN"))
    public boolean forceSpyglass(boolean original){
        return original || SpyglassImprovementsClient.force_spyglass;
    }

}
