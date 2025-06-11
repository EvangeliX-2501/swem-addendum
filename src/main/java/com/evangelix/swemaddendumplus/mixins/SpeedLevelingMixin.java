package com.evangelix.swemaddendumplus.mixins;

import com.alaharranhonor.swem.forge.config.ServerConfig;
import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntityBase;
import com.alaharranhonor.swem.forge.entities.horse.progression.leveling.ILeveling;
import com.alaharranhonor.swem.forge.entities.horse.progression.leveling.SpeedLeveling;
import com.evangelix.swemaddendumplus.ProgressionRegistrar;
import com.evangelix.swemaddendumplus.abstract_steed.AbstractSteed;
import com.evangelix.swemaddendumplus.progression.ISpeedLevelingMixin;
import net.minecraft.network.syncher.SynchedEntityData;
import org.spongepowered.asm.mixin.*;

@Mixin(SpeedLeveling.class)
@Implements(@Interface(iface = ISpeedLevelingMixin.class, prefix = "speedLeveling$"))
public abstract class SpeedLevelingMixin implements ILeveling {
    @Unique
    public String[] speedLeveling$getExtendedLevelNames() {
        return new String[]{"Speed I", "Speed II", "Speed III", "Speed IV", "Speed V", "Speed VI", "Speed VII [MAX]"};
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
    public String speedLeveling$getLevelName() {
        int level = this.dataManager.get(SWEMHorseEntityBase.SPEED_LEVEL);
        return this.speedLeveling$getExtendedLevelNames()[level];
    }

    @Intrinsic(displace = true)
    public int speedLeveling$getMaxLevel() {
        if (this.horse instanceof AbstractSteed) {
            return ((AbstractSteed) this.horse).getMaxSpeedLevel();
        }
        return this.getMaxLevel();
    }

    @Intrinsic(displace = true)
    public float speedLeveling$getRequiredXp() {
        if (this.dataManager.get(SWEMHorseEntityBase.SPEED_LEVEL) > 3) {
            return (float) ServerConfig.TOTAL_SPEED_XP.get() * 0.5F;
        }
        return this.getRequiredXp();
    }
}
