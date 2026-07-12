package com.evangelix.swemaddendum.breeds.kladruber;

import com.alaharranhonor.swem.entity.horse.LegacySwemHorse;
import com.evangelix.swemaddendum.abstract_steed.AbstractSteedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;

public class KladruberRenderer extends AbstractSteedRenderer {
    public final KladruberModelProvider MODEL_PROVIDER = new KladruberModelProvider();

    public KladruberRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public GeoModel<LegacySwemHorse> getGeoModel() {
        return this.MODEL_PROVIDER;
    }

}
