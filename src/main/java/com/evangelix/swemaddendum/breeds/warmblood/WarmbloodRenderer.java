package com.evangelix.swemaddendum.breeds.warmblood;

import com.alaharranhonor.swem.entity.horse.LegacySwemHorse;
import com.evangelix.swemaddendum.abstract_steed.AbstractSteedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;

public class WarmbloodRenderer extends AbstractSteedRenderer {
    public final WarmbloodModelProvider MODEL_PROVIDER = new WarmbloodModelProvider();

    public WarmbloodRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public GeoModel<LegacySwemHorse> getGeoModel() {
        return this.MODEL_PROVIDER;
    }

}
