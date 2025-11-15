package me.juancarloscp52.spyglass_improvements.forge;

import me.juancarloscp52.spyglass_improvements.client.SpyglassImprovementsClient;
import me.juancarloscp52.spyglass_improvements.forge.client.SpyglassImprovementsClientForge;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(value = SpyglassImprovementsClient.MOD_ID)
public class SpyglassImprovements {

    public SpyglassImprovements(){
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> MinecraftForge.EVENT_BUS.addListener(SpyglassImprovementsClientForge::initClient));
    }

}