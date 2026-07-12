package com.evangelix.swemaddendum.breeds.irish_draught;

import com.alaharranhonor.swem.entity.horse.LegacySwemHorse;
import com.evangelix.swemaddendum.abstract_steed.AbstractSteedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;

public class IrishDraughtRenderer extends AbstractSteedRenderer {
    public final IrishDraughtModelProvider MODEL_PROVIDER = new IrishDraughtModelProvider();

    public IrishDraughtRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public GeoModel<LegacySwemHorse> getGeoModel() {
        return this.MODEL_PROVIDER;
    }
}
