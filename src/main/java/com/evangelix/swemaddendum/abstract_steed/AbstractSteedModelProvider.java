package com.evangelix.swemaddendum.abstract_steed;

import com.alaharranhonor.swem.client.horse.model.LegacyHorseModel;
import com.alaharranhonor.swem.entity.horse.LegacySwemHorse;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractSteedModelProvider extends LegacyHorseModel {
    public abstract ResourceLocation getFoalModelLocation();
    public abstract ResourceLocation getAdultModelLocation();

    @Override
    public ResourceLocation getModelResource(LegacySwemHorse swemHorseEntity) {
        return swemHorseEntity.isBaby() ? this.getFoalModelLocation() : this.getAdultModelLocation();
    }

    @Override
    public ResourceLocation getTextureResource(LegacySwemHorse swemHorseEntity) {
        return swemHorseEntity.isBaby() ? ((AbstractSteed)swemHorseEntity).getFoalCoat() : ((AbstractSteed)swemHorseEntity).getCoat();
    }
}
