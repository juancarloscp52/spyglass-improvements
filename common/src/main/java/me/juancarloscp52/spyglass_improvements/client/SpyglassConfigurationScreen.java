package me.juancarloscp52.spyglass_improvements.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

public class SpyglassConfigurationScreen extends Screen {

    private static final Component TITLE = Component.translatable("options.spyglass-improvements.title");
    private final Screen lastScreen;
    static final int LEFT_OFFSET = -154;
    static final int RIGHT_OFFSET = 4;
    static final int WIDGET_WIDTH = 150;

    Settings settings = SpyglassImprovementsClient.getInstance().settings;

    public SpyglassConfigurationScreen(Screen parent) {
        super(TITLE);
        lastScreen = parent;
    }

    @Override
    public void init(){

        int centerX = this.width / 2;
        int startY = 55;
        SpyglassSliderWidget zoomSteps = new SpyglassSliderWidget(
                centerX+LEFT_OFFSET,
                startY,
                308,
                20,
                "options.spyglass-improvements.zoomQuantity",
                (settings.multiplierDelta-.1f)*1.25f,
                (slider, translationKey, value) -> Component.translatable("options.spyglass-improvements.zoomQuantity",
                String.format("%.2f",.1f+((float)value)*.8f)),
                value -> settings.multiplierDelta = .1f+((float)value)*.8f);

        this.addRenderableWidget(zoomSteps);
        this.addRenderableWidget(getExtraZoomButton(centerX+LEFT_OFFSET,startY+25));
        this.addRenderableWidget(getSpyglassOverlayButton(centerX+RIGHT_OFFSET,startY+25));
        this.addRenderableWidget(getShowCrosshairButton(centerX+LEFT_OFFSET,startY+50));
        this.addRenderableWidget(getSmoothCameraButton(centerX+RIGHT_OFFSET,startY+50));
        this.addRenderableWidget(getForceSpyglassButton(centerX+LEFT_OFFSET,startY+75));
        this.addRenderableWidget(getHideSettingsButton(centerX+RIGHT_OFFSET,startY+75));

        this.addRenderableWidget(getResetButton(centerX-100,this.height-55));
        this.addRenderableWidget(getDoneButton(centerX-100,this.height-30));
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        this.renderBackground(poseStack);
        drawCenteredString(poseStack, this.font, this.title, this.width / 2, 15, 16777215);
        super.render(poseStack, i, j, f);
    }

    private Button getSpyglassOverlayButton(int x, int y){
        return new Button(x,y,WIDGET_WIDTH,20,Component.translatable("options.spyglass-improvements.spyglassOverlay", I18n.get("options.spyglass-improvements.spyglassOverlay."+settings.overlay)), button -> {
            settings.overlay++;
            if(settings.overlay>3)
                settings.overlay=0;
            button.setMessage(Component.translatable("options.spyglass-improvements.spyglassOverlay", I18n.get("options.spyglass-improvements.spyglassOverlay."+settings.overlay)));
        });
    }

    private Button getShowCrosshairButton(int x, int y){
        return new Button(x, y, WIDGET_WIDTH, 20, Component.translatable("options.spyglass-improvements.showCrosshair", settings.showCrossHair? CommonComponents.GUI_YES:CommonComponents.GUI_NO),button -> {
            settings.showCrossHair=!settings.showCrossHair;
            button.setMessage(Component.translatable("options.spyglass-improvements.showCrosshair", settings.showCrossHair? CommonComponents.GUI_YES:CommonComponents.GUI_NO));
        });
    }

    private Button getSmoothCameraButton(int x, int y){
        return new Button(x, y, WIDGET_WIDTH, 20,Component.translatable("options.spyglass-improvements.smoothCamera", settings.smoothCamera? CommonComponents.GUI_YES:CommonComponents.GUI_NO), button -> {
            settings.smoothCamera=!settings.smoothCamera;
            button.setMessage(Component.translatable("options.spyglass-improvements.smoothCamera", settings.smoothCamera? CommonComponents.GUI_YES:CommonComponents.GUI_NO));
        });
    }

