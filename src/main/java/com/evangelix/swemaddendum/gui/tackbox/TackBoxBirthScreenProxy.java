package com.evangelix.swemaddendum.gui.tackbox;

import com.alaharranhonor.swem.forge.client.gui.screens.TackBoxBirthScreen;
import com.alaharranhonor.swem.forge.container.TackBoxContainer;
import com.evangelix.swemaddendum.SwemAddendumMain;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;

public class TackBoxBirthScreenProxy extends TackBoxBirthScreen  {
    public TackBoxBirthScreenProxy(TackBoxContainer tackBoxContainer, Inventory inv, Component titleIn) {
        super(tackBoxContainer, inv, titleIn);
    }
}
