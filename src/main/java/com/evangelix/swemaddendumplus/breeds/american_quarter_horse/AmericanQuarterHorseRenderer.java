package com.evangelix.swemaddendumplus.breeds.american_quarter_horse;

import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntity;
import com.evangelix.swemaddendumplus.abstract_steed.AbstractSteedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;

public class AmericanQuarterHorseRenderer extends AbstractSteedRenderer {
    public final AmericanQuarterHorseModelProvider MODEL_PROVIDER = new AmericanQuarterHorseModelProvider();

    public AmericanQuarterHorseRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public GeoModel<SWEMHorseEntity> getGeoModel() {
        return this.MODEL_PROVIDER;
    }
}
