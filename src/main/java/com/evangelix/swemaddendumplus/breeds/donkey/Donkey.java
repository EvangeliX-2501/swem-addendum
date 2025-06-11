package com.evangelix.swemaddendumplus.breeds.donkey;

import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntity;
import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntityBase;
import com.alaharranhonor.swem.forge.entities.horse.behaviors.impl.LineageBehavior;
import com.evangelix.swemaddendumplus.SwemAddendumPlusMain;
import com.evangelix.swemaddendumplus.abstract_steed.AbstractSteed;
import com.evangelix.swemaddendumplus.breeds.mule.Mule;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

public class Donkey extends AbstractSteed {

    public Donkey(EntityType<? extends SWEMHorseEntityBase> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    public void positionRider(Entity entity, Entity.MoveFunction moveFunction) {
        if (this.hasPassenger(entity)) {

            double offsetX = 0;
            double offsetY = 1.3;
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
    public boolean canMate(Animal animal) {
        return animal instanceof SWEMHorseEntity;
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob mob) {
        if(mob instanceof Donkey donkey) {
            EntityType<? extends AbstractSteed> foal;

            AbstractSteed inheritor = this.random.nextBoolean() ? this : donkey;
            foal = inheritor.getType();
            AbstractSteed steedFoal = foal.create(serverLevel);
            if(steedFoal != null) {
                steedFoal.getCoatBehavior().set(this.getCoatBehavior().coat());
                steedFoal.setGait(Gait.WALK);
                steedFoal.setBred(true);

                LineageBehavior muleFoalLineage = steedFoal.getBehavior(LineageBehavior.class);
                muleFoalLineage.setSire(this.getBreeding().isMale() ? this : donkey);
                muleFoalLineage.setDam(this.getBreeding().isFemale() ? this : donkey);
                this.addOffspring(steedFoal);
                donkey.addOffspring(steedFoal);
            }
            return steedFoal;
        } else if(mob instanceof AbstractSteed abstractSteed) {
            EntityType<Mule> foal = SwemAddendumPlusMain.MULE.get();
            Mule muleFoal = foal.create(serverLevel);
            if(muleFoal != null) {
                muleFoal.getCoatBehavior().set(this.getCoatBehavior().coat());
                muleFoal.setGait(Gait.WALK);
                muleFoal.setBred(true);

                LineageBehavior muleFoalLineage = muleFoal.getBehavior(LineageBehavior.class);
                muleFoalLineage.setSire(this.getBreeding().isMale() ? this : abstractSteed);
                muleFoalLineage.setDam(this.getBreeding().isFemale() ? this : abstractSteed);
                this.addOffspring(muleFoal);
                abstractSteed.addOffspring(muleFoal);
            }
            return muleFoal;
        }
        return null;
    }

    @Override
    public String getFolderName() {
        return "donkey";
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
        return 2;
    }
}

