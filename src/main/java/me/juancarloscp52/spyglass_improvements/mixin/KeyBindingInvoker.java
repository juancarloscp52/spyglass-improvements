package me.juancarloscp52.spyglass_improvements.mixin;

import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(KeyMapping.class)
public interface KeyBindingInvoker {

    @Invoker("release")
    void invokeReset();

}
