package me.juancarloscp52.spyglass_improvements.integrations;

import io.wispforest.accessories.api.*;
import me.juancarloscp52.spyglass_improvements.client.integrations.IEquipmentIntegration;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;


public class AccessoriesIntegration implements IEquipmentIntegration {

    @Override
    public boolean isPlayerUsingSpyglass(Player player) {
        return AccessoriesCapability.get(player).isEquipped(Items.SPYGLASS);
    }
    }
