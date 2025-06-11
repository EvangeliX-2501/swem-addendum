package com.evangelix.swemaddendumplus.breeds.mule;

import com.evangelix.swemaddendumplus.SwemAddendumPlusMain;
import com.evangelix.swemaddendumplus.abstract_steed.AbstractSteedModelProvider;
import net.minecraft.resources.ResourceLocation;

public class MuleModelProvider extends AbstractSteedModelProvider {
    public static final ResourceLocation FOAL_MODEL = new ResourceLocation(SwemAddendumPlusMain.MODID, "geo/foal/mule_foal.geo.json");
    public static final ResourceLocation ADULT_MODEL = new ResourceLocation(SwemAddendumPlusMain.MODID, "geo/swem_mule.geo.json");


    @Override
    public ResourceLocation getFoalModelLocation() {
        return FOAL_MODEL;
    }

    @Override
    public ResourceLocation getAdultModelLocation() {
        return ADULT_MODEL;
    }
}

