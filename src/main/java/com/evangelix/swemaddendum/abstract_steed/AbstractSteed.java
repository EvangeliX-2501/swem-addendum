package com.evangelix.swemaddendum.abstract_steed;

import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntity;
import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntityBase;
import com.alaharranhonor.swem.forge.entities.horse.behaviors.impl.ActionBehavior;
import com.alaharranhonor.swem.forge.entities.horse.behaviors.impl.LineageBehavior;
import com.alaharranhonor.swem.forge.entities.horse.coats.BaseCoats;
import com.alaharranhonor.swem.forge.network.protocol.SWEMPackets;
import com.alaharranhonor.swem.forge.network.protocol.game.ServerboundHorseActionPacket;
import com.alaharranhonor.swem.forge.util.SWEMUtil;
import com.evangelix.swemaddendum.AddendumNetwork;
import com.evangelix.swemaddendum.CoatDataRegistrar;
import com.evangelix.swemaddendum.SwemAddendumMain;
import com.evangelix.swemaddendum.TraitRegistrar;
import com.evangelix.swemaddendum.breeds.donkey.Donkey;
import com.evangelix.swemaddendum.breeds.mule.Mule;
import com.evangelix.swemaddendum.gui.inventory.AddendumMenu;
import com.evangelix.swemaddendum.gui.inventory.SWEMHorseInventoryContainerProxy;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractSteed extends SWEMHorseEntity {

    public static final MobEffectInstance STRENGTH_II = new MobEffectInstance(MobEffects.DAMAGE_BOOST, MobEffectInstance.INFINITE_DURATION, 1, false, false);
    public static final MobEffectInstance SPEED_I = new MobEffectInstance(MobEffects.MOVEMENT_SPEED, MobEffectInstance.INFINITE_DURATION, 0, false, false);
    public static final MobEffectInstance REGEN_I = new MobEffectInstance(MobEffects.REGENERATION, MobEffectInstance.INFINITE_DURATION, 0, false, false);
    public static final MobEffectInstance JUMP_I = new MobEffectInstance(MobEffects.JUMP, MobEffectInstance.INFINITE_DURATION, 0, false, false);
    public static final MobEffectInstance JUMP_II = new MobEffectInstance(MobEffects.JUMP, MobEffectInstance.INFINITE_DURATION, 1, false, false);
    public static final MobEffectInstance SLOWNESS_I = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, MobEffectInstance.INFINITE_DURATION, 0, false, false);
    public static final MobEffectInstance SLOWNESS_II = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, MobEffectInstance.INFINITE_DURATION, 1, false, false);
    public static final MobEffectInstance HEAL_II = new MobEffectInstance(MobEffects.HEAL, MobEffectInstance.INFINITE_DURATION, 1, false, false);
    public static final MobEffectInstance RESISTANCE_I = new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, MobEffectInstance.INFINITE_DURATION, 0, false, false);
    public static final MobEffectInstance WEAKNESS_I = new MobEffectInstance(MobEffects.WEAKNESS, MobEffectInstance.INFINITE_DURATION, 0, false, false);
    public static final List<MobEffectInstance> ALL_POSSIBLE_EFFECTS = List.of(
            STRENGTH_II,
            SPEED_I,
            REGEN_I,
            JUMP_I,
            JUMP_II,
            SLOWNESS_I,
            SLOWNESS_II,
            HEAL_II,
            RESISTANCE_I,
            WEAKNESS_I
    );

    public static final float WILD_REFUSAL_MOD = 1.2f;
    public static final float STUBBORN_REFUSAL_MOD = 1.5f;


    public int dirtyTickCounter = 0;

    public abstract String getFolderName();
    public abstract String getFoalFolderName();


    public AbstractSteed(EntityType<? extends SWEMHorseEntityBase> type, Level worldIn) {
        super(type, worldIn);
        CoatDataRegistrar.apply(this);
        TraitRegistrar.apply(this);
    }

    @Override
    public void openCustomInventoryScreen(Player playerEntity) {
        if(!this.level().isClientSide() && (!this.isVehicle() || this.hasPassenger(playerEntity)) && this.isTamed()) {
            if(!this.canAccessHorse(playerEntity)) {
                return;
            }

            Component horseDisplayName = Component.literal(SWEMUtil.checkTextOverflow(this.getDisplayName().getString(), 18));
            NetworkHooks.openScreen((ServerPlayer) playerEntity, new SimpleMenuProvider((containerId, inventory, serverPlayer) -> {
                return new AddendumMenu(containerId, inventory, this.getId());
            }, horseDisplayName), (data) -> data.writeInt(this.getId()));
        }
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

            if(this.getPersonality() == TraitRegistrar.Personality.NONE) {
                this.setPersonality(TraitRegistrar.getRandom(TraitRegistrar.Personality.class));
            }
        }
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new AddendumBreedGoal(this));
    }

    public void setPegasus() {
        this.getCoatBehavior().set(BaseCoats.WHITE);
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

    public TraitRegistrar.Personality getPersonality() {
        return this.entityData.get(TraitRegistrar.PERSONALITY);
    }

    public void reapplyEffects() {
        List<MobEffectInstance> currentApplicableEffects = this.getActiveEffects().stream().filter(ALL_POSSIBLE_EFFECTS::contains).toList();
        for(MobEffectInstance mobEffectInstance : currentApplicableEffects) {
            this.removeEffect(mobEffectInstance.getEffect());
        }

        switch (this.getPersonality()) {
            case VICIOUS -> this.addEffect(STRENGTH_II);
            case WILD -> this.addEffect(SPEED_I);
            case ENERGETIC -> {
                this.addEffect(SPEED_I);
                this.addEffect(JUMP_I);
            }
            case SLOTHFUL -> {
                this.addEffect(SLOWNESS_I);
                this.addEffect(WEAKNESS_I);
            }
            case GLUTTONOUS -> this.addEffect(HEAL_II);
            case BRAVE -> {
                this.addEffect(REGEN_I);
                this.addEffect(RESISTANCE_I);
            }
            case BOUNCY -> this.addEffect(JUMP_II);
        }

        if(this.getCleanliness() == TraitRegistrar.Cleanliness.DIRTY) {
            this.addEffect(SLOWNESS_I);
        } else if(this.getCleanliness() == TraitRegistrar.Cleanliness.FILTHY) {
            this.addEffect(SLOWNESS_II);
        }
    }

    public void setPersonality(TraitRegistrar.Personality personality) {
        this.entityData.set(TraitRegistrar.PERSONALITY, personality);
        this.reapplyEffects();
    }

    public TraitRegistrar.Cleanliness getCleanliness() {
        return this.entityData.get(TraitRegistrar.CLEANLINESS);
    }

    public void setCleanliness(TraitRegistrar.Cleanliness cleanliness) {
        this.entityData.set(TraitRegistrar.CLEANLINESS, cleanliness);
        this.reapplyEffects();
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Texture")) {
            ResourceLocation texture = ResourceLocation.tryParse(compound.getString("Texture"));
            if (texture == null) {
                texture = CoatDataRegistrar.MISSING_TEXTURE_LOCATION;
            }
            this.setCoat(texture);
            this.setPegasus();
        }

        if(compound.contains("FoalTexture")) {
            ResourceLocation foalTexture = ResourceLocation.tryParse(compound.getString("FoalTexture"));
            if(foalTexture == null) {
                foalTexture = CoatDataRegistrar.MISSING_TEXTURE_LOCATION;
            }
            this.setFoalCoat(foalTexture);
        }

        if (compound.contains("Personality")) {
            this.setPersonality(TraitRegistrar.Personality.values()[compound.getInt("Personality") % TraitRegistrar.Personality.values().length]);
        }

        if (compound.contains("Cleanliness")) {
            this.setCleanliness(TraitRegistrar.Cleanliness.values()[compound.getInt("Cleanliness") % TraitRegistrar.Cleanliness.values().length]);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Texture", this.getCoat().toString());
        compound.putString("FoalTexture", this.getFoalCoat().toString());
        compound.putInt("Cleanliness", this.getCleanliness().ordinal());
        compound.putInt("Personality", this.getPersonality().ordinal());
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

        TraitRegistrar.Personality personality = TraitRegistrar.getRandom(TraitRegistrar.Personality.class);
        if(dataTag != null && dataTag.contains("Personality")) {
            personality = TraitRegistrar.Personality.values()[dataTag.getInt("Personality") % TraitRegistrar.Personality.values().length];
        }
        this.setPersonality(personality);

        TraitRegistrar.Cleanliness cleanliness = TraitRegistrar.Cleanliness.CLEAN;
        if(dataTag != null && dataTag.contains("Cleanliness")) {
            cleanliness = TraitRegistrar.Cleanliness.values()[dataTag.getInt("Cleanliness") % TraitRegistrar.Cleanliness.values().length];
        }
        this.setCleanliness(cleanliness);
        this.reapplyEffects();
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

    @Override
    public void tick() {
        super.tick();

        if (this.dirtyTickCounter >= 43200) {
            this.setCleanliness(TraitRegistrar.getNextMax(this.getCleanliness()));
            this.dirtyTickCounter = 0;
        }
        this.dirtyTickCounter++;
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(SwemAddendumMain.CLEANLINESS_ITEMS)) {
            this.setCleanliness(TraitRegistrar.getPreviousMin(this.getCleanliness()));
            this.dirtyTickCounter = 0;
            return InteractionResult.sidedSuccess(this.level().isClientSide());
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public boolean isVisuallySad() {
        return (this.getPersonality() == TraitRegistrar.Personality.DOWN_TRODDEN) || super.isVisuallySad();
    }

    @Override
    public void disobey() {
        ActionBehavior.Action action = this.getRandom().nextDouble() > 0.5 ? ActionBehavior.Action.BUCK : ActionBehavior.Action.REAR;
        if (this.level().isClientSide) {
            SWEMPackets.sendToServer(new ServerboundHorseActionPacket(this.getUUID(), action));
        } else {
            this.getBehavior(ActionBehavior.class).startAction(action);
        }

        float mod = 1;
        if(this.getPersonality() == TraitRegistrar.Personality.WILD) {
            mod = WILD_REFUSAL_MOD;
        } else if(this.getPersonality() == TraitRegistrar.Personality.STUBBORN) {
            mod = STUBBORN_REFUSAL_MOD;
        }

        this.setDisobedienceTimer((int)(142 * mod));
    }

    @Override
    public double getJumpDisobey(float jumpHeight) {
        float mod = 1;
        if(this.getPersonality() == TraitRegistrar.Personality.WILD) {
            mod = WILD_REFUSAL_MOD;
        } else if(this.getPersonality() == TraitRegistrar.Personality.STUBBORN) {
            mod = STUBBORN_REFUSAL_MOD;
        }

        return mod * super.getJumpDisobey(jumpHeight);
    }

    @Override
    public boolean canEat() {
        return (this.getPersonality() == TraitRegistrar.Personality.GLUTTONOUS && !this.isBaby() && !this.isVehicle() && !this.isLeashed() && !this.isBridleLeashed() && !this.hasBridle()) || super.canEat();
    }
}
