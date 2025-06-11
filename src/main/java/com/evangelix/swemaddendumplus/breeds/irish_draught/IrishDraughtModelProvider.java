package com.evangelix.swemaddendumplus.breeds.irish_draught;

import com.evangelix.swemaddendumplus.SwemAddendumPlusMain;
import com.evangelix.swemaddendumplus.abstract_steed.AbstractSteedModelProvider;
import net.minecraft.resources.ResourceLocation;

public class IrishDraughtModelProvider extends AbstractSteedModelProvider {

    public static final ResourceLocation FOAL_MODEL = new ResourceLocation(SwemAddendumPlusMain.MODID, "geo/foal/steed_foal.geo.json");
    public static final ResourceLocation ADULT_MODEL = new ResourceLocation("swem", "geo/entity/horse/swem_horse.geo.json");


    @Override
    public ResourceLocation getFoalModelLocation() {
        return FOAL_MODEL;
    }

    @Override
    public ResourceLocation getAdultModelLocation() {
        return ADULT_MODEL;
    }
}
