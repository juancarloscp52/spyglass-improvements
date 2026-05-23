package me.juancarloscp52.spyglass_improvements.fabric.client.integrations;

import eu.pb4.trinkets.api.TrinketsApi;
import me.juancarloscp52.spyglass_improvements.client.integrations.IEquipmentIntegration;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;

public class TrinketsIntegration implements IEquipmentIntegration {
    @Override
    public boolean isPlayerUsingSpyglass(Player player) {
        var trinketComponentOptional = TrinketsApi.getAttachment(player);
        return trinketComponentOptional.isEquipped(Items.SPYGLASS);
        //return trinketComponentOptional.map(trinketComponent -> trinketComponent.isEquipped(Items.SPYGLASS)).orElse(false);
    }
}
