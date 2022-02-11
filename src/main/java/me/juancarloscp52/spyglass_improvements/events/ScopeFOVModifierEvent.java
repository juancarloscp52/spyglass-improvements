package me.juancarloscp52.spyglass_improvements.events;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraftforge.eventbus.api.Event;

public class ScopeFOVModifierEvent extends Event {
    private final float fov;
    private float newFov;

    public ScopeFOVModifierEvent(float fov)
    {
        this.fov = fov;
        this.setNewFov(Mth.lerp(Minecraft.getInstance().options.fovEffectScale, 1.0F, fov));
    }

    public float getFov()
    {
        return fov;
    }

    public float getNewFov()
    {
        return newFov;
    }

    public void setNewFov(float newFov)
    {
        this.newFov = newFov;
    }
}
