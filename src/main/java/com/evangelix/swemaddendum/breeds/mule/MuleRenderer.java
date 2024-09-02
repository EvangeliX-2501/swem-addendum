package com.evangelix.swemaddendum.breeds.mule;

import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntity;
import com.evangelix.swemaddendum.abstract_steed.AbstractSteedRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;


public class MuleRenderer extends AbstractSteedRenderer {
    public final MuleModelProvider MODEL_PROVIDER = new MuleModelProvider();

    public MuleRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public GeoModel<SWEMHorseEntity> getGeoModel() {
        return this.MODEL_PROVIDER;
    }
}
