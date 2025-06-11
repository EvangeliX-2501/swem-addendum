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

public class JumpBreaker extends Item {
    public JumpBreaker() {
        super(new Properties());
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand hand) {
        if (livingEntity instanceof AbstractSteed abstractSteed) {
            LivingEntity owner = abstractSteed.getOwner();
            if(owner != null && owner.is(player)) {
                if (!player.level().isClientSide()) {
                    int oldJump = abstractSteed.getMaxJumpLevel();
                    int newJump = oldJump + 1;

                    if (newJump > 6) {
                        player.sendSystemMessage(Component.literal("Your " + abstractSteed.getDisplayName().getString() + "'s max jump level is already peaked!").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
                        return InteractionResult.PASS;
                    } else if (newJump == 6) {
                        player.sendSystemMessage(Component.literal("Your " + abstractSteed.getDisplayName().getString() + "'s max jump level has peaked! " + (oldJump + 1) + " -> " + (newJump + 1) + " [MAX]").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
                        itemStack.shrink(1);
                        abstractSteed.setMaxJumpLevel(newJump);
                        return InteractionResult.SUCCESS;
                    } else {
                        player.sendSystemMessage(Component.literal("Your " + abstractSteed.getDisplayName().getString() + "'s max jump level has increased! " + (oldJump + 1) + " -> " + (newJump + 1)).withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
                        itemStack.shrink(1);
                        abstractSteed.setMaxJumpLevel(newJump);
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
