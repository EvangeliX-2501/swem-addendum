package com.evangelix.swemaddendum.abstract_steed;

import com.alaharranhonor.swem.client.horse.render.SwemHorseRenderer;
import com.alaharranhonor.swem.client.model.ModelBoneType;
import com.alaharranhonor.swem.entity.horse.HorseModelType;
import com.alaharranhonor.swem.entity.horse.LegacySwemHorse;
import com.alaharranhonor.swem.item.tack.HorseArmorTier;
import com.alaharranhonor.swem.item.tack.TackItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class AbstractSteedRenderer extends SwemHorseRenderer implements GeoRendererHack<LegacySwemHorse> {

    public static final Map<ResourceLocation, ResourceLocation> WING_CACHE = new HashMap<>();
    public static final Set<ResourceLocation> NO_WING_CACHE = new HashSet<>();

    public boolean isReRendering = false;

    public AbstractSteedRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public abstract GeoModel<LegacySwemHorse> getGeoModel();

    @Override
    public ResourceLocation getTextureLocation(LegacySwemHorse animatable) {
        return this.isReRendering ? null : this.getGeoModel().getTextureResource(animatable, this);
    }

    @Override
    public void updateAnimatedTextureFrame(LegacySwemHorse animatable) {
        if(!this.isReRendering) {
            super.updateAnimatedTextureFrame(animatable);
        }
    }

    @Override
    public void reRender(BakedGeoModel model, PoseStack poseStack, MultiBufferSource bufferSource, LegacySwemHorse animatable, RenderType renderType, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay, int colour) {
        this.isReRendering = true;
        super.reRender(model, poseStack, bufferSource, animatable, renderType, buffer, partialTick, packedLight, packedOverlay, colour);
        this.isReRendering = false;
    }

    @Override
    public void actuallyRender(PoseStack poseStack, LegacySwemHorse animatable, BakedGeoModel model, @Nullable RenderType renderType, MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        poseStack.pushPose();

        LivingEntity livingEntity = animatable instanceof LivingEntity entity ? entity : null;
        boolean shouldSit = animatable.isPassenger() && (animatable.getVehicle() != null);
        float lerpBodyRot = livingEntity == null ? 0 : Mth.rotLerp(partialTick, livingEntity.yBodyRotO, livingEntity.yBodyRot);
        float lerpHeadRot = livingEntity == null ? 0 : Mth.rotLerp(partialTick, livingEntity.yHeadRotO, livingEntity.yHeadRot);
        float netHeadYaw = lerpHeadRot - lerpBodyRot;

        if (shouldSit && animatable.getVehicle() instanceof LivingEntity livingentity) {
            lerpBodyRot = Mth.rotLerp(partialTick, livingentity.yBodyRotO, livingentity.yBodyRot);
            netHeadYaw = lerpHeadRot - lerpBodyRot;
            float clampedHeadYaw = Mth.clamp(Mth.wrapDegrees(netHeadYaw), -85, 85);
            lerpBodyRot = lerpHeadRot - clampedHeadYaw;

            if (clampedHeadYaw * clampedHeadYaw > 2500f)
                lerpBodyRot += clampedHeadYaw * 0.2f;

            netHeadYaw = lerpHeadRot - lerpBodyRot;
        }

        if (animatable.getPose() == Pose.SLEEPING && livingEntity != null) {
            Direction bedDirection = livingEntity.getBedOrientation();

            if (bedDirection != null) {
                float eyePosOffset = livingEntity.getEyeHeight(Pose.STANDING) - 0.1F;

                poseStack.translate(-bedDirection.getStepX() * eyePosOffset, 0, -bedDirection.getStepZ() * eyePosOffset);
            }
        }

        float nativeScale = livingEntity != null ? livingEntity.getScale() : 1;
        float ageInTicks = animatable.tickCount + partialTick;
        float limbSwingAmount = 0;
        float limbSwing = 0;

        poseStack.scale(nativeScale, nativeScale, nativeScale);
        applyRotations(animatable, poseStack, ageInTicks, lerpBodyRot, partialTick, nativeScale);

        if (!shouldSit && animatable.isAlive() && livingEntity != null) {
            limbSwingAmount = livingEntity.walkAnimation.speed(partialTick);
            limbSwing = livingEntity.walkAnimation.position(partialTick);

            if (livingEntity.isBaby())
                limbSwing *= 3f;

            if (limbSwingAmount > 1f)
                limbSwingAmount = 1f;
        }

        if (!isReRender) {
            float headPitch = Mth.lerp(partialTick, animatable.xRotO, animatable.getXRot());
            float motionThreshold = getMotionAnimThreshold(animatable);
            Vec3 velocity = animatable.getDeltaMovement();
            float avgVelocity = (float)((Math.abs(velocity.x) + Math.abs(velocity.z)) / 2f);
            AnimationState<LegacySwemHorse> animationState = new AnimationState<>(animatable, limbSwing, limbSwingAmount, partialTick, avgVelocity >= motionThreshold && limbSwingAmount != 0);
            long instanceId = getInstanceId(animatable);
            GeoModel<LegacySwemHorse> currentModel = getGeoModel();

            animationState.setData(DataTickets.TICK, animatable.getTick(animatable));
            animationState.setData(DataTickets.ENTITY, animatable);
            animationState.setData(DataTickets.ENTITY_MODEL_DATA, new EntityModelData(shouldSit, livingEntity != null && livingEntity.isBaby(), -netHeadYaw, -headPitch));
            currentModel.addAdditionalStateData(animatable, instanceId, animationState::setData);
            currentModel.handleAnimations(animatable, instanceId, animationState, partialTick);
        }

        poseStack.translate(0, 0.01f, 0);

        this.modelRenderTranslations = new Matrix4f(poseStack.last().pose());

        if (buffer != null)
            GeoRendererHack.super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick,
                    packedLight, packedOverlay, colour);

        poseStack.popPose();
    }

    @Override
    public @Nullable ResourceLocation getTextureOverrideForBone(GeoBone bone, LegacySwemHorse currentEntity, float partialTick) {
        if(this.isReRendering) {
            return null;
        }

        String boneName = bone.getName();
        if(boneName.contains(ModelBoneType.WINGS.bone)) {
            ResourceLocation location = this.getTextureLocation(currentEntity);
            if(WING_CACHE.containsKey(location)) {
                bone.setHidden(false);
                return WING_CACHE.get(location);
            } else if(!NO_WING_CACHE.contains(location)) {
                ResourceLocation texture = ResourceLocation.tryParse(location.toString().replace(".png", "_wings.png"));
                if(texture != null && Minecraft.getInstance().getResourceManager().getResource(texture).isPresent()) {
                    WING_CACHE.put(location, texture);
                    return texture;
                } else {
                    NO_WING_CACHE.add(location);
                }
            }

            bone.setHidden(true);

            ItemStack item = currentEntity.getArmor();
            if(!item.isEmpty()) {
                TackItem tackItem = TackItem.as(item);
                if(currentEntity.getArmorTier().getTier() >= HorseArmorTier.AMETHYST.getTier()) {
                    bone.setHidden(false);
                    return tackItem.getTextureOnHorse(HorseModelType.LEGACY, ModelBoneType.WINGS);
                }
            }
            return null;
        } else if(boneName.contains(ModelBoneType.ARMOR.bone)) {
            ItemStack itemStack = currentEntity.getArmor();
            if(!itemStack.isEmpty()) {
                HorseArmorTier tier = currentEntity.getArmorTier();
                ResourceLocation location = this.getTextureLocation(currentEntity);
                if(tier.getTier() >= HorseArmorTier.AMETHYST.getTier() && WING_CACHE.containsKey(location)) {
                    return null;
                }
            }
        }
        return super.getTextureOverrideForBone(bone, currentEntity, partialTick);
    }
}
