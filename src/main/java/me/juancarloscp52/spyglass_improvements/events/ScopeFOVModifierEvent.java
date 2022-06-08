package me.juancarloscp52.spyglass_improvements.events;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraftforge.eventbus.api.Event;

public class ScopeFOVModifierEvent extends Event {
    private final double fov;
    private double newFov;

    public ScopeFOVModifierEvent(double fov)
    {
        this.fov = fov;
        this.setNewFov(Mth.lerp(Minecraft.getInstance().options.fovEffectScale().get(), 1.0, fov));
    }

    public double getFov()
    {
        return fov;
    }

    public double getNewFov()
    {
        return newFov;
    }

    public void setNewFov(double newFov)
    {
        this.newFov = newFov;
    }
}
