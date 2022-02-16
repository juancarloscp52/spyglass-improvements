package me.juancarloscp52.spyglass_improvements.client;

import com.mojang.blaze3d.platform.InputConstants;
import me.juancarloscp52.spyglass_improvements.SpyglassImprovements;
import me.juancarloscp52.spyglass_improvements.client.gui.SpyglassConfigurationScreen;
import me.juancarloscp52.spyglass_improvements.config.SpyglassImprovementsConfig;
import me.juancarloscp52.spyglass_improvements.events.EventsHandler;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.ConfigGuiHandler;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = "spyglass_improvements", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SpyglassImprovementsClient {

    public static KeyMapping useSpyglass = new KeyMapping(
            "key.spyglass-improvements.use",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_Z,
            "category.spyglass-improvements");

    // Zoom multiplier
    public static double MULTIPLIER = .1f;

    @SubscribeEvent
    public static void init(final FMLClientSetupEvent event) {
        Gui.SPYGLASS_SCOPE_LOCATION = SpyglassImprovementsConfig.overlay.get().getResourceLocation();
        ClientRegistry.registerKeyBinding(useSpyglass);
        SpyglassImprovements.LOGGER.info("Spyglass Improvements client Initialized");

        MinecraftForge.EVENT_BUS.register(new EventsHandler());
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SpyglassImprovementsConfig.SPEC);

        ModLoadingContext.get().registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory.class,
                () -> new ConfigGuiHandler.ConfigGuiFactory(
                        (minecraft, screen) -> new SpyglassConfigurationScreen(screen)
                )
        );

    }

}
