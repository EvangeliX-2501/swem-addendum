package com.evangelix.swemaddendum.breeds.shire;

import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntityBase;
import com.evangelix.swemaddendum.abstract_steed.AbstractSteed;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class Shire extends AbstractSteed {

    public Shire(EntityType<? extends SWEMHorseEntityBase> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    public String getFolderName() {
        return "shire";
    }

    @Override
    public String getFoalFolderName() {
        return "steed_foal";
    }
}

