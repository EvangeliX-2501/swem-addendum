package com.evangelix.swemaddendum.gui.inventory;

import com.evangelix.swemaddendum.SwemAddendumMain;
import com.evangelix.swemaddendum.abstract_steed.AbstractSteed;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TraitMenu extends AbstractContainerMenu {
    public final AbstractSteed abstractSteed;

    public TraitMenu(int containerId, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
        this(containerId, inventory, friendlyByteBuf.readInt());
    }

    public TraitMenu(int containerId, Inventory inventory, int id) {
        super(SwemAddendumMain.TRAIT_MENU.get(), containerId);
        this.abstractSteed = (AbstractSteed) inventory.player.level().getEntity(id);

        int playerSlots = 0;
        for(int x = 0; x < 9; x++) {
            this.addSlot(new Slot(inventory, playerSlots++, 8 + x * 18, 198));
        }

        for(int y = 0; y < 3; y++) {
            for(int x = 0; x < 9; x++) {
                this.addSlot(new Slot(inventory, playerSlots++, 8 + x * 18, 140 + y * 18));
            }
        }
    }

    @Override
    public void initializeContents(int id, List<ItemStack> itemStacks, ItemStack itemStack) {
        List<ItemStack> itemStacksCopy = new ArrayList<>();
        for(int i = 0; i < itemStacks.size() - 8; i++) {
            itemStacksCopy.add(itemStacks.get(i));
        }

        super.initializeContents(id, itemStacksCopy, itemStack);
    }

    @Override
    public boolean stillValid(Player player) {
        return this.abstractSteed.isAlive() && this.abstractSteed.distanceTo(player) < 8;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player playerIn, int slotId) {
        Slot slot = this.slots.get(slotId);
        if(!slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack itemStack = slot.getItem();
        ItemStack itemStackCopy = itemStack.copy();

        if(slotId < 9) {
            if(!this.moveItemStackTo(itemStack, 9, 36, false)) {
                return ItemStack.EMPTY;
            }
        } else if(slotId < 36) {
            if(!this.moveItemStackTo(itemStack, 0, 9, false)) {
                return ItemStack.EMPTY;
            }
        }

        if(itemStack.getCount() == 0) {
            slot.set(ItemStack.EMPTY);
        }

        slot.setChanged();
        return itemStackCopy;
    }

    @Override
    public boolean moveItemStackTo(ItemStack itemStack, int prevIndex, int index, boolean bool) {
        return super.moveItemStackTo(itemStack, prevIndex, index, bool);
    }

    @Override
    public void resetQuickCraft() {
        super.resetQuickCraft();
    }
}
