package com.evangelix.swemaddendum.abstract_steed;

import com.alaharranhonor.swem.entity.horse.AbstractSwemHorse;
import net.minecraft.world.entity.ai.goal.BreedGoal;

public class AddendumBreedGoal extends BreedGoal {

    public final AbstractSwemHorse horse;

    public AddendumBreedGoal(AbstractSwemHorse animal) {
        super(animal, 4.0, AbstractSwemHorse.class);
        this.horse = animal;
    }

    @Override
    protected void breed() {
        AbstractSwemHorse partner = (AbstractSwemHorse) this.partner;
        if(partner != null) {
            this.horse.getBreeding().breed(partner);
            partner.getBreeding().breed(this.horse);
        }
    }
}
