package me.juancarloscp52.spyglass_improvements.forge.client;
import me.juancarloscp52.spyglass_improvements.client.SpyglassConfigurationScreen;
import me.juancarloscp52.spyglass_improvements.client.SpyglassImprovementsClient;
import me.juancarloscp52.spyglass_improvements.client.integrations.IEquipmentIntegration;
import me.juancarloscp52.spyglass_improvements.forge.client.integratons.CuriosIntegration;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod.EventBusSubscriber(modid = SpyglassImprovementsClient.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
@Mod(value = SpyglassImprovementsClient.MOD_ID)
public class SpyglassImprovementsClientForge {

    public SpyglassImprovementsClientForge(FMLJavaModLoadingContext context){
        MinecraftForge.EVENT_BUS.addListener(this::onClientTick);
        context.getModEventBus().addListener(this::registerKeymapping);
        context.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(
                        (minecraft, screen) -> new SpyglassConfigurationScreen(screen)));

        IEquipmentIntegration curios = null;
        if (ModList.get().isLoaded("curios")) {
            curios = new CuriosIntegration();
        }
        SpyglassImprovementsClient.getInstance().init(curios, true);
    }

    public void onClientTick(TickEvent.ClientTickEvent event){
        SpyglassImprovementsClient.getInstance().onClientTick(Minecraft.getInstance());
    }

    public void registerKeymapping(RegisterKeyMappingsEvent event){
        event.register(SpyglassImprovementsClient.useSpyglass);
    }
}
