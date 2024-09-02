package com.evangelix.swemaddendum.event;

import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntityBase;
import com.evangelix.swemaddendum.SwemAddendumMain;
import com.evangelix.swemaddendum.breeds.american_quarter_horse.AmericanQuarterHorseRenderer;
import com.evangelix.swemaddendum.breeds.arabian.ArabianRenderer;
import com.evangelix.swemaddendum.breeds.breton.BretonRenderer;
import com.evangelix.swemaddendum.breeds.donkey.DonkeyRenderer;
import com.evangelix.swemaddendum.breeds.fjord.FjordRenderer;
import com.evangelix.swemaddendum.breeds.friesian.FriesianRenderer;
import com.evangelix.swemaddendum.breeds.irish_draught.IrishDraughtRenderer;
import com.evangelix.swemaddendum.breeds.kladruber.KladruberRenderer;
import com.evangelix.swemaddendum.breeds.knabstrupper.KnabstrupperRenderer;
import com.evangelix.swemaddendum.breeds.marwari.MarwariRenderer;
import com.evangelix.swemaddendum.breeds.mule.MuleRenderer;
import com.evangelix.swemaddendum.breeds.mustang.MustangRenderer;
import com.evangelix.swemaddendum.breeds.pegasus.PegasusRenderer;
import com.evangelix.swemaddendum.breeds.shire.ShireRenderer;
import com.evangelix.swemaddendum.breeds.thoroughbred.ThoroughbredRenderer;
import com.evangelix.swemaddendum.breeds.turkoman.TurkomanRenderer;
import com.evangelix.swemaddendum.breeds.warmblood.WarmbloodRenderer;
import com.evangelix.swemaddendum.gui.inventory.AddendumScreen;
import com.evangelix.swemaddendum.gui.inventory.TraitScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = SwemAddendumMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SwemAddendumEvent {

    @SubscribeEvent
    public static void clientSetupEvent(FMLClientSetupEvent event) {
        MenuScreens.register(SwemAddendumMain.ADDENDUM_MENU.get(), AddendumScreen::new);
        MenuScreens.register(SwemAddendumMain.TRAIT_MENU.get(), TraitScreen::new);

        EntityRenderers.register(SwemAddendumMain.AMERICAN_QUARTER_HORSE.get(), AmericanQuarterHorseRenderer::new);
        EntityRenderers.register(SwemAddendumMain.ARABIAN.get(), ArabianRenderer::new);
        EntityRenderers.register(SwemAddendumMain.BRETON.get(), BretonRenderer::new);
        EntityRenderers.register(SwemAddendumMain.DONKEY.get(), DonkeyRenderer::new);
        EntityRenderers.register(SwemAddendumMain.FJORD.get(), FjordRenderer::new);
        EntityRenderers.register(SwemAddendumMain.FRIESIAN.get(), FriesianRenderer::new);
        EntityRenderers.register(SwemAddendumMain.IRISH_DRAUGHT.get(), IrishDraughtRenderer::new);
        EntityRenderers.register(SwemAddendumMain.KLADRUBER.get(), KladruberRenderer::new);
        EntityRenderers.register(SwemAddendumMain.KNABSTRUPPER.get(), KnabstrupperRenderer::new);
        EntityRenderers.register(SwemAddendumMain.MARWARI.get(), MarwariRenderer::new);
        EntityRenderers.register(SwemAddendumMain.MULE.get(), MuleRenderer::new);
        EntityRenderers.register(SwemAddendumMain.MUSTANG.get(), MustangRenderer::new);
        EntityRenderers.register(SwemAddendumMain.PEGASUS.get(), PegasusRenderer::new);
        EntityRenderers.register(SwemAddendumMain.SHIRE.get(), ShireRenderer::new);
        EntityRenderers.register(SwemAddendumMain.THOROUGHBRED.get(), ThoroughbredRenderer::new);
        EntityRenderers.register(SwemAddendumMain.TURKOMAN.get(), TurkomanRenderer::new);
        EntityRenderers.register(SwemAddendumMain.WARMBLOOD.get(), WarmbloodRenderer::new);
    }

    @SubscribeEvent
    public static void entityAttributeCreationEvent(EntityAttributeCreationEvent event) {
        event.put(SwemAddendumMain.AMERICAN_QUARTER_HORSE.get(), SWEMHorseEntityBase.createBaseSWEMHorseAttributes().build());
        event.put(SwemAddendumMain.ARABIAN.get(), SWEMHorseEntityBase.createBaseSWEMHorseAttributes().build());
        event.put(SwemAddendumMain.BRETON.get(), SWEMHorseEntityBase.createBaseSWEMHorseAttributes().build());
        event.put(SwemAddendumMain.DONKEY.get(), SWEMHorseEntityBase.createBaseSWEMHorseAttributes().build());
        event.put(SwemAddendumMain.FJORD.get(), SWEMHorseEntityBase.createBaseSWEMHorseAttributes().build());
        event.put(SwemAddendumMain.FRIESIAN.get(), SWEMHorseEntityBase.createBaseSWEMHorseAttributes().build());
        event.put(SwemAddendumMain.IRISH_DRAUGHT.get(), SWEMHorseEntityBase.createBaseSWEMHorseAttributes().build());
        event.put(SwemAddendumMain.KLADRUBER.get(), SWEMHorseEntityBase.createBaseSWEMHorseAttributes().build());
        event.put(SwemAddendumMain.KNABSTRUPPER.get(), SWEMHorseEntityBase.createBaseSWEMHorseAttributes().build());
        event.put(SwemAddendumMain.MARWARI.get(), SWEMHorseEntityBase.createBaseSWEMHorseAttributes().build());
        event.put(SwemAddendumMain.MULE.get(), SWEMHorseEntityBase.createBaseSWEMHorseAttributes().build());
        event.put(SwemAddendumMain.MUSTANG.get(), SWEMHorseEntityBase.createBaseSWEMHorseAttributes().build());
        event.put(SwemAddendumMain.PEGASUS.get(), SWEMHorseEntityBase.createBaseSWEMHorseAttributes().build());
        event.put(SwemAddendumMain.SHIRE.get(), SWEMHorseEntityBase.createBaseSWEMHorseAttributes().build());
        event.put(SwemAddendumMain.THOROUGHBRED.get(), SWEMHorseEntityBase.createBaseSWEMHorseAttributes().build());
        event.put(SwemAddendumMain.TURKOMAN.get(), SWEMHorseEntityBase.createBaseSWEMHorseAttributes().build());
        event.put(SwemAddendumMain.WARMBLOOD.get(), SWEMHorseEntityBase.createBaseSWEMHorseAttributes().build());
    }
}
