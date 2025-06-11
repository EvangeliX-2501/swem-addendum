package com.evangelix.swemaddendumplus.item;

import com.evangelix.swemaddendumplus.abstract_steed.AbstractSteed;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SpeedBreaker extends Item {
    public SpeedBreaker() {
        super(new Properties());
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand hand) {
        if (livingEntity instanceof AbstractSteed abstractSteed) {
            LivingEntity owner = abstractSteed.getOwner();
            if(owner != null && owner.is(player)) {
                if (!player.level().isClientSide()) {
                    int oldSpeed = abstractSteed.getMaxSpeedLevel();
                    int newSpeed = oldSpeed + 1;

                    if (newSpeed > 6) {
                        player.sendSystemMessage(Component.literal("Your " + abstractSteed.getDisplayName().getString() + "'s max speed level has already peaked!").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
                        return InteractionResult.PASS;
                    } else if (newSpeed == 6) {
                        player.sendSystemMessage(Component.literal("Your " + abstractSteed.getDisplayName().getString() + "'s max speed level has peaked! " + (oldSpeed + 1) + " -> " + (newSpeed + 1) + " [MAX]").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
                        itemStack.shrink(1);
                        abstractSteed.setMaxSpeedLevel(newSpeed);
                        return InteractionResult.SUCCESS;
                    } else {
                        player.sendSystemMessage(Component.literal("Your " + abstractSteed.getDisplayName().getString() + "'s max speed level has increased! " + (oldSpeed + 1) + " -> " + (newSpeed + 1)).withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
                        itemStack.shrink(1);
                        abstractSteed.setMaxSpeedLevel(newSpeed);
                        return InteractionResult.SUCCESS;
                    }
                }
                return InteractionResult.sidedSuccess(player.level().isClientSide());
            } else {
                player.sendSystemMessage(Component.literal("This isn't your " + abstractSteed.getDisplayName().getString() + "..."));
                return InteractionResult.FAIL;
            }
        }
        return super.interactLivingEntity(itemStack, player, livingEntity, hand);
    }
}
