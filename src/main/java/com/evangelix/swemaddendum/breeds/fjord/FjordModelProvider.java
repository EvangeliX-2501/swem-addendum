package com.evangelix.swemaddendum.breeds.fjord;

import com.evangelix.swemaddendum.SwemAddendumMain;
import com.evangelix.swemaddendum.abstract_steed.AbstractSteedModelProvider;
import net.minecraft.resources.ResourceLocation;

public class FjordModelProvider extends AbstractSteedModelProvider {
    public static final ResourceLocation FOAL_MODEL = new ResourceLocation(SwemAddendumMain.MODID, "geo/foal/steed_foal.geo.json");
    public static final ResourceLocation ADULT_MODEL = new ResourceLocation(SwemAddendumMain.MODID, "geo/swem_fjord.geo.json");


    @Override
    public ResourceLocation getFoalModelLocation() {
        return FOAL_MODEL;
    }

    @Override
    public ResourceLocation getAdultModelLocation() {
        return ADULT_MODEL;
    }
}

