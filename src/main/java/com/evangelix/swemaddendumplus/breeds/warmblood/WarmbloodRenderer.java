package com.evangelix.swemaddendumplus.breeds.warmblood;

import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntity;
import com.evangelix.swemaddendumplus.abstract_steed.AbstractSteedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;

public class WarmbloodRenderer extends AbstractSteedRenderer {
    public final WarmbloodModelProvider MODEL_PROVIDER = new WarmbloodModelProvider();

    public WarmbloodRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public GeoModel<SWEMHorseEntity> getGeoModel() {
        return this.MODEL_PROVIDER;
    }

}
