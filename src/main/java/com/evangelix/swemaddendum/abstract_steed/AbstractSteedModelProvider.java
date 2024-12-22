package com.evangelix.swemaddendum.abstract_steed;

import com.alaharranhonor.swem.forge.client.model.SWEMHorseModel;
import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntity;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractSteedModelProvider extends SWEMHorseModel {
    public abstract ResourceLocation getFoalModelLocation();
    public abstract ResourceLocation getAdultModelLocation();

    @Override
    public ResourceLocation getModelLocation(SWEMHorseEntity swemHorseEntity) {
        return swemHorseEntity.isBaby() ? this.getFoalModelLocation() : this.getAdultModelLocation();
    }

    @Override
    public ResourceLocation getTextureLocation(SWEMHorseEntity swemHorseEntity) {
        return swemHorseEntity.isBaby() ? ((AbstractSteed)swemHorseEntity).getFoalTexture() : ((AbstractSteed)swemHorseEntity).getTextureLocation();
    }
}

