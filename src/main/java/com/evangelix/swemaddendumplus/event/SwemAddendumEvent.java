package com.evangelix.swemaddendumplus.event;

import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntityBase;
import com.evangelix.swemaddendumplus.SwemAddendumPlusMain;
import com.evangelix.swemaddendumplus.breeds.american_quarter_horse.AmericanQuarterHorseRenderer;
import com.evangelix.swemaddendumplus.breeds.arabian.ArabianRenderer;
import com.evangelix.swemaddendumplus.breeds.breton.BretonRenderer;
import com.evangelix.swemaddendumplus.breeds.donkey.DonkeyRenderer;
import com.evangelix.swemaddendumplus.breeds.fjord.FjordRenderer;
import com.evangelix.swemaddendumplus.breeds.friesian.FriesianRenderer;
import com.evangelix.swemaddendumplus.breeds.irish_draught.IrishDraughtRenderer;
import com.evangelix.swemaddendumplus.breeds.kladruber.KladruberRenderer;
import com.evangelix.swemaddendumplus.breeds.knabstrupper.KnabstrupperRenderer;
import com.evangelix.swemaddendumplus.breeds.marwari.MarwariRenderer;
import com.evangelix.swemaddendumplus.breeds.mule.MuleRenderer;
import com.evangelix.swemaddendumplus.breeds.mustang.MustangRenderer;
import com.evangelix.swemaddendumplus.breeds.pegasus.PegasusRenderer;
import com.evangelix.swemaddendumplus.breeds.shire.ShireRenderer;
import com.evangelix.swemaddendumplus.breeds.thoroughbred.ThoroughbredRenderer;
import com.evangelix.swemaddendumplus.breeds.turkoman.TurkomanRenderer;
import com.evangelix.swemaddendumplus.breeds.warmblood.WarmbloodRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = SwemAddendumPlusMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SwemAddendumEvent {

    @SubscribeEvent
    public static void clientSetupEvent(FMLClientSetupEvent event) {
        EntityRenderers.register(SwemAddendumPlusMain.AMERICAN_QUARTER_HORSE.get(), AmericanQuarterHorseRenderer::new);
        EntityRenderers.register(SwemAddendumPlusMain.ARABIAN.get(), ArabianRenderer::new);
        EntityRenderers.register(SwemAddendumPlusMain.BRETON.get(), BretonRenderer::new);
        EntityRenderers.register(SwemAddendumPlusMain.DONKEY.get(), DonkeyRenderer::new);
        EntityRenderers.register(SwemAddendumPlusMain.FJORD.get(), FjordRenderer::new);
        EntityRenderers.register(SwemAddendumPlusMain.FRIESIAN.get(), FriesianRenderer::new);
        EntityRenderers.register(SwemAddendumPlusMain.IRISH_DRAUGHT.get(), IrishDraughtRenderer::new);
        EntityRenderers.register(SwemAddendumPlusMain.KLADRUBER.get(), KladruberRenderer::new);
        EntityRenderers.register(SwemAddendumPlusMain.KNABSTRUPPER.get(), KnabstrupperRenderer::new);
        EntityRenderers.register(SwemAddendumPlusMain.MARWARI.get(), MarwariRenderer::new);
        EntityRenderers.register(SwemAddendumPlusMain.MULE.get(), MuleRenderer::new);
        EntityRenderers.register(SwemAddendumPlusMain.MUSTANG.get(), MustangRenderer::new);
        EntityRenderers.register(SwemAddendumPlusMain.PEGASUS.get(), PegasusRenderer::new);
        EntityRenderers.register(SwemAddendumPlusMain.SHIRE.get(), ShireRenderer::new);
        EntityRenderers.register(SwemAddendumPlusMain.THOROUGHBRED.get(), ThoroughbredRenderer::new);
        EntityRenderers.register(SwemAddendumPlusMain.TURKOMAN.get(), TurkomanRenderer::new);
        EntityRenderers.register(SwemAddendumPlusMain.WARMBLOOD.get(), WarmbloodRenderer::new);
    }

    @SubscribeEvent
    public static void entityAttributeCreationEvent(EntityAttributeCreationEvent event) {
        event.put(SwemAddendumPlusMain.AMERICAN_QUARTER_HORSE.get(), SWEMHorseEntityBase.createBaseSWEMHorseAttributes().build());
        event.put(SwemAddendumPlusMain.ARABIAN.get(), SWEMHorseEntityBase.createBaseSWEMHorseAttributes().build());
        event.put(SwemAddendumPlusMain.BRETON.get(), SWEMHorseEntityBase.createBaseSWEMHorseAttributes().build());
        event.put(SwemAddendumPlusMain.DONKEY.get(), SWEMHorseEntityBase.createBaseSWEMHorseAttributes().build());
        event.put(SwemAddendumPlusMain.FJORD.get(), SWEMHorseEntityBase.createBaseSWEMHorseAttributes().build());
        event.put(SwemAddendumPlusMain.FRIESIAN.get(), SWEMHorseEntityBase.createBaseSWEMHorseAttributes().build());
        event.put(SwemAddendumPlusMain.IRISH_DRAUGHT.get(), SWEMHorseEntityBase.createBaseSWEMHorseAttributes().build());
        event.put(SwemAddendumPlusMain.KLADRUBER.get(), SWEMHorseEntityBase.createBaseSWEMHorseAttributes().build());
        event.put(SwemAddendumPlusMain.KNABSTRUPPER.get(), SWEMHorseEntityBase.createBaseSWEMHorseAttributes().build());
        event.put(SwemAddendumPlusMain.MARWARI.get(), SWEMHorseEntityBase.createBaseSWEMHorseAttributes().build());
        event.put(SwemAddendumPlusMain.MULE.get(), SWEMHorseEntityBase.createBaseSWEMHorseAttributes().build());
        event.put(SwemAddendumPlusMain.MUSTANG.get(), SWEMHorseEntityBase.createBaseSWEMHorseAttributes().build());
        event.put(SwemAddendumPlusMain.PEGASUS.get(), SWEMHorseEntityBase.createBaseSWEMHorseAttributes().build());
        event.put(SwemAddendumPlusMain.SHIRE.get(), SWEMHorseEntityBase.createBaseSWEMHorseAttributes().build());
        event.put(SwemAddendumPlusMain.THOROUGHBRED.get(), SWEMHorseEntityBase.createBaseSWEMHorseAttributes().build());
        event.put(SwemAddendumPlusMain.TURKOMAN.get(), SWEMHorseEntityBase.createBaseSWEMHorseAttributes().build());
        event.put(SwemAddendumPlusMain.WARMBLOOD.get(), SWEMHorseEntityBase.createBaseSWEMHorseAttributes().build());
    }
}
