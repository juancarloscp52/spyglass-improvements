package me.juancarloscp52.enhancedspyglass.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MinecraftClient.class)
public interface MinecraftClientInvoker {
    @Invoker("doItemUse")
    void invokeDoItemUse();

    @Accessor("itemUseCooldown")
    int getItemUseCooldown();

}
