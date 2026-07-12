package com.evangelix.swemaddendum.event;

import com.alaharranhonor.swem.entity.horse.AbstractSwemHorse;
import com.evangelix.swemaddendum.SwemAddendumMain;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber
public class SwemAddendumEvent {
    @SubscribeEvent
    public static void entityAttributeCreationEvent(EntityAttributeCreationEvent event) {
        event.put(SwemAddendumMain.AMERICAN_QUARTER_HORSE.get(), AbstractSwemHorse.createBaseSwemHorseAttributes().build());
        event.put(SwemAddendumMain.ARABIAN.get(), AbstractSwemHorse.createBaseSwemHorseAttributes().build());
        event.put(SwemAddendumMain.BRETON.get(), AbstractSwemHorse.createBaseSwemHorseAttributes().build());
        event.put(SwemAddendumMain.DONKEY.get(), AbstractSwemHorse.createBaseSwemHorseAttributes().build());
        event.put(SwemAddendumMain.FJORD.get(), AbstractSwemHorse.createBaseSwemHorseAttributes().build());
        event.put(SwemAddendumMain.FRIESIAN.get(), AbstractSwemHorse.createBaseSwemHorseAttributes().build());
        event.put(SwemAddendumMain.IRISH_DRAUGHT.get(), AbstractSwemHorse.createBaseSwemHorseAttributes().build());
        event.put(SwemAddendumMain.KLADRUBER.get(), AbstractSwemHorse.createBaseSwemHorseAttributes().build());
        event.put(SwemAddendumMain.KNABSTRUPPER.get(), AbstractSwemHorse.createBaseSwemHorseAttributes().build());
        event.put(SwemAddendumMain.MARWARI.get(), AbstractSwemHorse.createBaseSwemHorseAttributes().build());
        event.put(SwemAddendumMain.MULE.get(), AbstractSwemHorse.createBaseSwemHorseAttributes().build());
        event.put(SwemAddendumMain.MUSTANG.get(), AbstractSwemHorse.createBaseSwemHorseAttributes().build());
        event.put(SwemAddendumMain.PEGASUS.get(), AbstractSwemHorse.createBaseSwemHorseAttributes().build());
        event.put(SwemAddendumMain.SHIRE.get(), AbstractSwemHorse.createBaseSwemHorseAttributes().build());
        event.put(SwemAddendumMain.THOROUGHBRED.get(), AbstractSwemHorse.createBaseSwemHorseAttributes().build());
        event.put(SwemAddendumMain.TURKOMAN.get(), AbstractSwemHorse.createBaseSwemHorseAttributes().build());
        event.put(SwemAddendumMain.WARMBLOOD.get(), AbstractSwemHorse.createBaseSwemHorseAttributes().build());
    }
}
