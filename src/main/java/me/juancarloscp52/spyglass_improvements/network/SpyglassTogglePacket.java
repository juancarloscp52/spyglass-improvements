package me.juancarloscp52.spyglass_improvements.network;

import me.juancarloscp52.spyglass_improvements.SpyglassImprovements;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SpyglassTogglePacket {

        private final boolean toggle;

        public SpyglassTogglePacket(boolean toggle) {
            this.toggle = toggle;
        }

        void encode(FriendlyByteBuf buf) {
            buf.writeBoolean(toggle);
        }
        // Decode
        SpyglassTogglePacket (FriendlyByteBuf buf) {
            this(buf.readBoolean());
        }

        void handle(Supplier<NetworkEvent.Context> context) {
            context.get().enqueueWork(() -> {
                SpyglassImprovements.LOGGER.info("Package Received "+this.toggle);
                SpyglassImprovements.FORCE_SPYGLASS = this.toggle;
                context.get().setPacketHandled(true);
            });
        }
}
