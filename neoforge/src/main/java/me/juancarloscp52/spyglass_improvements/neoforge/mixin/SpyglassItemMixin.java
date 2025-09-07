package me.juancarloscp52.spyglass_improvements.neoforge.mixin;

import io.wispforest.accessories.api.core.Accessory;
import io.wispforest.accessories.api.core.AccessoryRegistry;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpyglassItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpyglassItem.class)
public class SpyglassItemMixin extends Item implements Accessory {

    public SpyglassItemMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "<init>", at= @At("RETURN"))
    public void init (Properties properties, CallbackInfo ci){
        AccessoryRegistry.register(this, this);
    }

    @Override
    public boolean canEquipFromUse(ItemStack stack, SlotReference reference) {
        return false;
    }
}
