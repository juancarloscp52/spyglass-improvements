package me.juancarloscp52.spyglass_improvements.events;

import me.juancarloscp52.spyglass_improvements.SpyglassImprovements;
import me.juancarloscp52.spyglass_improvements.config.SpyglassImprovementsConfig;
import me.juancarloscp52.spyglass_improvements.network.SpyglassImprovementsPacketHandler;
import me.juancarloscp52.spyglass_improvements.network.SpyglassTogglePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventsHandler {

    @SubscribeEvent
    public void onFovModifier(ScopeFOVModifierEvent event){
        event.setNewFov((float) SpyglassImprovements.MULTIPLIER);
    }

    @SubscribeEvent
    public void onMouseScroll(InputEvent.MouseScrollEvent event){
        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.player;
        if(player != null && player.isScoping() && client.options.getCameraType().isFirstPerson()){
            SpyglassImprovements.MULTIPLIER = Mth.clamp(SpyglassImprovements.MULTIPLIER-(event.getScrollDelta() * SpyglassImprovementsConfig.multiplierDelta.get()), .1,.8);
            player.playSound(SoundEvents.SPYGLASS_STOP_USING, 1.0f, (float)(1.0f+(1*(1- SpyglassImprovements.MULTIPLIER)*(1- SpyglassImprovements.MULTIPLIER))));
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.PreLayer event){
        if(event.getOverlay().equals(ForgeIngameGui.SPYGLASS_ELEMENT)
                && SpyglassImprovementsConfig.overlay.get()==SpyglassImprovementsConfig.Overlays.None){
            event.setCanceled(true);
        }
    }

    // Tracks the slot were the spyglass is located
    public int slot = -1;
    @SubscribeEvent
    public void onStopUsingItem (LivingEntityUseItemEvent.Stop event){
        Minecraft client = Minecraft.getInstance();
        // When stop using, reset spyglass position if it was changed.
        if(event.getEntityLiving().level.isClientSide && SpyglassImprovements.useSpyglass.consumeClick()){
            SpyglassImprovements.useSpyglass.release();
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

    private boolean force_spyglass = false;
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event){
        Minecraft client = Minecraft.getInstance();
        if(null != client.player) {
            if (SpyglassImprovements.useSpyglass.isDown() && client.rightClickDelay == 0 && !client.player.isUsingItem()) {
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
                        client.gameMode.useItem(client.player, client.level, InteractionHand.MAIN_HAND);
                        return;
                    }
                    // On creative mode, we do not need to have a spyglass to use it
                    if (client.player.isCreative() && !force_spyglass) {
                        force_spyglass=true;
                        SpyglassImprovementsPacketHandler.INSTANCE.sendToServer(new SpyglassTogglePacket(true));
                    }
                } else {
                    client.gameMode.useItem(client.player, client.level, InteractionHand.OFF_HAND);
                }
            }
            // Release force spyglass when not pressing the keybind
            if(force_spyglass && !SpyglassImprovements.useSpyglass.isDown()){
                force_spyglass=false;
                SpyglassImprovementsPacketHandler.INSTANCE.sendToServer(new SpyglassTogglePacket(false));
            }
        }
    }

}
