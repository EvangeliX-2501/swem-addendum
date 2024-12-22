package com.evangelix.swemaddendum.breeds.irish_draught;

import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntity;
import com.evangelix.swemaddendum.abstract_steed.AbstractSteedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.model.provider.GeoModelProvider;

public class IrishDraughtRenderer extends AbstractSteedRenderer {
    public final IrishDraughtModelProvider MODEL_PROVIDER = new IrishDraughtModelProvider();

    public IrishDraughtRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public GeoModelProvider<SWEMHorseEntity> getGeoModelProvider() {
        return this.MODEL_PROVIDER;
    }
}
