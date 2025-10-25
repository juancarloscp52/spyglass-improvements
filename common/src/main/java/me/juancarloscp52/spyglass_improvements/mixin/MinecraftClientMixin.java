package me.juancarloscp52.spyglass_improvements.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.juancarloscp52.spyglass_improvements.client.SpyglassImprovementsClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftClientMixin {

    @Shadow static Minecraft instance;

    @Shadow @Nullable public LocalPlayer player;

    @ModifyExpressionValue(method = "handleKeybinds", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/KeyMapping;isDown()Z", ordinal = 2))
    public boolean handleInput(boolean original){
        return original || SpyglassImprovementsClient.useSpyglass.isDown();
    }

    @Inject(method = "handleKeybinds", at = @At(value = "INVOKE",shift = At.Shift.AFTER, target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;releaseUsingItem(Lnet/minecraft/world/entity/player/Player;)V"))
    public void stopUsing(CallbackInfo ci){
        if(player==null)
            return;

        // When stop using, reset spyglass position if it was changed.
        if(SpyglassImprovementsClient.useSpyglass.consumeClick()){
            ((KeyBindingInvoker) SpyglassImprovementsClient.useSpyglass).invokeReset();
            int slot = SpyglassImprovementsClient.slot;
            if(player.getOffhandItem().getItem().equals(Items.SPYGLASS)){
                if(slot > 8 && instance.gameMode!=null) {
                    instance.gameMode.handleInventoryMouseClick(0, slot, 40, ClickType.SWAP, player);
                    SpyglassImprovementsClient.slot = -1;
                }
            }else if(slot >= 0 && slot <=8) {
                player.getInventory().setSelectedSlot(slot);
            }
        }
    }
}
