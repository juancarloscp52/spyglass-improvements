//package me.juancarloscp52.spyglass_improvements.client;
//import net.minecraft.client.gui.GuiGraphics;
//import net.minecraft.client.gui.components.Button;
//import net.minecraft.client.gui.components.StringWidget;
//import net.minecraft.client.gui.components.Tooltip;
//import net.minecraft.client.gui.layouts.*;
//import net.minecraft.client.gui.screens.OptionsScreen;
//import net.minecraft.client.gui.screens.Screen;
//import net.minecraft.client.resources.language.I18n;
//import net.minecraft.network.chat.CommonComponents;
//import net.minecraft.network.chat.Component;
//
//
//public class SpyglassConfigurationScreen extends Screen {
//
//    private static final Component TITLE = Component.translatable("options.spyglass-improvements.title");
//    private final Screen lastScreen;
//    Settings settings = SpyglassImprovementsClient.getInstance().settings;
//    public SpyglassConfigurationScreen(Screen parent) {
//        super(TITLE);
//        lastScreen = parent;
//    }
//
//    @Override
//    protected void init(){
//        linearLayout.addChild(new StringWidget(TITLE, this.font), LayoutSettings.defaults().alignHorizontallyCenter().paddingTop(10));
//
//        GridLayout gridLayout = new GridLayout();
//        gridLayout.defaultCellSetting().padding(4, 4, 4, 0);
//        GridLayout.RowHelper rowHelper = gridLayout.createRowHelper(2);
//        rowHelper.addChild(new SpyglassSliderWidget(0, 0, 308, 20,"options.spyglass-improvements.zoomQuantity",(settings.multiplierDelta-.1f)*1.25f,(slider, translationKey, value) -> Component.translatable("options.spyglass-improvements.zoomQuantity", String.format("%.2f",.1f+((float)value)*.8f)), value -> settings.multiplierDelta = .1f+((float)value)*.8f),2);
//        rowHelper.addChild(getExtraZoomButton());
//        rowHelper.addChild(getSpyglassOverlayButton());
//        rowHelper.addChild(getShowCrosshairButton());
//        rowHelper.addChild(getSmoothCameraButton());
//        rowHelper.addChild(getForceSpyglassButton());
//        rowHelper.addChild(getHideSettingsButton());
//        rowHelper.addChild(SpacerElement.height(26), 2);
//        rowHelper.addChild(
//                getResetButton(),
//                2,
//                rowHelper.newCellSettings().alignHorizontallyCenter()
//        );
//        rowHelper.addChild(
//                Button.builder(CommonComponents.GUI_DONE, button -> onDone()).width(200).build(),
//                2,
//                rowHelper.newCellSettings().alignHorizontallyCenter().paddingTop(4)
//        );
//        this.layout.addToContents(gridLayout);
//        FrameLayout.alignInRectangle(gridLayout, 0, this.height / 6 - 12, this.width, this.height, 0.5F, 0.0F);
//        this.layout.visitWidgets(this::addRenderableWidget);
//        this.layout.arrangeElements();
//    }
//
//    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
//        this.renderBackground(guiGraphics);
//        super.render(guiGraphics, i, j, f);
//    }
//
//    private Button getSpyglassOverlayButton(){
//        return Button.builder(Component.translatable("options.spyglass-improvements.spyglassOverlay", I18n.get("options.spyglass-improvements.spyglassOverlay."+settings.overlay)), button -> {
//            settings.overlay++;
//            if(settings.overlay>3)
//                settings.overlay=0;
//            button.setMessage(Component.translatable("options.spyglass-improvements.spyglassOverlay", I18n.get("options.spyglass-improvements.spyglassOverlay."+settings.overlay)));
//        }).build();
//    }
//
//    private Button getShowCrosshairButton(){
//        return Button.builder(Component.translatable("options.spyglass-improvements.showCrosshair", settings.showCrossHair? CommonComponents.GUI_YES:CommonComponents.GUI_NO),button -> {
//            settings.showCrossHair=!settings.showCrossHair;
//            button.setMessage(Component.translatable("options.spyglass-improvements.showCrosshair", settings.showCrossHair? CommonComponents.GUI_YES:CommonComponents.GUI_NO));
//        }).build();
//    }
//
//    private Button getSmoothCameraButton(){
//        return Button.builder(Component.translatable("options.spyglass-improvements.smoothCamera", settings.smoothCamera? CommonComponents.GUI_YES:CommonComponents.GUI_NO), button -> {
//            settings.smoothCamera=!settings.smoothCamera;
//            button.setMessage(Component.translatable("options.spyglass-improvements.smoothCamera", settings.smoothCamera? CommonComponents.GUI_YES:CommonComponents.GUI_NO));
//        }).build();
//    }
//
//    private Button getHideSettingsButton(){
//        return Button.builder(Component.translatable("options.spyglass-improvements.hideSettingsButton", settings.hideSettingsButton? CommonComponents.GUI_YES:CommonComponents.GUI_NO), button -> {
//            settings.hideSettingsButton=!settings.hideSettingsButton;
//            button.setMessage(Component.translatable("options.spyglass-improvements.hideSettingsButton", settings.hideSettingsButton? CommonComponents.GUI_YES:CommonComponents.GUI_NO));
//        }).tooltip(Tooltip.create(Component.translatable("options.spyglass-improvements.hideSettingsButton.tooltip"))).build();
//    }
//
//    private Button getExtraZoomButton(){
//        return Button.builder(Component.translatable("options.spyglass-improvements.extraZoom", settings.extraZoom ? CommonComponents.GUI_YES:CommonComponents.GUI_NO), button -> {
//            settings.extraZoom=!settings.extraZoom;
//            button.setMessage(Component.translatable("options.spyglass-improvements.extraZoom", settings.extraZoom ? CommonComponents.GUI_YES:CommonComponents.GUI_NO));
//        }).build();
//    }
//
//    private Button getForceSpyglassButton(){
//        return Button.builder(Component.translatable("options.spyglass-improvements.forceSpyglass", settings.userForceSpyglass ? CommonComponents.GUI_YES:CommonComponents.GUI_NO), button -> {
//            settings.userForceSpyglass=!settings.userForceSpyglass;
//            button.setMessage(Component.translatable("options.spyglass-improvements.forceSpyglass", settings.userForceSpyglass ? CommonComponents.GUI_YES:CommonComponents.GUI_NO));
//        }).tooltip(Tooltip.create(Component.translatable("options.spyglass-improvements.forceSpyglass.tooltip"))).build();
//    }
//
//    private Button getResetButton(){
//        return Button.builder(Component.translatable("options.spyglass-improvements.reset"), button -> {
//            SpyglassImprovementsClient.getInstance().settings=new Settings();
//            onDone();
//        }).width(200).build();
//    }
//
//    private void onDone() {
//        SpyglassImprovementsClient.getInstance().saveSettings();
//        onClose();
//    }
//
//    @Override
//    public void onClose() {
//        this.minecraft.setScreen(this.lastScreen);
//    }
//}
