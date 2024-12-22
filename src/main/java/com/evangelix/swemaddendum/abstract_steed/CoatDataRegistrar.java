package com.evangelix.swemaddendum.abstract_steed;

import com.evangelix.swemaddendum.SwemAddendumMain;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;

public class CoatDataRegistrar {
    public static final ResourceLocation MISSING_TEXTURE_LOCATION = new ResourceLocation("missingno");
    public static final EntityDataAccessor<ResourceLocation> TEXTURE = SynchedEntityData.defineId(AbstractSteed.class, SwemAddendumMain.RESOURCE_LOCATION);
    public static final EntityDataAccessor<ResourceLocation> FOAL_TEXTURE = SynchedEntityData.defineId(AbstractSteed.class, SwemAddendumMain.RESOURCE_LOCATION);

    public static void apply(AbstractSteed abstractSteed) {
        abstractSteed.getEntityData().define(TEXTURE, MISSING_TEXTURE_LOCATION);
        abstractSteed.getEntityData().define(FOAL_TEXTURE, MISSING_TEXTURE_LOCATION);
    }
}
