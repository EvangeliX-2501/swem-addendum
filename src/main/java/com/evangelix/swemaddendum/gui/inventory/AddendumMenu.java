package com.evangelix.swemaddendum.gui.inventory;

import com.evangelix.swemaddendum.SwemAddendumMain;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.ContainerSynchronizer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.OptionalInt;

public class AddendumMenu extends AbstractContainerMenu {
    public final SWEMHorseInventoryContainerProxy swemMenu;
    public final TraitMenu traitMenu;
    public AbstractContainerMenu currentMenu;

    public AddendumMenu(int containerId, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
        this(containerId, inventory, friendlyByteBuf.readInt());
    }

    public AddendumMenu(int containerId, Inventory inventory, int id) {
        super(SwemAddendumMain.ADDENDUM_MENU.get(), containerId);
        this.currentMenu = this.swemMenu = new SWEMHorseInventoryContainerProxy(containerId, inventory, id);
        this.traitMenu = new TraitMenu(containerId, inventory, id);
    }

    public void setSwemTab(boolean isSwemTab) {
        this.currentMenu = isSwemTab ? this.swemMenu : this.traitMenu;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotId) {
        return this.currentMenu.quickMoveStack(player, slotId);
    }

    @Override
    public boolean stillValid(Player player) {
        return this.currentMenu.stillValid(player);
    }

    @Override
    public boolean isValidSlotIndex(int index) {
        return this.currentMenu.isValidSlotIndex(index);
    }

    @Override
    public void setSynchronizer(ContainerSynchronizer synchronizer) {
        this.currentMenu.setSynchronizer(synchronizer);
    }

    @Override
    public void sendAllDataToRemote() {
        this.currentMenu.sendAllDataToRemote();
    }

    @Override
    public void broadcastChanges() {
        this.currentMenu.broadcastChanges();
    }

    @Override
    public void broadcastFullState() {
        this.currentMenu.broadcastFullState();
    }

    @Override
    public void setRemoteSlot(int i, ItemStack itemStack) {
        this.currentMenu.setRemoteSlot(i, itemStack);
    }

    @Override
    public void setRemoteSlotNoCopy(int i, ItemStack itemStack) {
        this.currentMenu.setRemoteSlotNoCopy(i, itemStack);
    }

    @Override
    public void setRemoteCarried(ItemStack itemStack) {
        this.currentMenu.setRemoteCarried(itemStack);
    }

    @Override
    public boolean clickMenuButton(Player player, int index) {
        return this.currentMenu.clickMenuButton(player, index);
    }

    @Override
    public Slot getSlot(int index) {
        return this.currentMenu.getSlot(index);
    }

    @Override
    public void clicked(int x, int y, ClickType clickType, Player player) {
        this.currentMenu.clicked(x, y, clickType, player);
    }

    @Override
    public void removed(Player player) {
        this.currentMenu.removed(player);
    }

    @Override
    public void setItem(int index, int id, ItemStack itemStack) {
        this.currentMenu.setItem(index, id, itemStack);
    }

    @Override
    public void initializeContents(int id, List<ItemStack> itemStacks, ItemStack itemStack) {
        this.currentMenu.initializeContents(id, itemStacks, itemStack);
    }

    @Override
    public void setData(int index, int value) {
        this.currentMenu.setData(index, value);
    }

    @Override
    public boolean moveItemStackTo(ItemStack itemStack, int prevIndex, int index, boolean bool) {
        if(this.currentMenu instanceof SWEMHorseInventoryContainerProxy) {
            return ((SWEMHorseInventoryContainerProxy) this.currentMenu).moveItemStackTo(itemStack, prevIndex, index, bool);
        } else if(this.currentMenu instanceof TraitMenu) {
            return ((TraitMenu)this.currentMenu).moveItemStackTo(itemStack, prevIndex, index, bool);
        }
        return super.moveItemStackTo(itemStack, prevIndex, index, bool);
    }

    @Override
    public void resetQuickCraft() {
        if(this.currentMenu instanceof SWEMHorseInventoryContainerProxy) {
            ((SWEMHorseInventoryContainerProxy) this.currentMenu).resetQuickCraft();
        } else if(this.currentMenu instanceof TraitMenu) {
            ((TraitMenu)this.currentMenu).resetQuickCraft();
        }
        super.resetQuickCraft();
    }

    @Override
    public boolean canDragTo(Slot slot) {
        return this.currentMenu.canDragTo(slot);
    }

    @Override
    public void setCarried(ItemStack itemStack) {
        this.currentMenu.setCarried(itemStack);
    }

    @Override
    public ItemStack getCarried() {
        return this.currentMenu.getCarried();
    }

    @Override
    public void suppressRemoteUpdates() {
        this.currentMenu.suppressRemoteUpdates();
    }

    @Override
    public void resumeRemoteUpdates() {
        this.currentMenu.resumeRemoteUpdates();
    }

    @Override
    public void transferState(AbstractContainerMenu abstractContainerMenu) {
        this.currentMenu.transferState(abstractContainerMenu);
    }

    @Override
    public OptionalInt findSlot(Container container, int index) {
        return this.currentMenu.findSlot(container, index);
    }

    @Override
    public int getStateId() {
        return this.currentMenu.getStateId();
    }

    @Override
    public int incrementStateId() {
        return this.currentMenu.incrementStateId();
    }
}
