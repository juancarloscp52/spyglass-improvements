package me.juancarloscp52.spyglass_improvements;


import me.juancarloscp52.spyglass_improvements.config.SpyglassImprovementsConfig;
import me.juancarloscp52.spyglass_improvements.curios.SpyglassCuriosRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("spyglass_improvements")
public class SpyglassImprovements {

    public static final Logger LOGGER = LogManager.getLogger();

    public SpyglassImprovements() {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT,() ->()-> ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, SpyglassImprovementsConfig.SPEC));

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            if (ModList.get().isLoaded("curios")) {
                SpyglassCuriosRenderer.register();
            } else {
                LOGGER.warn("Curios API not found, skipping Spyglass Improvements and Curios integration");
            }
        });
    }

}