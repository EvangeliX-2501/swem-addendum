package com.evangelix.swemaddendum.breeds.irish_draught;

import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntityBase;
import com.alaharranhonor.swem.forge.entities.horse.coats.BaseCoats;
import com.evangelix.swemaddendum.abstract_steed.AbstractSteed;
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
}
