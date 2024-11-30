package me.juancarloscp52.spyglass_improvements.fabric.client;

import me.juancarloscp52.spyglass_improvements.client.SpyglassImprovementsClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

public final class SpyglassImprovementsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        KeyBindingHelper.registerKeyBinding(SpyglassImprovementsClient.useSpyglass);
        SpyglassImprovementsClient.getInstance().init(null);

        ClientTickEvents.END_CLIENT_TICK.register(client -> SpyglassImprovementsClient.getInstance().onClientTick(client));
    }
}