    private Button getHideSettingsButton(int x, int y){
        Button.OnTooltip onTooltip =  new Button.OnTooltip() {
            private final Component text = Component.translatable("options.spyglass-improvements.hideSettingsButton.tooltip");

            @Override
            public void onTooltip(Button button, PoseStack poseStack, int ix, int jx) {
                if (SpyglassConfigurationScreen.this.minecraft != null) {
                    SpyglassConfigurationScreen.this.renderTooltip(poseStack, SpyglassConfigurationScreen.this.minecraft.font.split(this.text, Math.max(SpyglassConfigurationScreen.this.width / 2 - 43, 170)), ix, jx);
                }

            }

            @Override
            public void narrateTooltip(Consumer<Component> consumer) {
                consumer.accept(this.text);
            }
        };
        return new Button(x, y, WIDGET_WIDTH, 20, Component.translatable("options.spyglass-improvements.hideSettingsButton", settings.hideSettingsButton? CommonComponents.GUI_YES:CommonComponents.GUI_NO), button -> {
            settings.hideSettingsButton=!settings.hideSettingsButton;
            button.setMessage(Component.translatable("options.spyglass-improvements.hideSettingsButton", settings.hideSettingsButton? CommonComponents.GUI_YES:CommonComponents.GUI_NO));
        }, onTooltip);
    }

    private Button getExtraZoomButton(int x, int y){
        return new Button(x, y, WIDGET_WIDTH, 20, Component.translatable("options.spyglass-improvements.extraZoom", settings.extraZoom ? CommonComponents.GUI_YES:CommonComponents.GUI_NO), button -> {
            settings.extraZoom=!settings.extraZoom;
            button.setMessage(Component.translatable("options.spyglass-improvements.extraZoom", settings.extraZoom ? CommonComponents.GUI_YES:CommonComponents.GUI_NO));
        });
    }

    private Button getForceSpyglassButton(int x, int y){
        Button.OnTooltip onTooltip =  new Button.OnTooltip() {
            private final Component text = Component.translatable("options.spyglass-improvements.forceSpyglass.tooltip");

            @Override
            public void onTooltip(Button button, PoseStack poseStack, int ix, int jx) {
                if (SpyglassConfigurationScreen.this.minecraft != null) {
                    SpyglassConfigurationScreen.this.renderTooltip(poseStack, SpyglassConfigurationScreen.this.minecraft.font.split(this.text, Math.max(SpyglassConfigurationScreen.this.width / 2 - 43, 170)), ix, jx);
                }
            }

            @Override
            public void narrateTooltip(Consumer<Component> consumer) {
                consumer.accept(this.text);
            }
        };
        return new Button(x, y, WIDGET_WIDTH, 20, Component.translatable("options.spyglass-improvements.forceSpyglass", settings.userForceSpyglass ? CommonComponents.GUI_YES:CommonComponents.GUI_NO), button -> {
            settings.userForceSpyglass=!settings.userForceSpyglass;
            button.setMessage(Component.translatable("options.spyglass-improvements.forceSpyglass", settings.userForceSpyglass ? CommonComponents.GUI_YES:CommonComponents.GUI_NO));
        },onTooltip);
    }

    private Button getDoneButton(int x, int y){
        return new Button(x, y, 200, 20, CommonComponents.GUI_DONE, button -> onDone());
    }

    private Button getResetButton(int x, int y){
        return new Button(x, y, 200, 20,Component.translatable("options.spyglass-improvements.reset"), button -> {
            SpyglassImprovementsClient.getInstance().settings=new Settings();
            SpyglassImprovementsClient.getInstance().settings.hideSettingsButton = SpyglassImprovementsClient.getInstance().forge;
            onDone();
        });
    }

    private void onDone() {
        SpyglassImprovementsClient.getInstance().saveSettings();
        onClose();
    }

    @Override
    public void onClose() {
        if (this.minecraft != null) {
            this.minecraft.setScreen(this.lastScreen);
        }
    }
}
