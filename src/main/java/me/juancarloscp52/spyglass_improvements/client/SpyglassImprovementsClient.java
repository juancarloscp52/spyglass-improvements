package me.juancarloscp52.spyglass_improvements.client;

import com.google.gson.Gson;
import me.juancarloscp52.spyglass_improvements.mixin.MinecraftClientInvoker;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Environment(EnvType.CLIENT)
public class SpyglassImprovementsClient implements ClientModInitializer {

    public static int slot = -1;
    public static final Logger LOGGER = LogManager.getLogger();
    public static float MULTIPLIER = .1f;
    public static KeyBinding useSpyglass = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.spyglass-improvements.use",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_Z,
            "category.spyglass-improvements"));
    public Settings settings;
    private static SpyglassImprovementsClient INSTANCE;
    public static SpyglassImprovementsClient getInstance() {
        return INSTANCE;
    }

    @Override
    public void onInitializeClient() {
        INSTANCE = this;
        loadSettings();
        LOGGER.info("Initialized spyglass-improvements");
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(client.player !=null)
            if (useSpyglass.isPressed() && ((MinecraftClientInvoker)client).getItemUseCooldown() == 0 && !client.player.isUsingItem()) {
                if(!client.player.getOffHandStack().getItem().equals(Items.SPYGLASS)){
                    slot = client.player.getInventory().getSlotWithStack(new ItemStack(Items.SPYGLASS));
                    System.out.println("Found " + slot);
                    if(slot >= 9){
                        client.interactionManager.clickSlot(0, slot,40, SlotActionType.SWAP,client.player);
                    }else if (slot >=0){
                        int oldSlot=client.player.getInventory().selectedSlot;
                        client.player.getInventory().selectedSlot = slot;
                        slot = oldSlot;
                        client.interactionManager.interactItem(client.player, client.world, Hand.MAIN_HAND);
                        return;
                    }
                }else{
                    client.interactionManager.interactItem(client.player, client.world, Hand.OFF_HAND);
                    System.out.println("Interacting");
                }

            }
        });
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
