package com.evangelix.swemaddendumplus.mixins;

import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntityBase;
import com.alaharranhonor.swem.forge.entities.horse.progression.leveling.AffinityLeveling;
import com.evangelix.swemaddendumplus.abstract_steed.AbstractSteed;
import com.evangelix.swemaddendumplus.progression.IAffinityLevelingMixin;
import org.spongepowered.asm.mixin.*;

@Mixin(AffinityLeveling.class)
@Implements(@Interface(iface = IAffinityLevelingMixin.class, prefix = "affinityLeveling$"))
public abstract class AffinityLevelingMixin {

    @Final
    @Shadow
    private SWEMHorseEntityBase horse;

    @Shadow
    public abstract boolean addXP(float amount);

    @Intrinsic(displace = true)
    public boolean affinityLeveling$addXP(float amount) {
        if (this.horse instanceof AbstractSteed) {
            return this.addXP(amount * (((AbstractSteed) this.horse).getAffinityExpMultiplier()));
        }
        return this.addXP(amount);
    }
}
