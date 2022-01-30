package me.juancarloscp52.spyglass_improvements;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class SpyglassImprovements implements ModInitializer {
    public static boolean FORCE_SPYGLASS = false;
    @Override
    public void onInitialize() {
        ServerPlayNetworking.registerGlobalReceiver(new Identifier("spyglass-improvements", "toggle"), (server, player, handler, buf, responseSender) -> FORCE_SPYGLASS = buf.readBoolean());
    }
}
