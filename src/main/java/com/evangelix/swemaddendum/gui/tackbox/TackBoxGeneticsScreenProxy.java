package com.evangelix.swemaddendum.gui.tackbox;

import com.alaharranhonor.swem.forge.client.gui.screens.TackBoxGeneticsScreen;
import com.alaharranhonor.swem.forge.container.TackBoxContainer;
import com.evangelix.swemaddendum.SwemAddendumMain;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class TackBoxGeneticsScreenProxy extends TackBoxGeneticsScreen {
    public TackBoxGeneticsScreenProxy(TackBoxContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
    }
}
