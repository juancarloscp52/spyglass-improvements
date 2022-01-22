package me.juancarloscp52.enhancedspyglass.mixin;

import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(KeyBinding.class)
public interface KeyBindingInvoker {

    @Invoker("reset")
    void invokeReset();

}
