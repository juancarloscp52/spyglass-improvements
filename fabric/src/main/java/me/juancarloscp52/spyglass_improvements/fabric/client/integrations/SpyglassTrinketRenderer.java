package me.juancarloscp52.spyglass_improvements.fabric.client.integrations;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import me.juancarloscp52.spyglass_improvements.client.integrations.IEquipmentIntegration;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class SpyglassTrinketRenderer implements TrinketRenderer {

    private final IEquipmentIntegration trinkets;
    public SpyglassTrinketRenderer(IEquipmentIntegration trinkets){
        this.trinkets=trinkets;
    }

    @Override
    public void render(ItemStack itemStack, SlotReference slotReference, EntityModel<? extends LivingEntity> entityModel, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, LivingEntity livingEntity, float v, float v1, float v2, float v3, float v4, float v5) {
        this.trinkets.render(itemStack, livingEntity, entityModel, poseStack,multiBufferSource,i);
    }
}
