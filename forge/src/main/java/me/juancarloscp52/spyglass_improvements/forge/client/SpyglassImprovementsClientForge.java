package me.juancarloscp52.spyglass_improvements.forge.client;
import me.juancarloscp52.spyglass_improvements.client.SpyglassConfigurationScreen;
import me.juancarloscp52.spyglass_improvements.client.SpyglassImprovementsClient;
import me.juancarloscp52.spyglass_improvements.client.integrations.IEquipmentIntegration;
import me.juancarloscp52.spyglass_improvements.forge.client.integratons.CuriosIntegration;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.ConfigGuiHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod.EventBusSubscriber(modid = SpyglassImprovementsClient.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SpyglassImprovementsClientForge {

    @SubscribeEvent
    public static void initClient (final FMLClientSetupEvent event){
        ClientRegistry.registerKeyBinding(SpyglassImprovementsClient.useSpyglass);
        MinecraftForge.EVENT_BUS.addListener(SpyglassImprovementsClientForge::onClientTick);
        ModLoadingContext.get().registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory.class,
                () -> new ConfigGuiHandler.ConfigGuiFactory(
                        (minecraft, screen) -> new SpyglassConfigurationScreen(screen)));

        IEquipmentIntegration curios = null;
        if (ModList.get().isLoaded("curios")) {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(CuriosIntegration::enqueueSlot);
            curios = new CuriosIntegration();
        }
        SpyglassImprovementsClient.getInstance().init(curios, true);
    }

    public static void onClientTick(TickEvent.ClientTickEvent event){
        SpyglassImprovementsClient.getInstance().onClientTick(Minecraft.getInstance());
    }
}
