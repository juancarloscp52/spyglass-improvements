package me.juancarloscp52.spyglass_improvements.client;

import dev.emi.trinkets.api.TrinketsApi;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.Items;

public class TrinketsIntegration {

    public static void registerRenderer(){
        TrinketRendererRegistry.registerRenderer(Items.SPYGLASS, new SpyglassTrinketRenderer());
    }
    public static boolean verifySpyglassTrinket(LocalPlayer player){
        var trinketComponentOptional = TrinketsApi.getTrinketComponent(player);
        return trinketComponentOptional.map(trinketComponent -> trinketComponent.isEquipped(Items.SPYGLASS)).orElse(false);
    }

}
