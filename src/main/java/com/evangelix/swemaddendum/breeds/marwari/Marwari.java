package com.evangelix.swemaddendum.breeds.marwari;

import com.alaharranhonor.swem.entity.horse.AbstractSwemHorse;
import com.evangelix.swemaddendum.abstract_steed.AbstractSteed;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class Marwari extends AbstractSteed {

    public Marwari(EntityType<? extends AbstractSwemHorse> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    public String getFolderName() {
        return "marwari";
    }

    @Override
    public String getFoalFolderName() {
        return "steed_foal";
    }
}

