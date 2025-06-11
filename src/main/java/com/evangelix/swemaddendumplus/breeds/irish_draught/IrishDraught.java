package com.evangelix.swemaddendumplus.breeds.irish_draught;

import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntityBase;
import com.evangelix.swemaddendumplus.abstract_steed.AbstractSteed;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class IrishDraught extends AbstractSteed {
    public IrishDraught(EntityType<? extends SWEMHorseEntityBase> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    public String getFolderName() {
        return "irish_draught";
    }

    @Override
    public String getFoalFolderName() {
        return "steed_foal";
    }

    public float getAffinityExpMultiplier() {
        return 1f;
    }

    public int getMaxSpeedDefault() {
        return 3;
    }

    public int getMaxJumpDefault() {
        return 3;
    }
}
