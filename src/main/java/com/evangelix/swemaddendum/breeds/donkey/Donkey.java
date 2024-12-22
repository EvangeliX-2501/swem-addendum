package com.evangelix.swemaddendum.breeds.donkey;

import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntity;
import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntityBase;
import com.alaharranhonor.swem.forge.entities.horse.gender.FemaleHorseGender;
import com.alaharranhonor.swem.forge.entities.horse.gender.MaleHorseGender;
import com.evangelix.swemaddendum.SwemAddendumMain;
import com.evangelix.swemaddendum.abstract_steed.AbstractSteed;
import com.evangelix.swemaddendum.breeds.mule.Mule;
import com.mojang.authlib.GameProfile;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class Donkey extends AbstractSteed {

    public Donkey(EntityType<? extends SWEMHorseEntityBase> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    public void positionRider(Entity entity) {
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
            EntityType<Donkey> foal = SwemAddendumMain.DONKEY.get();

            Donkey inheritor = this.random.nextBoolean() ? this : donkey;
            ResourceLocation coat = inheritor.getTextureLocation();

            AbstractSteed donkeyFoal = foal.create(level);
            if(donkeyFoal != null) {
                donkeyFoal.setTextureLocation(coat);
                donkeyFoal.setCoatColour(this.getCoatColor());

                HorseSpeed oldSpeed = donkeyFoal.currentSpeed;
                donkeyFoal.currentSpeed = HorseSpeed.WALK;
                donkeyFoal.updateSelectedSpeed(oldSpeed);

                donkeyFoal.setFather(this.getGender() instanceof MaleHorseGender ? this : donkey);
                donkeyFoal.setMother(this.getGender() instanceof FemaleHorseGender ? this : donkey);

                donkeyFoal.changeGender();
                if(this.random.nextBoolean()) {
                    donkeyFoal.changeGender();
                }

                if(this.isTamed()) {
                    Optional<GameProfile> optional =  this.level.getServer().getProfileCache().get(this.getOwnerUUID());
                    optional.ifPresent(donkeyFoal::tameWithName);
                }
            }
            return donkeyFoal;
        } else if(mob instanceof AbstractSteed abstractSteed) {
            EntityType<Mule> foal = SwemAddendumMain.MULE.get();
            Mule muleFoal = foal.create(level);
            if(muleFoal != null) {
                muleFoal.setCoatColour(this.getCoatColor());

                HorseSpeed oldSpeed = muleFoal.currentSpeed;
                muleFoal.currentSpeed = HorseSpeed.WALK;
                muleFoal.updateSelectedSpeed(oldSpeed);

                muleFoal.setFather(this.getGender() instanceof MaleHorseGender ? this : abstractSteed);
                muleFoal.setMother(this.getGender() instanceof FemaleHorseGender ? this : abstractSteed);

                muleFoal.changeGender();
                if(this.random.nextBoolean()) {
                    muleFoal.changeGender();
                }

                if(this.isTamed()) {
                    Optional<GameProfile> optional =  this.level.getServer().getProfileCache().get(this.getOwnerUUID());
                    optional.ifPresent(muleFoal::tameWithName);
                }
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
}

