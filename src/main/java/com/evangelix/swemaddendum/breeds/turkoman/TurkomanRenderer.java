package com.evangelix.swemaddendum.breeds.turkoman;

import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntity;
import com.evangelix.swemaddendum.abstract_steed.AbstractSteedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.model.provider.GeoModelProvider;

public class TurkomanRenderer extends AbstractSteedRenderer {
    public final TurkomanModelProvider MODEL_PROVIDER = new TurkomanModelProvider();

    public TurkomanRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public GeoModelProvider<SWEMHorseEntity> getGeoModelProvider() {
        return this.MODEL_PROVIDER;
    }

}
