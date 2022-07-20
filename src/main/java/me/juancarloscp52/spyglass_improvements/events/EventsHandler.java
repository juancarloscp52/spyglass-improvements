package me.juancarloscp52.spyglass_improvements.events;

import me.juancarloscp52.spyglass_improvements.client.SpyglassImprovementsClient;
import me.juancarloscp52.spyglass_improvements.config.SpyglassImprovementsConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventsHandler {

    @SubscribeEvent
    public void registerKeymapping(RegisterKeyMappingsEvent event){
        event.register(SpyglassImprovementsClient.useSpyglass);
    }

    @SubscribeEvent
    public void onFovModifier(ScopeFOVModifierEvent event){
        event.setNewFov((float) SpyglassImprovementsClient.MULTIPLIER);
    }

    @SubscribeEvent
    public void onMouseScroll(InputEvent.MouseScrollingEvent event){
        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.player;
        if(player != null && player.isScoping() && client.options.getCameraType().isFirstPerson()){
            SpyglassImprovementsClient.MULTIPLIER = Mth.clamp(SpyglassImprovementsClient.MULTIPLIER-(event.getScrollDelta() * SpyglassImprovementsConfig.multiplierDelta.get()), .1,.8);
            player.playSound(SoundEvents.SPYGLASS_STOP_USING, 1.0f, (float)(1.0f+(1*(1- SpyglassImprovementsClient.MULTIPLIER)*(1- SpyglassImprovementsClient.MULTIPLIER))));
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGuiOverlayEvent.Pre event){

        if(event.getOverlay()== VanillaGuiOverlay.SPYGLASS.type()
                && SpyglassImprovementsConfig.overlay.get()==SpyglassImprovementsConfig.Overlays.None){
            event.setCanceled(true);
        }

        if(event.getOverlay()==VanillaGuiOverlay.CROSSHAIR.type() && !SpyglassImprovementsConfig.showCrosshair.get()
                && Minecraft.getInstance().player.isScoping()){
            event.setCanceled(true);
        }
    }

    // Tracks the slot were the spyglass is located
    public int slot = -1;
    @SubscribeEvent
    public void onStopUsingItem (LivingEntityUseItemEvent.Stop event){
        Minecraft client = Minecraft.getInstance();
        // When stop using, reset spyglass position if it was changed.
        if(event.getEntity().level.isClientSide && SpyglassImprovementsClient.useSpyglass.consumeClick()){
            SpyglassImprovementsClient.useSpyglass.release();
            int slot = this.slot;
            if(client.player.getOffhandItem().getItem().equals(Items.SPYGLASS)){
                if(slot > 8) {
                    client.gameMode.handleInventoryMouseClick(0, slot, 40, ClickType.SWAP, client.player);
                    this.slot = -1;
                }
            }else if(slot >= 0 && slot <=8) {
                client.player.getInventory().selected = slot;
            }
        }
    }

    public static boolean force_spyglass = false;
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event){
        Minecraft client = Minecraft.getInstance();
        if(null != client.player) {
            if (SpyglassImprovementsClient.useSpyglass.isDown() && client.rightClickDelay == 0 && !client.player.isUsingItem()) {
                if (!client.player.getOffhandItem().getItem().equals(Items.SPYGLASS)) {
                    slot = client.player.getInventory().findSlotMatchingItem(new ItemStack(Items.SPYGLASS));
                    //If the spyglass is in the inventory, move it to the off hand
                    if (slot >= 9) {
                        client.gameMode.handleInventoryMouseClick(0, slot, 40, ClickType.SWAP, client.player);
                        return;
                    } else if (slot >= 0) {
                        // If the item is in the hotbar, select the item and interact with it.
                        int oldSlot = client.player.getInventory().selected;
                        client.player.getInventory().selected = slot;
                        slot = oldSlot;
                        client.gameMode.useItem(client.player, InteractionHand.MAIN_HAND);
                        return;
                    }
                    // On creative mode, we do not need to have a spyglass to use it
                    if (client.player.isCreative() && !force_spyglass) {
                        force_spyglass=true;
                        client.player.playSound(SoundEvents.SPYGLASS_USE, 1.0f, 1.0f);
                    }
                } else {
                    client.gameMode.useItem(client.player, InteractionHand.OFF_HAND);
                }
            }
            // Release force spyglass when not pressing the keybind
            if(force_spyglass && !SpyglassImprovementsClient.useSpyglass.isDown()){
                force_spyglass=false;
                client.player.playSound(SoundEvents.SPYGLASS_STOP_USING, 1.0f, 1.0f);
            }
        }
    }

}
