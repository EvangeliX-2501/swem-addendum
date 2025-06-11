package com.evangelix.swemaddendumplus.breeds.mule;

import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntityBase;
import com.evangelix.swemaddendumplus.abstract_steed.AbstractSteed;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

public class Mule extends AbstractSteed {

    public Mule(EntityType<? extends SWEMHorseEntityBase> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    public void positionRider(Entity entity, Entity.MoveFunction moveFunction) {
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
        return "mule";
    }

    @Override
    public String getFoalFolderName() {
        return "steed_foal";
    }

    public boolean canMate(Animal animal) {
        return false;
    }

    public float getAffinityExpMultiplier() {
        return 1f;
    }

    public int getMaxSpeedDefault() {
        return 2;
    }

    public int getMaxJumpDefault() {
        return 2;
    }
}

