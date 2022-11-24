/*
 * Copyright (c) 2021 juancarloscp52
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package me.juancarloscp52.spyglass_improvements.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;


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
        zoomMultiplierWidget = new SpyglassSliderWidget(this.width / 2 - 150, this.height / 6 + 14 - 6, 300, 20,"options.spyglass-improvements.zoomQuantity",(settings.multiplierDelta-.1f)*1.1f,(slider, translationKey, value) -> Component.translatable("options.spyglass-improvements.zoomQuantity", String.format("%.2f",.1f+((float)value)*.9f)), value -> settings.multiplierDelta = .1f+((float)value)*.9f);
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
            onDone();
        }).pos(this.width / 2 - 100, this.height / 6 + 144).width(200).build();
        this.addRenderableWidget(reset);
        this.done = Button.builder(CommonComponents.GUI_DONE, button -> onDone()).pos(this.width / 2 - 100, this.height / 6 + 168).width(200).build();
        this.addRenderableWidget(done);
    }

    public void render(PoseStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        drawString(matrices, this.font, title, this.width / 2 - font.width(title)/2, 20, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
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
