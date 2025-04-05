package com.evangelix.swemaddendum.registrar;

import com.evangelix.swemaddendum.SwemAddendumMain;
import com.evangelix.swemaddendum.abstract_steed.AbstractSteed;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;

public class CoatDataRegistrar {
    public static final ResourceLocation MISSING_TEXTURE_LOCATION = new ResourceLocation("missingno");
    public static final EntityDataAccessor<ResourceLocation> COAT = SynchedEntityData.defineId(AbstractSteed.class, SwemAddendumMain.RESOURCE_LOCATION);
    public static final EntityDataAccessor<ResourceLocation> FOAL_COAT = SynchedEntityData.defineId(AbstractSteed.class, SwemAddendumMain.RESOURCE_LOCATION);

    public static void apply(AbstractSteed abstractSteed) {
        abstractSteed.getEntityData().define(COAT, MISSING_TEXTURE_LOCATION);
        abstractSteed.getEntityData().define(FOAL_COAT, MISSING_TEXTURE_LOCATION);
    }
}
