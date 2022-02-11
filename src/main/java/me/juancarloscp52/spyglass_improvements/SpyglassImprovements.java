package me.juancarloscp52.spyglass_improvements;

import com.mojang.blaze3d.platform.InputConstants;
import me.juancarloscp52.spyglass_improvements.client.gui.SpyglassConfigurationScreen;
import me.juancarloscp52.spyglass_improvements.config.SpyglassImprovementsConfig;
import me.juancarloscp52.spyglass_improvements.events.EventsHandler;
import me.juancarloscp52.spyglass_improvements.network.SpyglassImprovementsPacketHandler;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.ConfigGuiHandler;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

@Mod("spyglass_improvements")
public class SpyglassImprovements {

    public static final Logger LOGGER = LogManager.getLogger();
    public static boolean FORCE_SPYGLASS = false;
    public static KeyMapping useSpyglass = new KeyMapping(
            "key.spyglass-improvements.use",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_Z,
            "category.spyglass-improvements");

    // Zoom multiplier
    public static double MULTIPLIER = .1f;


    public SpyglassImprovements() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(new EventsHandler());

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SpyglassImprovementsConfig.SPEC);
        ModLoadingContext.get().registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory.class,
                () -> new ConfigGuiHandler.ConfigGuiFactory(
                        (minecraft, screen) -> new SpyglassConfigurationScreen(screen)
                )
        );
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(SpyglassImprovementsPacketHandler::registerMessages);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        Gui.SPYGLASS_SCOPE_LOCATION = SpyglassImprovementsConfig.overlay.get().getResourceLocation();
        ClientRegistry.registerKeyBinding(useSpyglass);
        LOGGER.info("Spyglass Improvements client Initialized");
    }
}
