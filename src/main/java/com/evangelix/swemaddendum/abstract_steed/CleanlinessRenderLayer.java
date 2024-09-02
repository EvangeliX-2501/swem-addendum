package com.evangelix.swemaddendum.abstract_steed;

import com.alaharranhonor.swem.forge.client.SWEMRenderTypes;
import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntity;
import com.evangelix.swemaddendum.CoatDataRegistrar;
import com.evangelix.swemaddendum.SwemAddendumMain;
import com.evangelix.swemaddendum.TraitRegistrar;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ScreenEvent;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

import java.util.function.BiFunction;

public class CleanlinessRenderLayer extends GeoRenderLayer<SWEMHorseEntity> {
    public static final ResourceLocation GRIMY = new ResourceLocation(SwemAddendumMain.MODID, "textures/cleanliness_overlay/grimy.png");
    public static final ResourceLocation DIRTY = new ResourceLocation(SwemAddendumMain.MODID, "textures/cleanliness_overlay/dirty.png");
    public static final ResourceLocation FILTHY = new ResourceLocation(SwemAddendumMain.MODID, "textures/cleanliness_overlay/filthy.png");

    public CleanlinessRenderLayer(AbstractSteedRenderer abstractSteedRenderer) {
        super(abstractSteedRenderer);
    }

    @Override
    public void render(PoseStack poseStack, SWEMHorseEntity animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        if (((AbstractSteed) animatable).getCleanliness() == TraitRegistrar.Cleanliness.GRIMY) {
            RenderType cleanlinessRenderType = SWEMRenderTypes.horseArmor(GRIMY, true);
            this.getRenderer().reRender(this.getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, cleanlinessRenderType, bufferSource.getBuffer(cleanlinessRenderType), partialTick, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        } else if (((AbstractSteed) animatable).getCleanliness() == TraitRegistrar.Cleanliness.DIRTY) {
            RenderType cleanlinessRenderType = SWEMRenderTypes.horseArmor(DIRTY, true);
            this.getRenderer().reRender(this.getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, cleanlinessRenderType, bufferSource.getBuffer(cleanlinessRenderType), partialTick, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        } else if (((AbstractSteed) animatable).getCleanliness() == TraitRegistrar.Cleanliness.FILTHY) {
            RenderType cleanlinessRenderType = SWEMRenderTypes.horseArmor(FILTHY, true);
            this.getRenderer().reRender(this.getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, cleanlinessRenderType, bufferSource.getBuffer(cleanlinessRenderType), partialTick, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        }
    }
}
