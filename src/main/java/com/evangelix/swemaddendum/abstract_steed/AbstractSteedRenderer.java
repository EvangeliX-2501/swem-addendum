package com.evangelix.swemaddendum.abstract_steed;

import com.alaharranhonor.swem.forge.client.render.SWEMHorseRenderer;
import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntity;
import com.alaharranhonor.swem.forge.items.SWEMHorseArmorItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.provider.GeoModelProvider;

import java.util.*;

public abstract class AbstractSteedRenderer extends SWEMHorseRenderer {

    public static final Map<ResourceLocation, ResourceLocation> WING_CACHE = new HashMap<>();
    public static final Set<ResourceLocation> NO_WING_CACHE = new HashSet<>();


    public AbstractSteedRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public abstract GeoModelProvider<SWEMHorseEntity> getGeoModelProvider();

    @Override
    public ResourceLocation getTextureLocation(SWEMHorseEntity animatable) {
        return this.getGeoModelProvider().getTextureLocation(animatable);
    }

    public void replicateBoneStructure(List<GeoBone> geoBones, GeoModel geoModel) {
        for(GeoBone geoBone : geoBones) {
            geoModel.getBone(geoBone.name).ifPresent(bone -> {
                bone.setRotation(geoBone.getRotation());
                bone.setPosition(geoBone.getPosition());
                bone.setScale(geoBone.getScale());
            });

            if(!geoBone.childBones.isEmpty()) {
                this.replicateBoneStructure(geoBone.childBones, geoModel);
            }
        }
    }

    @Override
    public void render(GeoModel model, SWEMHorseEntity animatable, float partialTick, RenderType type, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        GeoModel geoModel = this.getGeoModelProvider().getModel(this.getGeoModelProvider().getModelLocation(animatable));
        this.replicateBoneStructure(model.topLevelBones, geoModel);
        super.render(geoModel, animatable, partialTick, type, poseStack, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public @Nullable ResourceLocation getTextureForBone(String boneName, SWEMHorseEntity currentEntity) {
        if(boneName.contains("Wings")) {
            ItemStack stack = currentEntity.getSWEMArmor();
            ResourceLocation location = this.getTextureLocation(currentEntity);
            if(!stack.isEmpty()) {
                SWEMHorseArmorItem armorItem = (SWEMHorseArmorItem)stack.getItem();
                if(armorItem.tier.equals(SWEMHorseArmorItem.HorseArmorTier.AMETHYST)) {
                    if(WING_CACHE.containsKey(location)) {
                        return WING_CACHE.get(location);
                    } else if(!NO_WING_CACHE.contains(location)) {
                        String locationString = location.toString();
                        locationString = locationString.replace(".png", "_wings.png");
                        ResourceLocation texture = ResourceLocation.tryParse(locationString);

                        if (texture != null && Minecraft.getInstance().getResourceManager().hasResource(texture)) {
                            WING_CACHE.put(location, texture);
                            return texture;
                        } else {
                            NO_WING_CACHE.add(location);
                        }
                    }
                }
            }
        }
        return super.getTextureForBone(boneName, currentEntity);
    }
}
