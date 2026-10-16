package me.juancarloscp52.spyglass_improvements.neoforge.client;
import me.juancarloscp52.spyglass_improvements.client.SpyglassConfigurationScreen;
import me.juancarloscp52.spyglass_improvements.client.SpyglassImprovementsClient;
import me.juancarloscp52.spyglass_improvements.client.integrations.IEquipmentIntegration;
import me.juancarloscp52.spyglass_improvements.integrations.AccessoriesIntegration;
import me.juancarloscp52.spyglass_improvements.integrations.TrinketsIntegration;
import me.juancarloscp52.spyglass_improvements.neoforge.client.integratons.CuriosIntegration;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = SpyglassImprovementsClient.MOD_ID, dist = Dist.CLIENT)
public class SpyglassImprovementsClientNeoForge {

    public SpyglassImprovementsClientNeoForge(IEventBus modEventBus){
        NeoForge.EVENT_BUS.addListener(this::onClientTick);
        modEventBus.register(this);

        IEquipmentIntegration integration = null;
        if (ModList.get().isLoaded("curios")) {
            integration = new CuriosIntegration();
        } else if (ModList.get().isLoaded("accessories")) {
            integration = new AccessoriesIntegration();
        } else if (ModList.get().isLoaded("trinkets_updated")) {
            integration = new TrinketsIntegration();
        }
        SpyglassImprovementsClient.getInstance().init(integration);
    }

    public void onClientTick(ClientTickEvent.Post event){
        SpyglassImprovementsClient.getInstance().onClientTick(Minecraft.getInstance());
    }

    @SubscribeEvent
    public void init(final FMLClientSetupEvent event) {
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class,
                () -> (mc, screen) -> new SpyglassConfigurationScreen(screen));
    }

    @SubscribeEvent
    public void registerKeymapping(RegisterKeyMappingsEvent event){
        event.register(SpyglassImprovementsClient.useSpyglass);
    }
}
