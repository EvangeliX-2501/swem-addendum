package com.evangelix.swemaddendumplus;

import com.evangelix.swemaddendumplus.abstract_steed.AbstractSteed;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;

public class ProgressionRegistrar {
    public static final EntityDataAccessor<Integer> MAX_JUMP_LEVEL = SynchedEntityData.defineId(AbstractSteed.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> MAX_SPEED_LEVEL = SynchedEntityData.defineId(AbstractSteed.class, EntityDataSerializers.INT);

    public static void apply(AbstractSteed abstractSteed) {
        abstractSteed.getEntityData().define(MAX_JUMP_LEVEL, 0);
        abstractSteed.getEntityData().define(MAX_SPEED_LEVEL, 0);
    }
}
