package com.evangelix.swemaddendumplus.mixins;

import com.alaharranhonor.swem.forge.config.ServerConfig;
import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntityBase;
import com.alaharranhonor.swem.forge.entities.horse.progression.leveling.ILeveling;
import com.alaharranhonor.swem.forge.entities.horse.progression.leveling.JumpLeveling;
import com.evangelix.swemaddendumplus.ProgressionRegistrar;
import com.evangelix.swemaddendumplus.abstract_steed.AbstractSteed;
import com.evangelix.swemaddendumplus.progression.IJumpLevelingMixin;
import net.minecraft.network.syncher.SynchedEntityData;
import org.spongepowered.asm.mixin.*;

@Mixin(JumpLeveling.class)
@Implements(@Interface(iface = IJumpLevelingMixin.class, prefix = "jumpLeveling$"))
public abstract class JumpLevelingMixin implements ILeveling {
    @Unique
    public String[] jumpLeveling$getExtendedLevelNames() {
        return new String[]{"Jump I", "Jump II", "Jump III", "Jump IV", "Jump V", "Jump VI", "Jump VII [MAX]"};
    }

    @Shadow @Final
    private String[] levelNames;

    @Shadow @Final
    private SynchedEntityData dataManager;

    @Shadow @Final
    private SWEMHorseEntityBase horse;

    @Shadow
    public abstract int getMaxLevel();

    @Shadow public abstract String getLevelName();

    @Shadow public abstract float getRequiredXp();

    @Intrinsic(displace = true)
    public String jumpLeveling$getLevelName() {
        int level = this.dataManager.get(SWEMHorseEntityBase.JUMP_LEVEL);
        return this.jumpLeveling$getExtendedLevelNames()[level];
    }

    @Intrinsic(displace = true)
    public int jumpLeveling$getMaxLevel() {
        if (this.horse instanceof AbstractSteed) {
            return ((AbstractSteed) this.horse).getMaxJumpLevel();
        }
        return this.getMaxLevel();
    }

    @Intrinsic(displace = true)
    public float jumpLeveling$getRequiredXp() {
        if (this.dataManager.get(SWEMHorseEntityBase.JUMP_LEVEL) > 3) {
            return (float) ServerConfig.TOTAL_JUMP_XP.get() * 0.5F;
        }
        return this.getRequiredXp();
    }
}
