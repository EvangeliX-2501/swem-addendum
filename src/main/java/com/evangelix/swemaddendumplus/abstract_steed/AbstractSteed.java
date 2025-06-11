package com.evangelix.swemaddendumplus.abstract_steed;

import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntity;
import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntityBase;
import com.alaharranhonor.swem.forge.entities.horse.behaviors.impl.LineageBehavior;
import com.alaharranhonor.swem.forge.entities.horse.coats.BaseCoats;
import com.alaharranhonor.swem.forge.util.SWEMUtil;
import com.evangelix.swemaddendumplus.AddendumNetwork;
import com.evangelix.swemaddendumplus.CoatDataRegistrar;
import com.evangelix.swemaddendumplus.ProgressionRegistrar;
import com.evangelix.swemaddendumplus.SwemAddendumPlusMain;
import com.evangelix.swemaddendumplus.breeds.donkey.Donkey;
import com.evangelix.swemaddendumplus.breeds.mule.Mule;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractSteed extends SWEMHorseEntity {

    public abstract String getFolderName();
    public abstract String getFoalFolderName();
    public abstract float getAffinityExpMultiplier(); // 1.0f
    public abstract int getMaxJumpDefault();
    public abstract int getMaxSpeedDefault();


    public AbstractSteed(EntityType<? extends SWEMHorseEntityBase> type, Level worldIn) {
        super(type, worldIn);
        CoatDataRegistrar.apply(this);
        ProgressionRegistrar.apply(this);
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
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

            if(this.getCoat() == CoatDataRegistrar.MISSING_TEXTURE_LOCATION) {
                AddendumNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> this),
                        new AddendumNetwork.TextureRequest(id, folderName, false, useServerCoats));
            }

            if(this.getFoalCoat() == CoatDataRegistrar.MISSING_TEXTURE_LOCATION) {
                AddendumNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> this),
                        new AddendumNetwork.TextureRequest(id, foalFolderName, true, useServerCoats));
            }
        }
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new AddendumBreedGoal(this));
    }

    public void setPegasus(boolean isPegasus) {
        this.getCoatBehavior().set(isPegasus ? BaseCoats.SWIFT_WIND_SHE_RA : BaseCoats.WHITE);
    }

    public ResourceLocation getCoat() {
        return this.entityData.get(CoatDataRegistrar.COAT);
    }

    public void setCoat(ResourceLocation resourceLocation) {
        this.entityData.set(CoatDataRegistrar.COAT, resourceLocation);
    }

    public ResourceLocation getFoalCoat() {
        return this.entityData.get(CoatDataRegistrar.FOAL_COAT);
    }

    public void setFoalCoat(ResourceLocation foalTexture) {
        this.entityData.set(CoatDataRegistrar.FOAL_COAT, foalTexture);
    }

    public int getMaxJumpLevel() {
        return this.entityData.get(ProgressionRegistrar.MAX_JUMP_LEVEL);
    }

    public void setMaxJumpLevel(int jumpLevel) {
        this.entityData.set(ProgressionRegistrar.MAX_JUMP_LEVEL, jumpLevel);
    }

    public int getMaxSpeedLevel() {
        return this.entityData.get(ProgressionRegistrar.MAX_SPEED_LEVEL);
    }

    public void setMaxSpeedLevel(int speedLevel) {
        this.entityData.set(ProgressionRegistrar.MAX_SPEED_LEVEL, speedLevel);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        if(compound.contains("MaxJumpLevel")) {
            this.setMaxJumpLevel(compound.getInt("MaxJumpLevel"));
        } else {
            this.setMaxJumpLevel(this.getMaxJumpDefault());
        }


        if(compound.contains("MaxSpeedLevel")) {
            this.setMaxSpeedLevel(compound.getInt("MaxSpeedLevel"));
        } else {
            this.setMaxSpeedLevel(this.getMaxSpeedDefault());
        }

        super.readAdditionalSaveData(compound);

        if(compound.contains("Texture")) {
            ResourceLocation texture = ResourceLocation.tryParse(compound.getString("Texture"));
            if (texture == null) {
                texture = CoatDataRegistrar.MISSING_TEXTURE_LOCATION;
            }
            this.setCoat(texture);
        }

        if(compound.contains("FoalTexture")) {
            ResourceLocation foalTexture = ResourceLocation.tryParse(compound.getString("FoalTexture"));
            if(foalTexture == null) {
                foalTexture = CoatDataRegistrar.MISSING_TEXTURE_LOCATION;
            }
            this.setFoalCoat(foalTexture);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Texture", this.getCoat().toString());
        compound.putString("FoalTexture", this.getFoalCoat().toString());
        compound.putInt("MaxJumpLevel", this.getMaxJumpLevel());
        compound.putInt("MaxSpeedLevel", this.getMaxSpeedLevel());
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        ResourceLocation texture = CoatDataRegistrar.MISSING_TEXTURE_LOCATION;
        if (dataTag != null && dataTag.contains("Texture")) {
            texture = ResourceLocation.tryParse(dataTag.getString("Texture"));
            if (texture == null) {
                texture = CoatDataRegistrar.MISSING_TEXTURE_LOCATION;
            }
        }
        this.setCoat(texture);

        ResourceLocation foalTexture = CoatDataRegistrar.MISSING_TEXTURE_LOCATION;
        if(dataTag != null && dataTag.contains("FoalTexture")) {
            foalTexture = ResourceLocation.tryParse(dataTag.getString("FoalTexture"));
            if(foalTexture == null) {
                foalTexture = CoatDataRegistrar.MISSING_TEXTURE_LOCATION;
            }
        }
        this.setFoalCoat(foalTexture);

        this.setMaxJumpLevel(this.getMaxJumpDefault());
        if(dataTag != null && dataTag.contains("MaxJumpLevel")) {
            this.setMaxJumpLevel(dataTag.getInt("MaxJumpLevel"));
        }

        this.setMaxSpeedLevel(this.getMaxSpeedDefault());
        if(dataTag != null && dataTag.contains("MaxSpeedLevel")) {
            this.setMaxSpeedLevel(dataTag.getInt("MaxSpeedLevel"));
        }

        return super.finalizeSpawn(levelIn, difficultyIn, reason, spawnDataIn, dataTag);
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
            EntityType<Mule> foal = SwemAddendumPlusMain.MULE.get();
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
        } else if(mob instanceof AbstractSteed abstractSteed) {
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

    public double getAlteredMovementSpeed() {
        switch (this.progressionManager.getSpeedLeveling().getLevel()) {
            case 1 -> {
                return SWEMUtil.getInternalSpeedFromBlocksPerSecond(13.0);
            }
            case 2 -> {
                return SWEMUtil.getInternalSpeedFromBlocksPerSecond(15.0);
            }
            case 3 -> {
                return SWEMUtil.getInternalSpeedFromBlocksPerSecond(17.0);
            }
            case 4 -> {
                return SWEMUtil.getInternalSpeedFromBlocksPerSecond(19.0);
            }
            case 5 -> {
                return SWEMUtil.getInternalSpeedFromBlocksPerSecond(21.0);
            }
            case 6 -> {
                return SWEMUtil.getInternalSpeedFromBlocksPerSecond(23.0);
            }
            default -> {
                return SWEMUtil.getInternalSpeedFromBlocksPerSecond(11.0);
            }
        }
    }

    public double getAlteredJumpStrength() {
        switch (this.progressionManager.getJumpLeveling().getLevel()) {
            case 1 -> {
                return SWEMUtil.getInternalJumpFromBlocks(2.7);
            }
            case 2 -> {
                return SWEMUtil.getInternalJumpFromBlocks(3.7);
            }
            case 3 -> {
                return SWEMUtil.getInternalJumpFromBlocks(4.7);
            }
            case 4 -> {
                return SWEMUtil.getInternalJumpFromBlocks(5.7);
            }
            case 5 -> {
                return SWEMUtil.getInternalJumpFromBlocks(6.7);
            }
            case 6 -> {
                return SWEMUtil.getInternalJumpFromBlocks(7.7);
            }
            default -> {
                return SWEMUtil.getInternalJumpFromBlocks(1.7);
            }
        }
    }
}
