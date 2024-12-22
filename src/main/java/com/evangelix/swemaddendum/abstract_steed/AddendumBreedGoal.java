package com.evangelix.swemaddendum.abstract_steed;

import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntityBase;
import com.alaharranhonor.swem.forge.network.protocol.SWEMPackets;
import com.alaharranhonor.swem.forge.network.protocol.game.breeding.ClientboundHorseBreedPacket;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraftforge.network.PacketDistributor;

public class AddendumBreedGoal extends BreedGoal {

    public final SWEMHorseEntityBase horse;

    public AddendumBreedGoal(SWEMHorseEntityBase animal) {
        super(animal, 4.0, SWEMHorseEntityBase.class);
        this.horse = animal;
    }

    @Override
    public void breed() {
        this.horse.getGender().breed((SWEMHorseEntityBase) this.partner);
        ((SWEMHorseEntityBase)this.partner).getGender().breed(this.horse);
        SWEMPackets.sendToPlayers(PacketDistributor.TRACKING_ENTITY.with(() -> this.horse),
                new ClientboundHorseBreedPacket(this.horse.getId(), this.partner.getId()));
    }
}
