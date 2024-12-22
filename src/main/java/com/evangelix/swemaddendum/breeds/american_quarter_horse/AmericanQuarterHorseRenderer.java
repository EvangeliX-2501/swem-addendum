package com.evangelix.swemaddendum.breeds.american_quarter_horse;

import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntity;
import com.evangelix.swemaddendum.abstract_steed.AbstractSteedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.model.provider.GeoModelProvider;

public class AmericanQuarterHorseRenderer extends AbstractSteedRenderer {
    public final AmericanQuarterHorseModelProvider MODEL_PROVIDER = new AmericanQuarterHorseModelProvider();

    public AmericanQuarterHorseRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public GeoModelProvider<SWEMHorseEntity> getGeoModelProvider() {
        return this.MODEL_PROVIDER;
    }
}
