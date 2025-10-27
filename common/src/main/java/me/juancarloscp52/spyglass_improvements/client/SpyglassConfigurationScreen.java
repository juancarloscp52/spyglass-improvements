package me.juancarloscp52.spyglass_improvements.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class SpyglassConfigurationScreen extends Screen {
    Settings settings = SpyglassImprovementsClient.getInstance().settings;
    AbstractSliderButton zoomMultiplierWidget;
    Button done;
    Button reset;
    Screen parent;

    public SpyglassConfigurationScreen(Screen parent) {
        super(Component.translatable("options.spyglass-improvements.title"));
        this.parent = parent;
    }

    protected void init() {
        zoomMultiplierWidget = new SpyglassSliderWidget(this.width / 2 - 150, this.height / 6 + 14 - 6, 300, 20,"options.spyglass-improvements.zoomQuantity",(settings.multiplierDelta-.1f)*1.25f,(slider, translationKey, value) -> Component.translatable("options.spyglass-improvements.zoomQuantity", String.format("%.2f",.1f+((float)value)*.8f)), value -> settings.multiplierDelta = .1f+((float)value)*.8f);
        this.addRenderableWidget(zoomMultiplierWidget);
        Button spyGlassOverlay = Button.builder(Component.translatable("options.spyglass-improvements.spyglassOverlay", I18n.get("options.spyglass-improvements.spyglassOverlay."+settings.overlay)),button -> {
                    settings.overlay++;
                    if(settings.overlay>3)
                        settings.overlay=0;
                    button.setMessage(Component.translatable("options.spyglass-improvements.spyglassOverlay", I18n.get("options.spyglass-improvements.spyglassOverlay."+settings.overlay)));
                }).pos(this.width / 2 - 150, this.height / 6 + 38 - 6).width(300).build();
        this.addRenderableWidget(spyGlassOverlay);
        Button showCrosshair = Button.builder(Component.translatable("options.spyglass-improvements.showCrosshair", settings.showCrossHair? CommonComponents.GUI_YES:CommonComponents.GUI_NO),button -> {
            settings.showCrossHair=!settings.showCrossHair;
            button.setMessage(Component.translatable("options.spyglass-improvements.showCrosshair", settings.showCrossHair? CommonComponents.GUI_YES:CommonComponents.GUI_NO));
        }).pos(this.width / 2 - 150, this.height / 6 + 62 - 6).width(300).build();
        this.addRenderableWidget(showCrosshair);
        Button smoothCamera = Button.builder(Component.translatable("options.spyglass-improvements.smoothCamera", settings.smoothCamera? CommonComponents.GUI_YES:CommonComponents.GUI_NO), button -> {
            settings.smoothCamera=!settings.smoothCamera;
            button.setMessage(Component.translatable("options.spyglass-improvements.smoothCamera", settings.smoothCamera? CommonComponents.GUI_YES:CommonComponents.GUI_NO));
        }).pos(this.width / 2 - 150, this.height / 6 + 86 - 6).width(300).build();
        this.addRenderableWidget(smoothCamera);
        Button hideButton = Button.builder(Component.translatable("options.spyglass-improvements.hideSettingsButton", settings.hideSettingsButton? CommonComponents.GUI_YES:CommonComponents.GUI_NO), button -> {
                    settings.hideSettingsButton=!settings.hideSettingsButton;
                    button.setMessage(Component.translatable("options.spyglass-improvements.hideSettingsButton", settings.hideSettingsButton? CommonComponents.GUI_YES:CommonComponents.GUI_NO));
                }).pos(this.width / 2 - 150, this.height / 6 + 110 - 6).width(300).tooltip(Tooltip.create(Component.translatable("options.spyglass-improvements.hideSettingsButton.tooltip"))).build();
        this.addRenderableWidget(hideButton);
        this.reset = Button.builder(Component.translatable("options.spyglass-improvements.reset"), button -> {
            SpyglassImprovementsClient.getInstance().settings=new Settings();
            if(SpyglassImprovementsClient.getInstance().forge)
                SpyglassImprovementsClient.getInstance().settings.hideSettingsButton=true;
            onDone();
        }).pos(this.width / 2 - 100, this.height / 6 + 144).width(200).build();
        this.addRenderableWidget(reset);
        this.done = Button.builder(CommonComponents.GUI_DONE, button -> onDone()).pos(this.width / 2 - 100, this.height / 6 + 168).width(200).build();
        this.addRenderableWidget(done);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        guiGraphics.drawString(this.font, title, this.width / 2 - font.width(title)/2, 20, 16777215);
    }



    private void onDone() {
        SpyglassImprovementsClient.getInstance().saveSettings();
        onClose();
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(this.parent);
    }
}
