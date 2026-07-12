package com.evangelix.swemaddendum.breeds.kladruber;

import com.alaharranhonor.swem.entity.horse.AbstractSwemHorse;
import com.evangelix.swemaddendum.abstract_steed.AbstractSteed;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class Kladruber extends AbstractSteed {

    public Kladruber(EntityType<? extends AbstractSwemHorse> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    public String getFolderName() {
        return "kladruber";
    }

    @Override
    public String getFoalFolderName() {
        return "steed_foal";
    }
}

