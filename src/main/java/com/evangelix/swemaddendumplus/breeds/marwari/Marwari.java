package com.evangelix.swemaddendumplus.breeds.marwari;

import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntityBase;
import com.evangelix.swemaddendumplus.abstract_steed.AbstractSteed;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class Marwari extends AbstractSteed {

    public Marwari(EntityType<? extends SWEMHorseEntityBase> type, Level worldIn) {
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

    public float getAffinityExpMultiplier() {
        return 0.5f;
    }

    public int getMaxSpeedDefault() {
        return 4;
    }

    public int getMaxJumpDefault() {
        return 3;
    }
}

