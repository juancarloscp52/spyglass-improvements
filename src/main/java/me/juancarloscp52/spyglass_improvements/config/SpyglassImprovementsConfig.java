package me.juancarloscp52.spyglass_improvements.config;

import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;

public class SpyglassImprovementsConfig {

    public enum Overlays{
        Default(Gui.SPYGLASS_SCOPE_LOCATION),
        Clear(new ResourceLocation("spyglass_improvements","textures/spyglass_scope_clear.png")),
        Circle(new ResourceLocation("spyglass_improvements","textures/spyglass_scope_circle.png")),
        None(Gui.SPYGLASS_SCOPE_LOCATION);

        final ResourceLocation resourceLocation;
        private static final Overlays[] values = values();

        Overlays(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public ResourceLocation getResourceLocation() {
            return resourceLocation;
        }

        public Overlays next(){
            return values[(this.ordinal()+1)%values.length];
        }

    }
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<Overlays> overlay;
    public static final ForgeConfigSpec.ConfigValue<Double> multiplierDelta;
    //public boolean hideSettingsButton = false;
    static {
        BUILDER.push("Configuration file for spyglass Improvements");
        overlay = BUILDER.comment("Spyglass Overlay").defineEnum("overlay",Overlays.Default);
        multiplierDelta = BUILDER.comment("Size of zoom steps").defineInRange("multiplier",.1,0.0,1.0);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }

}
