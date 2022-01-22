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

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;


public class SpyglassConfigurationScreen extends Screen {
    Settings settings = SpyglassImprovementsClient.getInstance().settings;

    SliderWidget zoomMultiplierWidget;
    ButtonWidget done;
    ButtonWidget reset;

    Screen parent;

    public SpyglassConfigurationScreen(Screen parent) {
        super(new TranslatableText("options.spyglass-improvements.title"));
        this.parent = parent;
    }

    protected void init() {

        zoomMultiplierWidget = new SpyglassSliderWidget(this.width / 2 - 150, this.height / 6 + 48 - 6, 300, 20,"options.spyglass-improvements.zoomQuantity",(settings.multiplierDelta-.1f)*1.1f,(slider, translationKey, value) -> new TranslatableText("options.spyglass-improvements.zoomQuantity", String.format("%.2f",.1f+((float)value)*.9f)), value -> settings.multiplierDelta = .1f+((float)value)*.9f);
        this.addDrawableChild(zoomMultiplierWidget);
        ButtonWidget spyGlassOverlay = new ButtonWidget(this.width / 2 - 150, this.height / 6 + 72 - 6, 300, 20, new TranslatableText("options.spyglass-improvements.spyglassOverlay", I18n.translate("options.spyglass-improvements.spyglassOverlay."+settings.overlay)), button -> {
            settings.overlay++;
            if(settings.overlay>2)
                settings.overlay=0;
            button.setMessage(new TranslatableText("options.spyglass-improvements.spyglassOverlay", I18n.translate("options.spyglass-improvements.spyglassOverlay."+settings.overlay)));
        });
        this.addDrawableChild(spyGlassOverlay);

        this.reset = new ButtonWidget(this.width / 2 - 100, this.height / 6 + 144, 200, 20, new TranslatableText("options.spyglass-improvements.reset"), button -> {
            SpyglassImprovementsClient.getInstance().settings=new Settings();
            onDone();
        });
        this.addDrawableChild(reset);
        this.done = new ButtonWidget(this.width / 2 - 100, this.height / 6 + 168, 200, 20, ScreenTexts.DONE, button -> onDone());
        this.addDrawableChild(done);
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        drawTextWithShadow(matrices, this.textRenderer, title, this.width / 2 - textRenderer.getWidth(title)/2, 20, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }

    private void onDone() {
        SpyglassImprovementsClient.getInstance().saveSettings();
        onClose();
    }

    @Override
    public void onClose() {
        this.client.setScreen(this.parent);
    }
}
