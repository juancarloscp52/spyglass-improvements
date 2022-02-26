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
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TranslatableComponent;


public class SpyglassConfigurationScreen extends Screen {
    Settings settings = SpyglassImprovementsClient.getInstance().settings;

    AbstractSliderButton zoomMultiplierWidget;
    Button done;
    Button reset;

    Screen parent;

    public SpyglassConfigurationScreen(Screen parent) {
        super(new TranslatableComponent("options.spyglass-improvements.title"));
        this.parent = parent;
    }

    protected void init() {
        zoomMultiplierWidget = new SpyglassSliderWidget(this.width / 2 - 150, this.height / 6 + 48 - 6, 300, 20,"options.spyglass-improvements.zoomQuantity",(settings.multiplierDelta-.1f)*1.1f,(slider, translationKey, value) -> new TranslatableComponent("options.spyglass-improvements.zoomQuantity", String.format("%.2f",.1f+((float)value)*.9f)), value -> settings.multiplierDelta = .1f+((float)value)*.9f);
        this.addRenderableWidget(zoomMultiplierWidget);

        Button spyGlassOverlay = new Button(this.width / 2 - 150, this.height / 6 + 72 - 6, 300, 20, new TranslatableComponent("options.spyglass-improvements.spyglassOverlay", I18n.get("options.spyglass-improvements.spyglassOverlay."+settings.overlay)), button -> {
            settings.overlay++;
            if(settings.overlay>3)
                settings.overlay=0;
            button.setMessage(new TranslatableComponent("options.spyglass-improvements.spyglassOverlay", I18n.get("options.spyglass-improvements.spyglassOverlay."+settings.overlay)));
        });
        this.addRenderableWidget(spyGlassOverlay);

        Button hideButton = new Button(this.width / 2 - 150, this.height / 6 + 96 - 6, 300, 20, new TranslatableComponent("options.spyglass-improvements.hideSettingsButton", settings.hideSettingsButton? CommonComponents.GUI_YES:CommonComponents.GUI_NO), button -> {
            settings.hideSettingsButton=!settings.hideSettingsButton;
            button.setMessage(new TranslatableComponent("options.spyglass-improvements.hideSettingsButton", settings.hideSettingsButton? CommonComponents.GUI_YES:CommonComponents.GUI_NO));
        },(buttonWidget, matrixStack, i, j) -> this.renderTooltip(matrixStack, font.split(new TranslatableComponent("options.spyglass-improvements.hideSettingsButton.tooltip"), this.width / 2), i, j));
        this.addRenderableWidget(hideButton);

        this.reset = new Button(this.width / 2 - 100, this.height / 6 + 144, 200, 20, new TranslatableComponent("options.spyglass-improvements.reset"), button -> {
            SpyglassImprovementsClient.getInstance().settings=new Settings();
            onDone();
        });
        this.addRenderableWidget(reset);

        this.done = new Button(this.width / 2 - 100, this.height / 6 + 168, 200, 20, CommonComponents.GUI_DONE, button -> onDone());
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
