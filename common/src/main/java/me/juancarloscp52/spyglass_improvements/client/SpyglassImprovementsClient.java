package me.juancarloscp52.spyglass_improvements.client;

import com.google.gson.Gson;
import com.mojang.blaze3d.platform.InputConstants;
import me.juancarloscp52.spyglass_improvements.client.integrations.IEquipmentIntegration;
import me.juancarloscp52.spyglass_improvements.mixin.MinecraftClientInvoker;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SpyglassImprovementsClient {
    public static final String MOD_ID = "spyglass_improvements";

    private static SpyglassImprovementsClient INSTANCE;
    public static SpyglassImprovementsClient getInstance() {
        if(INSTANCE==null){
            INSTANCE = new SpyglassImprovementsClient();
        }
        return INSTANCE;
    }

    public static final Logger LOGGER = LogManager.getLogger();
    public Settings settings;
    private IEquipmentIntegration equipmentIntegration;

    public static boolean force_spyglass = false;
    // Tracks the slot were the spyglass is located
    public static int slot = -1;
    // Zoom multiplier
    public static float MULTIPLIER = .1f;


    public static KeyMapping useSpyglass = new KeyMapping(
            "key.spyglass-improvements.use",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_Z,
            "category.spyglass-improvements");

    public void init(IEquipmentIntegration equipmentIntegration) {
        INSTANCE = this;
        this.equipmentIntegration = equipmentIntegration;
//        if(this.equipmentIntegration!=null)
//            this.equipmentIntegration.registerRenderer();
        loadSettings();
        LOGGER.info("Spyglass Improvements Client Initialized");
    }



    public void onClientTick(Minecraft client){
        if(client.player==null || client.gameMode == null)
            return;

        LocalPlayer player = client.player;

        if(SpyglassImprovementsClient.useSpyglass.isDown() && ((MinecraftClientInvoker)client).getItemUseCooldown() == 0 && !client.player.isUsingItem()){
            // Player wants and is able to use spyglass
            slot = findSlotByItem(client.player.getInventory(), Items.SPYGLASS);

            if(player.getOffhandItem().getItem().equals(Items.SPYGLASS)){
                // In offhand
                client.gameMode.useItem(player, InteractionHand.OFF_HAND);
            } else if (player.getMainHandItem().getItem().equals(Items.SPYGLASS)) {
                // In main hand
                client.gameMode.useItem(player, InteractionHand.MAIN_HAND);
            } else if (player.isCreative()) {
                // On creative mode, we do not need to have a spyglass to use it
                forceUseSpyglass(player);
            } else if (equipmentIntegration!=null && equipmentIntegration.isPlayerUsingSpyglass(player)) {
                // In Equipment Slot
                forceUseSpyglass(player);
            } else {
                if (slot >= 9) {
                    // If the spyglass is in the inventory, move it to the offhand
                    client.gameMode.handleInventoryMouseClick(0, slot, 40, ClickType.SWAP, player);
                    client.gameMode.useItem(player, InteractionHand.OFF_HAND);
                } else if (slot >= 0) {
                    // If the item is in the hot-bar, select the item and interact with it.
                    int oldSlot = player.getInventory().selected;
                    player.getInventory().selected=slot;
                    slot = oldSlot;
                    client.gameMode.useItem(player, InteractionHand.MAIN_HAND);
                }
            }

        } else if (!useSpyglass.isDown() && force_spyglass){
            // Release force spyglass when not pressing the key-bind
            force_spyglass = false;
            //ClientPlayNetworking.send(new SpyglassTogglePacket(false));
            player.playSound(SoundEvents.SPYGLASS_STOP_USING, 1.0f, 1.0f);
        }
    }

    /**
     * Finds a slot containing an itemstack of the given item type.
     * @param inventory - Players inventory.
     * @param item - Item type to search for.
     * @return Slot ID, -1 if item was not found.
     */
    private int findSlotByItem(Inventory inventory, Item item) {
        List<ItemStack> items = inventory.items;
        for(int i = 0; i < items.size(); ++i) {
            if (!items.get(i).isEmpty() && items.get(i).is(item)) {
                return i;
            }
        }

        return -1;
    }

    private void forceUseSpyglass(LocalPlayer player) {
        if(force_spyglass)
            return;

        force_spyglass = true;
        //ClientPlayNetworking.send(new SpyglassTogglePacket(true));
        player.playSound(SoundEvents.SPYGLASS_USE, 1.0f, 1.0f);
    }

    public void loadSettings() {
        File file = new File("./config/spyglass-improvements/settings.json");
        Gson gson = new Gson();
        if (file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                settings = gson.fromJson(fileReader, Settings.class);
                fileReader.close();
            } catch (IOException e) {
                LOGGER.warn("Could not load Spyglass Improvements settings: " + e.getLocalizedMessage());
            }
        } else {
            settings = new Settings();
            saveSettings();
        }
    }

    public void saveSettings() {
        Gson gson = new Gson();
        File file = new File("./config/spyglass-improvements/settings.json");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdir();
        }
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(gson.toJson(settings));
            fileWriter.close();
        } catch (IOException e) {
            LOGGER.warn("Could not save Spyglass Improvements settings: " + e.getLocalizedMessage());
        }
    }

}
