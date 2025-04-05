package com.evangelix.swemaddendum.breeds.pegasus;

import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntityBase;
import com.alaharranhonor.swem.forge.entities.horse.coats.BaseCoats;
import com.evangelix.swemaddendum.abstract_steed.AbstractSteed;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class Pegasus extends AbstractSteed {

    public Pegasus(EntityType<? extends SWEMHorseEntityBase> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    public String getFolderName() {
        return "pegasus";
    }

    @Override
    public String getFoalFolderName() {
        return "steed_foal";
    }
}

