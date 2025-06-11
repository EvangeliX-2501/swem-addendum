package com.evangelix.swemaddendumplus.abstract_steed;

import com.alaharranhonor.swem.forge.client.model.SWEMHorseModel;
import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntity;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractSteedModelProvider extends SWEMHorseModel {
    public abstract ResourceLocation getFoalModelLocation();
    public abstract ResourceLocation getAdultModelLocation();

    @Override
    public ResourceLocation getModelResource(SWEMHorseEntity swemHorseEntity) {
        return swemHorseEntity.isBaby() ? this.getFoalModelLocation() : this.getAdultModelLocation();
    }

    @Override
    public ResourceLocation getTextureResource(SWEMHorseEntity swemHorseEntity) {
        return swemHorseEntity.isBaby() ? ((AbstractSteed)swemHorseEntity).getFoalCoat() : ((AbstractSteed)swemHorseEntity).getCoat();
    }
}
