package com.evangelix.swemaddendum.breeds.warmblood;

import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntity;
import com.evangelix.swemaddendum.abstract_steed.AbstractSteedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.model.provider.GeoModelProvider;

public class WarmbloodRenderer extends AbstractSteedRenderer {
    public final WarmbloodModelProvider MODEL_PROVIDER = new WarmbloodModelProvider();

    public WarmbloodRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public GeoModelProvider<SWEMHorseEntity> getGeoModelProvider() {
        return this.MODEL_PROVIDER;
    }

}
