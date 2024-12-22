package com.evangelix.swemaddendum.item;

import com.evangelix.swemaddendum.abstract_steed.AbstractSteed;
import net.minecraft.Util;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TextureItem extends Item {
    public TextureItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    @NotNull
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
        if(player.level.isClientSide()) {
            return super.interactLivingEntity(itemStack, player, livingEntity, interactionHand);
        }

        if(livingEntity instanceof AbstractSteed abstractSteed) {
            player.sendMessage(new TranslatableComponent(
                    "Texture: " + abstractSteed.getTextureLocation().toString() +
                    ",\nFoal Texture: " + abstractSteed.getFoalTexture().toString()),
                    Util.NIL_UUID
            );
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
