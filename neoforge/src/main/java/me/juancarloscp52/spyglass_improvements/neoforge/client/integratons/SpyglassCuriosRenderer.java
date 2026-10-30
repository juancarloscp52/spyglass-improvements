package me.juancarloscp52.spyglass_improvements.neoforge.client.integratons;

import com.mojang.blaze3d.vertex.PoseStack;
import me.juancarloscp52.spyglass_improvements.client.integrations.IEquipmentIntegration;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class SpyglassCuriosRenderer implements ICurioRenderer {
    private final IEquipmentIntegration equipmentIntegration;

    public SpyglassCuriosRenderer(IEquipmentIntegration equipmentIntegration){
        this.equipmentIntegration = equipmentIntegration;
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(
            ItemStack stack,
            SlotContext slotContext,
            PoseStack poseStack,
            RenderLayerParent<T, M> renderLayerParent,
            MultiBufferSource renderTypeBuffer,
            int light,
            float limbSwing,
            float limbSwingAmount,
            float partialTicks,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
        equipmentIntegration.render(stack,slotContext.entity(),renderLayerParent.getModel(),poseStack,renderTypeBuffer,light);
    }
}