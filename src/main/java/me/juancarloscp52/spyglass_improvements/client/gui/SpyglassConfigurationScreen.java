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

package me.juancarloscp52.spyglass_improvements.client.gui;

import me.juancarloscp52.spyglass_improvements.config.SpyglassImprovementsConfig;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.widget.ExtendedButton;
import org.jetbrains.annotations.NotNull;

public class SpyglassConfigurationScreen extends Screen {

    SpyglassSliderWidget zoomMultiplierWidget;
    ExtendedButton done;
    ExtendedButton reset;
    Screen parent;

    SpyglassImprovementsConfig.Overlays currentOverlay;
    double currentMultiplier;
    boolean hideCrossHair;
    boolean smoothCamera;
    public SpyglassConfigurationScreen(Screen parent) {
        super(Component.translatable("options.spyglass-improvements.title"));
        this.parent = parent;
    }

    protected void init() {
        currentOverlay = SpyglassImprovementsConfig.overlay.get();
        currentMultiplier =SpyglassImprovementsConfig.multiplierDelta.get();
        hideCrossHair = SpyglassImprovementsConfig.showCrosshair.get();
        smoothCamera = SpyglassImprovementsConfig.smoothCamera.get();

        zoomMultiplierWidget = new SpyglassSliderWidget(this.width / 2 - 150, this.height / 6 + 14 - 6, 300, 20,"options.spyglass-improvements.zoomQuantity",(currentMultiplier-.1)*1.1,(slider, translationKey, value) -> Component.translatable("options.spyglass-improvements.zoomQuantity", String.format("%.2f",.1+(value*.9))), value -> {
            currentMultiplier=.1+(value*.9);
        });

        this.addRenderableWidget(zoomMultiplierWidget);

        ExtendedButton spyGlassOverlay = new ExtendedButton(this.width / 2 - 150, this.height / 6 + 38 - 6, 300, 20, Component.translatable("options.spyglass-improvements.spyglassOverlay", I18n.get("options.spyglass-improvements.spyglassOverlay."+currentOverlay.name())), button -> {
            currentOverlay=currentOverlay.next();
            button.setMessage(Component.translatable("options.spyglass-improvements.spyglassOverlay", I18n.get("options.spyglass-improvements.spyglassOverlay."+currentOverlay.name())));
        });
        this.addRenderableWidget(spyGlassOverlay);

        ExtendedButton showCrosshair = new ExtendedButton(this.width / 2 - 150, this.height / 6 + 62 - 6, 300, 20, Component.translatable("options.spyglass-improvements.showCrosshair", hideCrossHair? CommonComponents.GUI_YES:CommonComponents.GUI_NO), button -> {
            hideCrossHair=!hideCrossHair;
            button.setMessage(Component.translatable("options.spyglass-improvements.showCrosshair", hideCrossHair? CommonComponents.GUI_YES:CommonComponents.GUI_NO));
        });
        this.addRenderableWidget(showCrosshair);

        ExtendedButton smoothCameraButton = new ExtendedButton(this.width / 2 - 150, this.height / 6 + 86 - 6, 300, 20, Component.translatable("options.spyglass-improvements.smoothCamera", smoothCamera? CommonComponents.GUI_YES:CommonComponents.GUI_NO), button -> {
            smoothCamera=!smoothCamera;
            button.setMessage(Component.translatable("options.spyglass-improvements.smoothCamera", smoothCamera? CommonComponents.GUI_YES:CommonComponents.GUI_NO));
        });
        this.addRenderableWidget(smoothCameraButton);

        this.reset = new ExtendedButton(this.width / 2 - 100, this.height / 6 + 144, 200, 20, Component.translatable("options.spyglass-improvements.reset"), button -> {
            currentOverlay= SpyglassImprovementsConfig.Overlays.Default;
            currentMultiplier=0.1;
            onDone();
        });
        this.addRenderableWidget(reset);

        this.done = new ExtendedButton(this.width / 2 - 100, this.height / 6 + 168, 200, 20, CommonComponents.GUI_DONE, button -> onDone());
        this.addRenderableWidget(done);
    }

    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(guiGraphics, mouseX, mouseY, delta);
        guiGraphics.drawCenteredString(this.font, title, this.width / 2, 40, 16777215);
        super.render(guiGraphics, mouseX, mouseY, delta);
    }

    private void onDone() {
        SpyglassImprovementsConfig.overlay.set(currentOverlay);
        SpyglassImprovementsConfig.multiplierDelta.set(currentMultiplier);
        SpyglassImprovementsConfig.showCrosshair.set(hideCrossHair);
        SpyglassImprovementsConfig.smoothCamera.set(smoothCamera);

        Gui.SPYGLASS_SCOPE_LOCATION = currentOverlay.getResourceLocation();
        onClose();
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(this.parent);
    }
}
