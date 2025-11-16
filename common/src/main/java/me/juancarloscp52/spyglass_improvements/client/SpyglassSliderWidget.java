package me.juancarloscp52.spyglass_improvements.client;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class SpyglassSliderWidget extends AbstractSliderButton {

    String translationKey;
    MessageSupplier messageSupplier;
    ValueUpdater valueUpdater;

    public SpyglassSliderWidget(int x, int y, int width, int height, String translationKey, double value, MessageSupplier messageSupplier, ValueUpdater valueUpdater) {
        super(x, y, width, height, new TranslatableComponent((translationKey), value), value);
        this.translationKey=translationKey;
        this.messageSupplier=messageSupplier;
        this.valueUpdater=valueUpdater;
        this.updateMessage();
    }

    protected void updateMessage() {
    }

    @Override
    public Component getMessage() {
        return this.messageSupplier.updateMessage(this,this.translationKey,this.value);
    }

    @Override
    protected void applyValue() {
        valueUpdater.applyValue(this.value);
    }

    public interface MessageSupplier {
        Component updateMessage(SpyglassSliderWidget slider,String translationKey, double value);
    }

    public interface ValueUpdater {
        void applyValue(double value);
    }
}
