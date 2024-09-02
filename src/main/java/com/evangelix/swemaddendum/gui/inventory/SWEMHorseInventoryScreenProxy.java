package com.evangelix.swemaddendum.gui.inventory;

import com.alaharranhonor.swem.forge.client.gui.screens.SWEMHorseInventoryScreen;
import com.alaharranhonor.swem.forge.container.SWEMHorseInventoryContainer;
import com.evangelix.swemaddendum.SwemAddendumMain;
import com.evangelix.swemaddendum.abstract_steed.AbstractSteed;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.NotNull;

public class SWEMHorseInventoryScreenProxy extends SWEMHorseInventoryScreen {
    public SWEMHorseInventoryScreenProxy(SWEMHorseInventoryContainer container, Inventory playerInventoryIn, Component title) {
        super(container, playerInventoryIn, title);
    }

    @Override
    public void renderBg(GuiGraphics pGuiGraphics, float pPartialTicks, int pMouseX, int pMouseY) {
        super.renderBg(pGuiGraphics, pPartialTicks, pMouseX, pMouseY);
    }

    @Override
    public void containerTick() {
        super.containerTick();
    }

    @Override
    public void renderLabels(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);
    }

    @Override
    public boolean hasClickedOutside(double mouseX, double mouseY, int width, int height, int keyCode) {
        return super.hasClickedOutside(mouseX, mouseY, width, height, keyCode);
    }

    @Override
    public boolean isHovering(int ox, int oy, int w, int h, double x, double y) {
        return super.isHovering(ox, oy, w, h, x, y);
    }

    @Override
    public void slotClicked(Slot slot, int x, int y, ClickType clickType) {
        super.slotClicked(slot, x, y, clickType);
    }

    @Override
    public boolean checkHotbarKeyPressed(int x, int y) {
        return super.checkHotbarKeyPressed(x, y);
    }
}
