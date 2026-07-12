package com.evangelix.swemaddendum.breeds.pegasus;

import com.alaharranhonor.swem.entity.horse.AbstractSwemHorse;
import com.evangelix.swemaddendum.abstract_steed.AbstractSteed;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class Pegasus extends AbstractSteed {

    public Pegasus(EntityType<? extends AbstractSwemHorse> type, Level worldIn) {
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

