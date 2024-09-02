package com.evangelix.swemaddendum.gui.inventory;

import com.evangelix.swemaddendum.AddendumNetwork;
import com.evangelix.swemaddendum.SwemAddendumMain;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;

public class AddendumScreen extends AbstractContainerScreen<AddendumMenu> {
    public static final ResourceLocation ICONS = new ResourceLocation(SwemAddendumMain.MODID, "gui/icons.png");

    public final SWEMHorseInventoryScreenProxy swemScreen;
    public final TraitScreen traitScreen;
    public AbstractContainerScreen<?> currentScreen;

    public AddendumScreen(AddendumMenu addendumMenu, Inventory inventory, Component component) {
        super(addendumMenu, inventory, component);
        this.currentScreen = this.swemScreen = new SWEMHorseInventoryScreenProxy(addendumMenu.swemMenu, inventory, component);
        this.traitScreen = new TraitScreen(addendumMenu.traitMenu, inventory, component);

        this.imageHeight = 247;
    }

    @Override
    public void onClose() {
        this.swemScreen.onClose();
        this.traitScreen.onClose();
        super.onClose();
    }

    @Override
    public void init() {
        super.init();
        this.swemScreen.init(this.getMinecraft(), this.width, this.height);
        this.traitScreen.init(this.getMinecraft(), this.width, this.height);
    }

    @Override
    public void containerTick() {
        super.containerTick();
        if(this.currentScreen instanceof SWEMHorseInventoryScreenProxy) {
            ((SWEMHorseInventoryScreenProxy) this.currentScreen).containerTick();
        } else if(this.currentScreen instanceof TraitScreen) {
            ((TraitScreen) this.currentScreen).containerTick();
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.currentScreen.render(guiGraphics, mouseX, mouseY, partialTick);

        int ax = 0;
        int hx = 88;

        if(this.currentScreen instanceof TraitScreen) {
            ax = 88;
            hx = 0;
        }

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0, 0, 500);
        guiGraphics.blit(ICONS, hx + this.leftPos, this.topPos, hx, 178, 88, 16);
        guiGraphics.blit(ICONS, ax + this.leftPos, this.topPos, ax, 162, 88, 16);
        guiGraphics.pose().popPose();
    }

    @Override
    public void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        if(this.currentScreen instanceof SWEMHorseInventoryScreenProxy) {
            ((SWEMHorseInventoryScreenProxy) this.currentScreen).renderBg(guiGraphics, partialTick, mouseX, mouseY);
        } else if(this.currentScreen instanceof TraitScreen) {
            ((TraitScreen) this.currentScreen).renderBg(guiGraphics, partialTick, mouseX, mouseY);
        }
    }

    @Override
    public void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        if(this.currentScreen instanceof SWEMHorseInventoryScreenProxy) {
            ((SWEMHorseInventoryScreenProxy) this.currentScreen).renderLabels(guiGraphics, mouseX, mouseY);
        } else if(this.currentScreen instanceof TraitScreen) {
            ((TraitScreen) this.currentScreen).renderLabels(guiGraphics, mouseX, mouseY);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int keyCode) {
        if(keyCode == InputConstants.MOUSE_BUTTON_LEFT) {
            double x = mouseX - this.leftPos;
            double y = mouseY - this.topPos;

            if(0 <= y && y <= 16) {
                if(0 <= x && x <= (double) this.imageWidth / 2) {
                    this.menu.setSwemTab(true);
                    this.currentScreen = this.swemScreen;
                    AddendumNetwork.INSTANCE.sendToServer(new AddendumNetwork.SetActiveTabRequest(true));
                    return true;
                } else if((double) this.imageWidth / 2 <= x && x <= this.imageWidth) {
                    this.menu.setSwemTab(false);
                    this.currentScreen = this.traitScreen;
                    AddendumNetwork.INSTANCE.sendToServer(new AddendumNetwork.SetActiveTabRequest(false));
                    return true;
                }
            }
        }
        return this.currentScreen.mouseClicked(mouseX, mouseY, keyCode);
    }

    @Override
    public boolean hasClickedOutside(double mouseX, double mouseY, int width, int height, int keyCode) {
        if(this.currentScreen instanceof SWEMHorseInventoryScreenProxy) {
            return ((SWEMHorseInventoryScreenProxy) this.currentScreen).hasClickedOutside(mouseX, mouseY, width, height, keyCode);
        } else if(this.currentScreen instanceof TraitScreen) {
            return ((TraitScreen) this.currentScreen).hasClickedOutside(mouseX, mouseY, width, height, keyCode);
        }
        return super.hasClickedOutside(mouseX, mouseY, width, height, keyCode);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int keyCoce, double dirX, double dirY) {
        return this.currentScreen.mouseDragged(mouseX, mouseY, keyCoce, dirX, dirY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int keyCode) {
        return this.currentScreen.mouseReleased(mouseX, mouseY, keyCode);
    }

    @Override
    public void clearDraggingState() {
        this.currentScreen.clearDraggingState();
    }

    @Override
    public boolean isHovering(int ox, int oy, int w, int h, double x, double y) {
        if(this.currentScreen instanceof SWEMHorseInventoryScreenProxy) {
            return ((SWEMHorseInventoryScreenProxy) this.currentScreen).isHovering(ox, oy, w, h, x, y);
        } else if(this.currentScreen instanceof TraitScreen) {
            return ((TraitScreen) this.currentScreen).isHovering(ox, oy, w, h, x, y);
        }
        return super.isHovering(ox, oy, w, h, x, y);
    }

    @Override
    public void slotClicked(Slot slot, int x, int y, ClickType clickType) {
        if(this.currentScreen instanceof SWEMHorseInventoryScreenProxy) {
            ((SWEMHorseInventoryScreenProxy) this.currentScreen).slotClicked(slot, x, y, clickType);
        } else if(this.currentScreen instanceof TraitScreen) {
            ((TraitScreen) this.currentScreen).slotClicked(slot, x, y, clickType);
        }
    }

    @Override
    public boolean keyPressed(int mouseX, int mouseY, int keyCode) {
        return this.currentScreen.keyPressed(mouseX, mouseY, keyCode);
    }

    @Override
    public boolean checkHotbarKeyPressed(int x, int y) {
        if(this.currentScreen instanceof SWEMHorseInventoryScreenProxy) {
            return ((SWEMHorseInventoryScreenProxy) this.currentScreen).checkHotbarKeyPressed(x, y);
        } else if(this.currentScreen instanceof TraitScreen) {
            return ((TraitScreen) this.currentScreen).checkHotbarKeyPressed(x, y);
        }
        return super.checkHotbarKeyPressed(x, y);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double dir) {
        return this.currentScreen.mouseScrolled(mouseX, mouseY, dir);
    }
}
