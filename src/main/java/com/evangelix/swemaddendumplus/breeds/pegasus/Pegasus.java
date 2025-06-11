package com.evangelix.swemaddendumplus.breeds.pegasus;

import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntityBase;
import com.evangelix.swemaddendumplus.abstract_steed.AbstractSteed;
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

    @Override
    public float getAffinityExpMultiplier() {
        return 1.0f;
    }

    @Override
    public int getMaxSpeedDefault() {
        return 4;
    }

    @Override
    public int getMaxJumpDefault() {
        return 4;
    }
}

