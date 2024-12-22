package com.evangelix.swemaddendum.breeds.donkey;

import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntity;
import com.evangelix.swemaddendum.abstract_steed.AbstractSteedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.model.provider.GeoModelProvider;

public class DonkeyRenderer extends AbstractSteedRenderer {
    public final DonkeyModelProvider MODEL_PROVIDER = new DonkeyModelProvider();

    public DonkeyRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public GeoModelProvider<SWEMHorseEntity> getGeoModelProvider() {
        return this.MODEL_PROVIDER;
    }
}
