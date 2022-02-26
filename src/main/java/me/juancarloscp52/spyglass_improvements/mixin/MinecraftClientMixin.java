package me.juancarloscp52.spyglass_improvements.mixin;

import me.juancarloscp52.spyglass_improvements.client.SpyglassImprovementsClient;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Minecraft.class)
public class MinecraftClientMixin {

    @Redirect(method = "handleKeybinds", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/KeyMapping;isDown()Z", ordinal = 2))
    public boolean handleInput(KeyMapping instance){
        return instance.isDown() || SpyglassImprovementsClient.useSpyglass.isDown();
    }
    @Redirect(method = "handleKeybinds", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;releaseUsingItem(Lnet/minecraft/world/entity/player/Player;)V"))
    public void stopUsing(MultiPlayerGameMode instance, Player player){
        instance.releaseUsingItem(player);
        Minecraft client = Minecraft.getInstance();

        // When stop using, reset spyglass position if it was changed.
        if(SpyglassImprovementsClient.useSpyglass.consumeClick()){
            ((KeyBindingInvoker) SpyglassImprovementsClient.useSpyglass).invokeReset();
            int slot = SpyglassImprovementsClient.slot;
            if(player.getOffhandItem().getItem().equals(Items.SPYGLASS)){
                if(slot > 8) {
                    client.gameMode.handleInventoryMouseClick(0, slot, 40, ClickType.SWAP, client.player);
                    SpyglassImprovementsClient.slot = -1;
                }
            }else if(slot >= 0 && slot <=8) {
                player.getInventory().selected = slot;
            }
        }
    }
}
