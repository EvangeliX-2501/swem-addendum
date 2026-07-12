package com.evangelix.swemaddendum.breeds.thoroughbred;

import com.alaharranhonor.swem.entity.horse.LegacySwemHorse;
import com.evangelix.swemaddendum.abstract_steed.AbstractSteedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;

public class ThoroughbredRenderer extends AbstractSteedRenderer {
    public final ThoroughbredModelProvider MODEL_PROVIDER = new ThoroughbredModelProvider();

    public ThoroughbredRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public GeoModel<LegacySwemHorse> getGeoModel() {
        return this.MODEL_PROVIDER;
    }

}
