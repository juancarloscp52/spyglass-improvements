package me.juancarloscp52.spyglass_improvements.mixin;

import me.juancarloscp52.spyglass_improvements.client.SpyglassConfigurationScreen;
import me.juancarloscp52.spyglass_improvements.client.SpyglassImprovementsClient;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(OptionsScreen.class)
public class OptionsScreenMixin extends Screen {

    protected OptionsScreenMixin(Component title) {
        super(title);
    }

    @Inject(method = "init", at = @At(value = "INVOKE",target = "Lnet/minecraft/client/gui/layouts/HeaderAndFooterLayout;addToContents(Lnet/minecraft/client/gui/layouts/LayoutElement;)Lnet/minecraft/client/gui/layouts/LayoutElement;", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD)
    public void addBedrockIfyButtonBelowSliders(CallbackInfo ci, LinearLayout linearLayout, LinearLayout linearLayout2, GridLayout gridLayout, GridLayout.RowHelper rowHelper) {
        if(!SpyglassImprovementsClient.getInstance().settings.hideSettingsButton)
            rowHelper.addChild(Button.builder(Component.translatable("options.spyglass-improvements.title"), button -> this.minecraft.gui.setScreen(new SpyglassConfigurationScreen(this))).build());
    }

}
