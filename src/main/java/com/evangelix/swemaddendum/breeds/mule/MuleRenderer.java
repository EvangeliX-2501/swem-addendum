package com.evangelix.swemaddendum.breeds.mule;

import com.alaharranhonor.swem.entity.horse.LegacySwemHorse;
import com.evangelix.swemaddendum.abstract_steed.AbstractSteedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;


public class MuleRenderer extends AbstractSteedRenderer {
    public final MuleModelProvider MODEL_PROVIDER = new MuleModelProvider();

    public MuleRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public GeoModel<LegacySwemHorse> getGeoModel() {
        return this.MODEL_PROVIDER;
    }
}
