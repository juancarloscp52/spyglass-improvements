package me.juancarloscp52.spyglass_improvements.curios;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

public class CuriosIntegration {
    public static void enqueueSlot(final InterModEnqueueEvent evt) {
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.BELT.getMessageBuilder().build());
    }

    public static boolean verifySpyglassCurios(Player player) {
        return CuriosApi.getCuriosHelper().findFirstCurio(player, Items.SPYGLASS).isPresent();
    }
}