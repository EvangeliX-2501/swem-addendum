package com.evangelix.swemaddendumplus.breeds.friesian;

import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntityBase;
import com.evangelix.swemaddendumplus.abstract_steed.AbstractSteed;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class Friesian extends AbstractSteed {

    public Friesian(EntityType<? extends SWEMHorseEntityBase> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    public String getFolderName() {
        return "friesian";
    }

    @Override
    public String getFoalFolderName() {
        return "steed_foal";
    }

    public float getAffinityExpMultiplier() {
        return 1.25f;
    }

    public int getMaxSpeedDefault() {
        return 2;
    }

    public int getMaxJumpDefault() {
        return 1;
    }
}

