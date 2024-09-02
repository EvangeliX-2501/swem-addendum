package com.evangelix.swemaddendum.gui.inventory;

import com.alaharranhonor.swem.forge.container.SWEMHorseInventoryContainer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class SWEMHorseInventoryContainerProxy extends SWEMHorseInventoryContainer {
    public SWEMHorseInventoryContainerProxy(int id, Inventory playerInventory, int entityId) {
        super(id, playerInventory, entityId);
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
