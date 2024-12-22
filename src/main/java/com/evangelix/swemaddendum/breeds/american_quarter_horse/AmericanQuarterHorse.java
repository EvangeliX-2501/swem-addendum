package com.evangelix.swemaddendum.breeds.american_quarter_horse;

import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntityBase;
import com.evangelix.swemaddendum.abstract_steed.AbstractSteed;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class AmericanQuarterHorse extends AbstractSteed {

    public AmericanQuarterHorse(EntityType<? extends SWEMHorseEntityBase> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    public void positionRider(Entity entity) {
        if (this.hasPassenger(entity)) {

            double offsetX = 0;
            double offsetY = 1.35;
            double offsetZ = 0;

            double radYaw = Math.toRadians(this.getYRot());

            double offsetXRotated = offsetX * Math.cos(radYaw) - offsetZ * Math.sin(radYaw);
            double offsetYRotated = offsetY;
            double offsetZRotated = offsetX * Math.sin(radYaw) + offsetZ * Math.cos(radYaw);

            double x = this.getX() + offsetXRotated;
            double y = this.getY() + offsetYRotated;
            double z = this.getZ() + offsetZRotated;

            entity.setPos(x, y, z);
        }
    }

    @Override
    public String getFolderName() {
        return "american_quarter_horse";
    }

    @Override
    public String getFoalFolderName() {
        return "steed_foal";
    }
}

