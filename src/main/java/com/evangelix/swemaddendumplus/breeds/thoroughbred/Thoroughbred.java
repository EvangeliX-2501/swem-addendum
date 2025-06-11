package com.evangelix.swemaddendumplus.breeds.thoroughbred;

import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntityBase;
import com.evangelix.swemaddendumplus.abstract_steed.AbstractSteed;
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

    public float getAffinityExpMultiplier() {
        return 0.5f;
    }

    public int getMaxSpeedDefault() {
        return 5;
    }

    public int getMaxJumpDefault() {
        return 3;
    }
}

