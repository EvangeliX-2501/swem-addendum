package com.evangelix.swemaddendum.abstract_steed;

import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntityBase;
import net.minecraft.world.entity.ai.goal.BreedGoal;

public class AddendumBreedGoal extends BreedGoal {

    public final SWEMHorseEntityBase horse;

    public AddendumBreedGoal(SWEMHorseEntityBase animal) {
        super(animal, 4.0, SWEMHorseEntityBase.class);
        this.horse = animal;
    }

    @Override
    protected void breed() {
        SWEMHorseEntityBase partner = (SWEMHorseEntityBase) this.partner;
        this.horse.getBreeding().breed(partner);
        partner.getBreeding().breed(this.horse);
    }
}
