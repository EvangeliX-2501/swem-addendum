package com.evangelix.swemaddendum.abstract_steed;

import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntity;
import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntityBase;
import com.alaharranhonor.swem.forge.entities.horse.coats.SWEMCoatColor;
import com.alaharranhonor.swem.forge.entities.horse.gender.FemaleHorseGender;
import com.alaharranhonor.swem.forge.entities.horse.gender.MaleHorseGender;
import com.evangelix.swemaddendum.AddendumNetwork;
import com.evangelix.swemaddendum.SwemAddendumMain;
import com.evangelix.swemaddendum.breeds.donkey.Donkey;
import com.evangelix.swemaddendum.breeds.mule.Mule;
import com.mojang.authlib.GameProfile;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class AbstractSteed extends SWEMHorseEntity {

    public abstract String getFolderName();
    public abstract String getFoalFolderName();

    public AbstractSteed(EntityType<? extends SWEMHorseEntityBase> type, Level worldIn) {
        super(type, worldIn);
        CoatDataRegistrar.apply(this);
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        if(!this.level.isClientSide()) {
            int id = this.getId();
            String folderName = this.getFolderName();
            String foalFolderName = this.getFoalFolderName();

            boolean useServerCoats = false;
            if(this.getServer() instanceof DedicatedServer dedicatedServer) {
                useServerCoats = dedicatedServer.isDedicatedServer() &&
                        !dedicatedServer.getProperties().resourcePack.equals("");
            }

            if(this.getTextureLocation() == CoatDataRegistrar.MISSING_TEXTURE_LOCATION) {
                AddendumNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> this),
                        new AddendumNetwork.TextureGenRequest(id, folderName, false, useServerCoats));
            }

            if(this.getFoalTexture() == CoatDataRegistrar.MISSING_TEXTURE_LOCATION) {
                AddendumNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> this),
                        new AddendumNetwork.TextureGenRequest(id, foalFolderName, true, useServerCoats));
            }
        }
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new AddendumBreedGoal(this));
    }

    public void setPegasus() {
        this.setCoatColour(SWEMCoatColor.SWIFT_WIND_SHE_RA);
    }

    public void setTextureLocation(ResourceLocation resourceLocation) {
        this.entityData.set(CoatDataRegistrar.TEXTURE, resourceLocation);
    }

    public ResourceLocation getTextureLocation() {
        return this.entityData.get(CoatDataRegistrar.TEXTURE);
    }

    public void setFoalTexture(ResourceLocation foalTexture) {
        this.entityData.set(CoatDataRegistrar.FOAL_TEXTURE, foalTexture);
    }

    public ResourceLocation getFoalTexture() {
        return this.entityData.get(CoatDataRegistrar.FOAL_TEXTURE);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Texture")) {
            ResourceLocation texture = ResourceLocation.tryParse(tag.getString("Texture"));
            if(texture == null) {
                texture = CoatDataRegistrar.MISSING_TEXTURE_LOCATION;
            }
            this.setTextureLocation(texture);
            this.setPegasus();
        }

        if(tag.contains("FoalTexture")) {
            ResourceLocation foalTexture = ResourceLocation.tryParse(tag.getString("FoalTexture"));
            if(foalTexture == null) {
                foalTexture = CoatDataRegistrar.MISSING_TEXTURE_LOCATION;
            }
            this.setFoalTexture(foalTexture);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("Texture", this.getTextureLocation().toString());
        tag.putString("FoalTexture", this.getFoalTexture().toString());
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        ResourceLocation texture;
        if(dataTag != null) {
            if(dataTag.contains("Texture")) {
                texture = ResourceLocation.tryParse(dataTag.getString("Texture"));
                if(texture == null) {
                    texture = CoatDataRegistrar.MISSING_TEXTURE_LOCATION;
                }
                this.setTextureLocation(texture);
            }

            if(dataTag.contains("FoalTexture")) {
                ResourceLocation foalTexture = ResourceLocation.tryParse(dataTag.getString("FoalTexture"));
                if(foalTexture == null) {
                    foalTexture = CoatDataRegistrar.MISSING_TEXTURE_LOCATION;
                }
                this.setFoalTexture(foalTexture);
            }
        }

        SpawnGroupData data = super.finalizeSpawn(levelIn, difficultyIn, reason, spawnDataIn, dataTag);
        this.setPegasus();
        return data;
    }

    @Override
    public boolean canMate(Animal animal) {
        return animal instanceof SWEMHorseEntity;
    }

    @Override
    public EntityType<? extends AbstractSteed> getType() {
        return (EntityType<? extends AbstractSteed>) super.getType();
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob mob) {
        if(mob instanceof Donkey donkey) {
            EntityType<Mule> foal = SwemAddendumMain.MULE.get();
            Mule muleFoal = foal.create(level);
            if(muleFoal != null) {
                muleFoal.setCoatColour(this.getCoatColor());

                HorseSpeed oldSpeed = muleFoal.currentSpeed;
                muleFoal.currentSpeed = HorseSpeed.WALK;
                muleFoal.updateSelectedSpeed(oldSpeed);

                muleFoal.setFather(this.getGender() instanceof MaleHorseGender ? this : donkey);
                muleFoal.setMother(this.getGender() instanceof FemaleHorseGender ? this : donkey);

                muleFoal.changeGender();
                if(this.random.nextBoolean()) {
                    muleFoal.changeGender();
                }

                if(this.isTamed()) {
                    Optional<GameProfile> optional = this.level.getServer().getProfileCache().get(this.getOwnerUUID());
                    optional.ifPresent(muleFoal::tameWithName);
                }
            }
            return muleFoal;
        } else if(mob instanceof AbstractSteed abstractSteed) {
            EntityType<? extends AbstractSteed> foal;
            ResourceLocation texture;

            AbstractSteed inheritor = this.random.nextBoolean() ? this : abstractSteed;
            foal = inheritor.getType();
            texture = inheritor.getTextureLocation();

            AbstractSteed steedFoal = foal.create(level);
            if(steedFoal != null) {
                steedFoal.setTextureLocation(texture);
                steedFoal.setCoatColour(this.getCoatColor());

                HorseSpeed oldSpeed = steedFoal.currentSpeed;
                steedFoal.currentSpeed = HorseSpeed.WALK;
                steedFoal.updateSelectedSpeed(oldSpeed);

                steedFoal.setFather(this.getGender() instanceof MaleHorseGender ? this : abstractSteed);
                steedFoal.setMother(this.getGender() instanceof FemaleHorseGender ? this : abstractSteed);

                steedFoal.changeGender();
                if(this.random.nextBoolean()) {
                    steedFoal.changeGender();
                }

                if(this.isTamed()) {
                    Optional<GameProfile> optional =  this.level.getServer().getProfileCache().get(this.getOwnerUUID());
                    optional.ifPresent(steedFoal::tameWithName);
                }
            }
            return steedFoal;
        }
        return null;
    }
}
