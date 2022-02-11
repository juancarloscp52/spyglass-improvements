package me.juancarloscp52.spyglass_improvements.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class SpyglassImprovementsPacketHandler {

    public static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("spyglass_improvements", "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals);

    public static void registerMessages(){
        INSTANCE.registerMessage(0,SpyglassTogglePacket.class,SpyglassTogglePacket::encode,SpyglassTogglePacket::new,SpyglassTogglePacket::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
    }
}
