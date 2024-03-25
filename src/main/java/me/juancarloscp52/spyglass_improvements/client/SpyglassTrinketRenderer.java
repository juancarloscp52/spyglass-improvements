package me.juancarloscp52.spyglass_improvements.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SpyglassTrinketRenderer implements TrinketRenderer {
    private static final Minecraft MINECRAFT = Minecraft.getInstance();
    @Override
    public void render(ItemStack itemStack, SlotReference slotReference, EntityModel<? extends LivingEntity> entityModel, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, LivingEntity livingEntity, float v, float v1, float v2, float v3, float v4, float v5) {
        ItemRenderer itemRenderer = MINECRAFT.getItemRenderer();

        BakedModel spyglassModel = itemRenderer.getModel(
                Items.SPYGLASS.getDefaultInstance(),
                MINECRAFT.level,
                MINECRAFT.player,
                1
        );

        poseStack.pushPose();

        if (livingEntity.isCrouching()) {
            poseStack.translate(0.0F, 0.15F, 0.32F);
        }

        poseStack.translate(0.16, 0.6, 0.16);
        poseStack.mulPose(Direction.DOWN.getRotation());
        poseStack.scale(0.7f, 0.7f, 0.7f);

        itemRenderer.render(
                itemStack,
                ItemDisplayContext.NONE,
                true,
                poseStack,
                multiBufferSource,
                i,
                OverlayTexture.NO_OVERLAY,
                spyglassModel
        );

        poseStack.popPose();
    }
}
