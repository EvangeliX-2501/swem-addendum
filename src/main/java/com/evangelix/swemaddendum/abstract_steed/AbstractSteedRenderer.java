package com.evangelix.swemaddendum.abstract_steed;

import com.alaharranhonor.swem.forge.client.model.ModelBoneType;
import com.alaharranhonor.swem.forge.client.render.SWEMHorseRenderer;
import com.alaharranhonor.swem.forge.entities.horse.HorseModelType;
import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntity;
import com.alaharranhonor.swem.forge.items.tack.HorseArmorTier;
import com.alaharranhonor.swem.forge.items.tack.TackItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class AbstractSteedRenderer extends SWEMHorseRenderer implements GeoRendererHack<SWEMHorseEntity> {

    public static final Map<ResourceLocation, ResourceLocation> WING_CACHE = new HashMap<>();
    public static final Set<ResourceLocation> NO_WING_CACHE = new HashSet<>();

    public boolean isReRendering = false;


    public AbstractSteedRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public abstract GeoModel<SWEMHorseEntity> getGeoModel();

    @Override
    public ResourceLocation getTextureLocation(SWEMHorseEntity animatable) {
        return this.isReRendering ? null : this.getGeoModel().getTextureResource(animatable);
    }

    @Override
    public void updateAnimatedTextureFrame(SWEMHorseEntity animatable) {
        if(!this.isReRendering) {
            super.updateAnimatedTextureFrame(animatable);
        }
    }

    @Override
    public void reRender(BakedGeoModel model, PoseStack poseStack, MultiBufferSource bufferSource, SWEMHorseEntity animatable, RenderType renderType, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.isReRendering = true;
        super.reRender(model, poseStack, bufferSource, animatable, renderType, buffer, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        this.isReRendering = false;
    }

    @Override
    public void actuallyRender(PoseStack poseStack, SWEMHorseEntity animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.pushPose();
        boolean shouldSit = animatable.isPassenger() && animatable.getVehicle() != null && animatable.getVehicle().shouldRiderSit();
        float lerpBodyRot = Mth.rotLerp(partialTick, animatable.yBodyRotO, animatable.yBodyRot);
        float lerpHeadRot = Mth.rotLerp(partialTick, animatable.yHeadRotO, animatable.yHeadRot);
        float netHeadYaw = lerpHeadRot - lerpBodyRot;
        float limbSwingAmount;
        if (shouldSit) {
            Entity vehicle = animatable.getVehicle();
            if (vehicle instanceof LivingEntity livingentity) {
                lerpBodyRot = Mth.rotLerp(partialTick, livingentity.yBodyRotO, livingentity.yBodyRot);
                netHeadYaw = lerpHeadRot - lerpBodyRot;
                limbSwingAmount = Mth.clamp(Mth.wrapDegrees(netHeadYaw), -85.0F, 85.0F);
                lerpBodyRot = lerpHeadRot - limbSwingAmount;
                if (limbSwingAmount * limbSwingAmount > 2500.0F) {
                    lerpBodyRot += limbSwingAmount * 0.2F;
                }

                netHeadYaw = lerpHeadRot - lerpBodyRot;
            }
        }

        if (animatable.getPose() == Pose.SLEEPING) {
            Direction bedDirection = animatable.getBedOrientation();
            if (bedDirection != null) {
                limbSwingAmount = animatable.getEyeHeight(Pose.STANDING) - 0.1F;
                poseStack.translate((float)(-bedDirection.getStepX()) * limbSwingAmount, 0.0F, (float)(-bedDirection.getStepZ()) * limbSwingAmount);
            }
        }

        float ageInTicks = (float)animatable.tickCount + partialTick;
        limbSwingAmount = 0.0F;
        float limbSwing = 0.0F;
        this.applyRotations(animatable, poseStack, ageInTicks, lerpBodyRot, partialTick);
        if (!shouldSit && animatable.isAlive()) {
            limbSwingAmount = animatable.walkAnimation.speed(partialTick);
            limbSwing = animatable.walkAnimation.position(partialTick);
            if (((LivingEntity) animatable).isBaby()) {
                limbSwing *= 3.0F;
            }

            if (limbSwingAmount > 1.0F) {
                limbSwingAmount = 1.0F;
            }
        }

        if (!isReRender) {
            float headPitch = Mth.lerp(partialTick, animatable.xRotO, animatable.getXRot());
            float motionThreshold = this.getMotionAnimThreshold(animatable);
            Vec3 velocity = animatable.getDeltaMovement();
            float avgVelocity = (float)(Math.abs(velocity.x) + Math.abs(velocity.z)) / 2.0F;
            AnimationState<SWEMHorseEntity> animationState = new AnimationState<>(animatable, limbSwing, limbSwingAmount, partialTick, avgVelocity >= motionThreshold && limbSwingAmount != 0.0F);
            long instanceId = this.getInstanceId(animatable);
            animationState.setData(DataTickets.TICK, ((GeoAnimatable)animatable).getTick(animatable));
            animationState.setData(DataTickets.ENTITY, animatable);
            animationState.setData(DataTickets.ENTITY_MODEL_DATA, new EntityModelData(shouldSit, ((LivingEntity) animatable).isBaby(), -netHeadYaw, -headPitch));
            this.getGeoModel().addAdditionalStateData(animatable, instanceId, animationState::setData);
            this.getGeoModel().handleAnimations(animatable, instanceId, animationState);
        }

        poseStack.translate(0.0F, 0.01F, 0.0F);
        this.modelRenderTranslations = new Matrix4f(poseStack.last().pose());
        if (animatable.isInvisibleTo(Minecraft.getInstance().player)) {
            if (Minecraft.getInstance().shouldEntityAppearGlowing(animatable)) {
                buffer = bufferSource.getBuffer(renderType = RenderType.outline(this.getTextureLocation(animatable)));
            } else {
                renderType = null;
            }
        }

        if (renderType != null) {
            // Im not learning mixins for this nonsense, so here we are
            GeoRendererHack.super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        }

        poseStack.popPose();
    }

    @Override
    public @Nullable ResourceLocation getTextureOverrideForBone(GeoBone bone, SWEMHorseEntity currentEntity, float partialTick) {
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
