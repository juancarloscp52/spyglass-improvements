package me.juancarloscp52.spyglass_improvements.forge.client.integratons;

import me.juancarloscp52.spyglass_improvements.client.integrations.IEquipmentIntegration;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

import java.util.concurrent.atomic.AtomicBoolean;

public class CuriosIntegration implements IEquipmentIntegration {

    public static void enqueueSlot(final InterModEnqueueEvent evt) {
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.BELT.getMessageBuilder().build());
    }

    @Override
    public boolean isPlayerUsingSpyglass(Player player) {
        AtomicBoolean spyglassInCurios = new AtomicBoolean(false);
        if (CuriosApi.getCuriosHelper().findFirstCurio(player, Items.SPYGLASS).isPresent()) {
                spyglassInCurios.set(true);
        }

        return spyglassInCurios.get();
    }
}
