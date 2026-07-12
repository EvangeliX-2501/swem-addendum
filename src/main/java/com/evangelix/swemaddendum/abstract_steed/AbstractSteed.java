package com.evangelix.swemaddendum.abstract_steed;

import com.alaharranhonor.swem.entity.horse.AbstractSwemHorse;
import com.alaharranhonor.swem.entity.horse.LegacySwemHorse;
import com.alaharranhonor.swem.entity.horse.behaviors.impl.LineageBehavior;
import com.evangelix.swemaddendum.AddendumNetwork;
import com.evangelix.swemaddendum.SwemAddendumMain;
import com.evangelix.swemaddendum.breeds.donkey.Donkey;
import com.evangelix.swemaddendum.breeds.mule.Mule;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
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
import net.minecraft.world.level.pathfinder.Path;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractSteed extends LegacySwemHorse {
    public static final ResourceLocation MISSING_TEXTURE_LOCATION = ResourceLocation.withDefaultNamespace("missingno");

    public static final EntityDataAccessor<ResourceLocation> COAT = SynchedEntityData.defineId(AbstractSteed.class, SwemAddendumMain.RESOURCE_LOCATION.get());
    public static final EntityDataAccessor<ResourceLocation> FOAL_COAT = SynchedEntityData.defineId(AbstractSteed.class, SwemAddendumMain.RESOURCE_LOCATION.get());


    public abstract String getFolderName();
    public abstract String getFoalFolderName();

    public AbstractSteed(EntityType<? extends AbstractSwemHorse> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    public void onAddedToLevel() {
        super.onAddedToLevel();
        if(!this.level().isClientSide()) {
            int id = this.getId();
            String folderName = this.getFolderName();
            String foalFolderName = this.getFoalFolderName();

            boolean useServerCoats = false;
            if(this.getServer() instanceof DedicatedServer dedicatedServer) {
                MinecraftServer.ServerResourcePackInfo serverResourcePackInfo = dedicatedServer.getProperties().serverResourcePackInfo.orElse(null);
                if(serverResourcePackInfo != null) {
                    useServerCoats = dedicatedServer.isDedicatedServer() && !serverResourcePackInfo.url().equals("");
                }
            }

            if(this.getCoat() == MISSING_TEXTURE_LOCATION) {
                PacketDistributor.sendToPlayersTrackingEntity(this, new AddendumNetwork.TextureRequest(id, folderName, false, useServerCoats));
            }

            if(this.getFoalCoat() == MISSING_TEXTURE_LOCATION) {
                PacketDistributor.sendToPlayersTrackingEntity(this, new AddendumNetwork.TextureRequest(id, foalFolderName, true, useServerCoats));
            }
        }
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new AddendumBreedGoal(this));
    }

    public ResourceLocation getCoat() {
        return this.entityData.get(COAT);
    }

    public void setCoat(ResourceLocation resourceLocation) {
        this.entityData.set(COAT, resourceLocation);
    }

    public ResourceLocation getFoalCoat() {
        return this.entityData.get(FOAL_COAT);
    }

    public void setFoalCoat(ResourceLocation foalTexture) {
        this.entityData.set(FOAL_COAT, foalTexture);
    }

    @Override
    public void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(FOAL_COAT, MISSING_TEXTURE_LOCATION);
        builder.define(COAT, MISSING_TEXTURE_LOCATION);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Texture")) {
            ResourceLocation texture = ResourceLocation.tryParse(compound.getString("Texture"));
            if (texture == null) {
                texture = MISSING_TEXTURE_LOCATION;
            }
            this.setCoat(texture);
        }

        if(compound.contains("FoalTexture")) {
            ResourceLocation foalTexture = ResourceLocation.tryParse(compound.getString("FoalTexture"));
            if(foalTexture == null) {
                foalTexture = MISSING_TEXTURE_LOCATION;
            }
            this.setFoalCoat(foalTexture);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Texture", this.getCoat().toString());
        compound.putString("FoalTexture", this.getFoalCoat().toString());
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn) {
//        ResourceLocation texture = CoatDataRegistrar.MISSING_TEXTURE_LOCATION;
//        if (dataTag != null && dataTag.contains("Texture")) {
//            texture = ResourceLocation.tryParse(dataTag.getString("Texture"));
//            if (texture == null) {
//                texture = CoatDataRegistrar.MISSING_TEXTURE_LOCATION;
//            }
//        }
//        this.setCoat(texture);
//
//        ResourceLocation foalTexture = CoatDataRegistrar.MISSING_TEXTURE_LOCATION;
//        if(dataTag != null && dataTag.contains("FoalTexture")) {
//            foalTexture = ResourceLocation.tryParse(dataTag.getString("FoalTexture"));
//            if(foalTexture == null) {
//                foalTexture = CoatDataRegistrar.MISSING_TEXTURE_LOCATION;
//            }
//        }
//        this.setFoalCoat(foalTexture);
        return super.finalizeSpawn(levelIn, difficultyIn, reason, spawnDataIn);
    }

    @Override
    public boolean canMate(Animal animal) {
        if(animal != this && animal instanceof AbstractSwemHorse swemHorseEntityBase) {
            if(swemHorseEntityBase.getBreeding().isInfertile() || this.getBreeding().isInfertile()) {
                return false; // should prevent a bug where females set in love, then made infertile, still can get pregnant
            }

            Path path = this.navigation.createPath(animal, 1);
            return path != null && path.canReach() && this.getBreeding().canMate(swemHorseEntityBase) && swemHorseEntityBase.getBreeding().canMate(this);
        }
        return false;
    }

    @Override
    public EntityType<? extends AbstractSteed> getType() {
        return (EntityType<? extends AbstractSteed>) super.getType();
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob mob) {
        if(mob instanceof Donkey donkey) {
            EntityType<Mule> foal = SwemAddendumMain.MULE.get();
            Mule muleFoal = foal.create(serverLevel);
            if(muleFoal != null) {
                muleFoal.getCoatBehavior().set(this.getCoatBehavior().coat());
                muleFoal.setGait(Gait.WALK);
                muleFoal.setBred(true);

                LineageBehavior muleFoalLineage = muleFoal.getBehavior(LineageBehavior.class);
                muleFoalLineage.setSire(this.getBreeding().isMale() ? this : donkey);
                muleFoalLineage.setDam(this.getBreeding().isFemale() ? this : donkey);
                this.addOffspring(muleFoal);
                donkey.addOffspring(muleFoal);
            }
            return muleFoal;
        } else if(mob instanceof AbstractSteed abstractSteed){
            EntityType<? extends AbstractSteed> foal;
            AbstractSteed inheritor = this.random.nextBoolean() ? this : abstractSteed;
            foal = inheritor.getType();
            AbstractSteed steedFoal = foal.create(serverLevel);
            if(steedFoal != null) {
                steedFoal.getCoatBehavior().set(this.getCoatBehavior().coat());
                steedFoal.setGait(Gait.WALK);
                steedFoal.setBred(true);

                LineageBehavior muleFoalLineage = steedFoal.getBehavior(LineageBehavior.class);
                muleFoalLineage.setSire(this.getBreeding().isMale() ? this : abstractSteed);
                muleFoalLineage.setDam(this.getBreeding().isFemale() ? this : abstractSteed);
                this.addOffspring(steedFoal);
                abstractSteed.addOffspring(steedFoal);
            }
            return steedFoal;
        }
        return null;
    }
}
