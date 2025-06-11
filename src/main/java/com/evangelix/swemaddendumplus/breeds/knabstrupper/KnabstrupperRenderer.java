package com.evangelix.swemaddendumplus.breeds.knabstrupper;

import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntity;
import com.evangelix.swemaddendumplus.abstract_steed.AbstractSteedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;

public class KnabstrupperRenderer extends AbstractSteedRenderer {
    public final KnabstrupperModelProvider MODEL_PROVIDER = new KnabstrupperModelProvider();

    public KnabstrupperRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public GeoModel<SWEMHorseEntity> getGeoModel() {
        return this.MODEL_PROVIDER;
    }

}
