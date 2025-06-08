package me.juancarloscp52.spyglass_improvements.forge.client.integratons;

import me.juancarloscp52.spyglass_improvements.client.integrations.IEquipmentIntegration;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.concurrent.atomic.AtomicBoolean;

public class CuriosIntegration implements IEquipmentIntegration {

    @Override
    public boolean isPlayerUsingSpyglass(Player player) {
        AtomicBoolean spyglassInCurios = new AtomicBoolean(false);
        CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
            // Find if a spyglass is in player curio slots
            if (iCuriosItemHandler.findFirstCurio(Items.SPYGLASS).isPresent()) {
                spyglassInCurios.set(true);
            }
        });

        return spyglassInCurios.get();
    }
}
