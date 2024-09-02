package com.evangelix.swemaddendum.breeds.fjord;

import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntity;
import com.evangelix.swemaddendum.abstract_steed.AbstractSteedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;

public class FjordRenderer extends AbstractSteedRenderer {
    public final FjordModelProvider MODEL_PROVIDER = new FjordModelProvider();

    public FjordRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public GeoModel<SWEMHorseEntity> getGeoModel() {
        return this.MODEL_PROVIDER;
    }

}
