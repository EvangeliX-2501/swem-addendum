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

public class StickOfAcknowledgement extends Item {
    public StickOfAcknowledgement() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand hand) {
        if(player.level().isClientSide()) {
            return super.interactLivingEntity(itemStack, player, livingEntity, hand);
        }

        if(livingEntity instanceof AbstractSteed abstractSteed) {
            player.sendSystemMessage(Component.literal("Texture: " + abstractSteed.getCoat().toString()).withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
            player.sendSystemMessage(Component.literal("Foal Texture: " + abstractSteed.getFoalCoat().toString()).withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
            player.sendSystemMessage(Component.literal("Max Jump Level: " + (abstractSteed.getMaxJumpLevel() + 1)).withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
            player.sendSystemMessage(Component.literal("Max Speed Level: " + (abstractSteed.getMaxSpeedLevel() + 1)).withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
