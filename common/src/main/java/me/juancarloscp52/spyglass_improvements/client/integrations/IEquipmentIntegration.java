package me.juancarloscp52.spyglass_improvements.client.integrations;
import net.minecraft.world.entity.player.Player;

public interface IEquipmentIntegration {

    boolean isPlayerUsingSpyglass(Player player);

//    default void render(
//    ItemStack stack,
//    LivingEntity livingEntity,
//    PoseStack poseStack,
//    MultiBufferSource renderTypeBuffer,
//    int light){
//        Minecraft mc = Minecraft.getInstance();
//        ItemRenderer itemRenderer = mc.getItemRenderer();
//        BakedModel spyglassModel = itemRenderer.getModel(
//                Items.SPYGLASS.getDefaultInstance(),
//                mc.level,
//                mc.player,
//                1
//        );
//
//        poseStack.pushPose();
//
//        if (livingEntity.isCrouching()) {
//            poseStack.translate(0.0F, 0.15F, 0.32F);
//        }
//
//        poseStack.translate(0.16, 0.6, 0.16);
//        poseStack.mulPose(Direction.DOWN.getRotation());
//        poseStack.scale(0.7f, 0.7f, 0.7f);
//
//        itemRenderer.render(
//                stack,
//                ItemDisplayContext.NONE,
//                true,
//                poseStack,
//                renderTypeBuffer,
//                light,
//                OverlayTexture.NO_OVERLAY,
//                spyglassModel
//        );
//
//        poseStack.popPose();
//
//    }

}
