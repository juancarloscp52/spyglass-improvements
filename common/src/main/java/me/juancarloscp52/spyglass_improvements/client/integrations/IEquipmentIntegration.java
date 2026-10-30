package me.juancarloscp52.spyglass_improvements.client.integrations;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public interface IEquipmentIntegration {

    boolean isPlayerUsingSpyglass(Player player);

    void registerRenderer();

    default void render(
    ItemStack stack,
    LivingEntity livingEntity,
    EntityModel<?> entityModel,
    PoseStack poseStack,
    MultiBufferSource renderTypeBuffer,
    int light){
        Minecraft mc = Minecraft.getInstance();
        ItemRenderer itemRenderer = mc.getItemRenderer();
        BakedModel spyglassModel = itemRenderer.getModel(
                Items.SPYGLASS.getDefaultInstance(),
                mc.level,
                mc.player,
                1
        );

        poseStack.pushPose();

        if (entityModel instanceof HumanoidModel<?> humanoidModel) {
            humanoidModel.body.translateAndRotate(poseStack);
        } else if (livingEntity.isCrouching()) {
            poseStack.translate(0.0F, 0.15F, 0.32F);
        }

        poseStack.translate(0.16, 0.6, 0.16);
        poseStack.mulPose(Direction.DOWN.getRotation());
        poseStack.scale(0.7f, 0.7f, 0.7f);

        itemRenderer.render(
                stack,
                ItemDisplayContext.NONE,
                true,
                poseStack,
                renderTypeBuffer,
                light,
                OverlayTexture.NO_OVERLAY,
                spyglassModel
        );

        poseStack.popPose();

    }

}
