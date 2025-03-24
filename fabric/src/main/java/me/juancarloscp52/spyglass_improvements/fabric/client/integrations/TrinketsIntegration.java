package me.juancarloscp52.spyglass_improvements.fabric.client.integrations;

import dev.emi.trinkets.api.TrinketsApi;
import me.juancarloscp52.spyglass_improvements.client.integrations.IEquipmentIntegration;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;

public class TrinketsIntegration implements IEquipmentIntegration {
    @Override
    public boolean isPlayerUsingSpyglass(Player player) {
        var trinketComponentOptional = TrinketsApi.getTrinketComponent(player);
        return trinketComponentOptional.map(trinketComponent -> trinketComponent.isEquipped(Items.SPYGLASS)).orElse(false);    }

    @Override
    public void registerRenderer() {
        //TrinketRendererRegistry.registerRenderer(Items.SPYGLASS, new SpyglassTrinketRenderer(this));
    }
}
