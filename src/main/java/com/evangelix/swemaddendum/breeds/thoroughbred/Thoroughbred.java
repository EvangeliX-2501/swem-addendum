package com.evangelix.swemaddendum.breeds.thoroughbred;

import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntityBase;
import com.evangelix.swemaddendum.abstract_steed.AbstractSteed;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class Thoroughbred extends AbstractSteed {

    public Thoroughbred(EntityType<? extends SWEMHorseEntityBase> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    public String getFolderName() {
        return "thoroughbred";
    }

    @Override
    public String getFoalFolderName() {
        return "steed_foal";
    }
}
