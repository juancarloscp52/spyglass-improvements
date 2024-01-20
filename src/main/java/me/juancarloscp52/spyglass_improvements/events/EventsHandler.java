package me.juancarloscp52.spyglass_improvements.events;

import me.juancarloscp52.spyglass_improvements.curios.CuriosIntegration;
import me.juancarloscp52.spyglass_improvements.client.SpyglassImprovementsClient;
import me.juancarloscp52.spyglass_improvements.config.SpyglassImprovementsConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

public class EventsHandler {
    private static final Minecraft client = Minecraft.getInstance();

    @SubscribeEvent
    public void onFovModifier(ScopeFOVModifierEvent event){
        event.setNewFov((float) SpyglassImprovementsClient.MULTIPLIER);
    }

    @SubscribeEvent
    public void onMouseScroll(InputEvent.MouseScrollingEvent event){
        if (client.player == null) return;
        LocalPlayer player = client.player;

        if (player.isScoping() && client.options.getCameraType().isFirstPerson()) {
            SpyglassImprovementsClient.MULTIPLIER = Mth.clamp(
                    SpyglassImprovementsClient.MULTIPLIER - (event.getScrollDelta() * SpyglassImprovementsConfig.multiplierDelta.get()),
                    .1,
                    .8
            );
            player.playSound(
                    SoundEvents.SPYGLASS_STOP_USING,
                    1.0f,
                    (float) (1.0f + (1 * (1 - SpyglassImprovementsClient.MULTIPLIER) * (1- SpyglassImprovementsClient.MULTIPLIER)))
            );
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGuiOverlayEvent.Pre event) {
        if (client.player == null) return;

        if (
                event.getOverlay() == VanillaGuiOverlay.SPYGLASS.type()
                        && SpyglassImprovementsConfig.overlay.get()==SpyglassImprovementsConfig.Overlays.None
        ) {
            event.setCanceled(true);
        }

        if (
                event.getOverlay() == VanillaGuiOverlay.CROSSHAIR.type()
                        && !SpyglassImprovementsConfig.showCrosshair.get()
                        && client.player.isScoping()
        ) {
            event.setCanceled(true);
        }
    }

    // Tracks the slot were the spyglass is located
    public int slot = -1;
    @SubscribeEvent
    public void onStopUsingItem (LivingEntityUseItemEvent.Stop event){
        if (client.player == null || client.gameMode == null) return;
        LocalPlayer player = client.player;

        // When stop using, reset spyglass position if it was changed.
        if (event.getEntity().level().isClientSide && SpyglassImprovementsClient.useSpyglass.consumeClick()) {
            SpyglassImprovementsClient.useSpyglass.release();
            int slot = this.slot;
            if (player.getOffhandItem().getItem().equals(Items.SPYGLASS)) {
                if (slot > 8) {
                    client.gameMode.handleInventoryMouseClick(0, slot, 40, ClickType.SWAP, player);
                    this.slot = -1;
                }
            } else if (slot >= 0 && slot <= 8) {
                player.getInventory().selected = slot;
            }
        }
    }

    public static boolean force_spyglass = false;
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event){
        if (client.player == null || client.gameMode == null) return;
        LocalPlayer player = client.player;

        if (
                SpyglassImprovementsClient.useSpyglass.isDown()
                        && client.rightClickDelay == 0
                        && !player.isUsingItem()
                        && !force_spyglass
        ) {
            // Player wants and is able to use spyglass
            if (player.getOffhandItem().getItem().equals(Items.SPYGLASS)) {
                // In offhand
                client.gameMode.useItem(player, InteractionHand.OFF_HAND);

            } else if (player.getMainHandItem().getItem().equals(Items.SPYGLASS)) {
                // In main hand
                client.gameMode.useItem(player, InteractionHand.MAIN_HAND);

            } else if (player.isCreative()) {
                // On creative mode, we do not need to have a spyglass to use it
                forceUseSpyglass(player);

            } else if (ModList.get().isLoaded("curios") && CuriosIntegration.verifySpyglassCurios(player)) {
                // In curios slot
                forceUseSpyglass(player);

            } else {
                // In inventory
                slot = findSlotByItem(player.getInventory(), Items.SPYGLASS);

                // If the spyglass is in the inventory, move it to the offhand
                if (slot >= 9) {
                    client.gameMode.handleInventoryMouseClick(0, slot, 40, ClickType.SWAP, player);
                } else if (slot >= 0) {
                    // If the item is in the hot-bar, select the item and interact with it.
                    int oldSlot = player.getInventory().selected;
                    player.getInventory().selected = slot;
                    slot = oldSlot;
                    client.gameMode.useItem(player, InteractionHand.MAIN_HAND);
                }
            }
        } else if (
                !SpyglassImprovementsClient.useSpyglass.isDown()
                        && force_spyglass
        ) {
            // Release force spyglass when not pressing the key-bind
            force_spyglass = false;
            player.playSound(SoundEvents.SPYGLASS_STOP_USING, 1.0f, 1.0f);
        }
    }


    /**
     * Finds a slot containing an item stack of the given item type.
     * @param inventory - Players inventory.
     * @param item - Item type to search for.
     * @return Slot ID, -1 if item was not found.
     */
    private int findSlotByItem(Inventory inventory, Item item) {
        for(int i = 0; i < inventory.items.size(); ++i) {
            if (!inventory.items.get(i).isEmpty() && inventory.items.get(i).is(item)) {
                return i;
            }
        }
        return -1;
    }

    private void forceUseSpyglass(LocalPlayer player) {
        force_spyglass = true;
        player.playSound(SoundEvents.SPYGLASS_USE, 1.0f, 1.0f);
    }
}
