package com.evangelix.swemaddendumplus.breeds.breton;

import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntity;
import com.evangelix.swemaddendumplus.abstract_steed.AbstractSteedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;

public class BretonRenderer extends AbstractSteedRenderer {
    public final BretonModelProvider MODEL_PROVIDER = new BretonModelProvider();

    public BretonRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public GeoModel<SWEMHorseEntity> getGeoModel() {
        return this.MODEL_PROVIDER;
    }
}
