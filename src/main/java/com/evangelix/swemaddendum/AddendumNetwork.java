package com.evangelix.swemaddendum;

//import com.evangelix.swemaddendum.abstract_steed.AbstractSteed;

import com.evangelix.swemaddendum.abstract_steed.AbstractSteed;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = SwemAddendumMain.MODID)
public class AddendumNetwork {
    public record ApplyTexture(int id, ResourceLocation coat, boolean isFoal) implements CustomPacketPayload {
        public static final CustomPacketPayload.Type<ApplyTexture> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(SwemAddendumMain.MODID, "apply_texture"));
        public static final StreamCodec<FriendlyByteBuf, ApplyTexture> STREAM_CODEC = new StreamCodec<>() {
            @Override
            public void encode(FriendlyByteBuf buffer, ApplyTexture value) {
                buffer.writeInt(value.id);
                buffer.writeResourceLocation(value.coat);
                buffer.writeBoolean(value.isFoal);
            }

            @Override
            public ApplyTexture decode(FriendlyByteBuf buffer) {
                int id = buffer.readInt();
                ResourceLocation coat = buffer.readResourceLocation();
                boolean isFoal = buffer.readBoolean();
                return new ApplyTexture(id, coat, isFoal);
            }
        };

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }

        public static void handler(ApplyTexture msg, IPayloadContext ctx) {
            ctx.enqueueWork(() -> {
               Player player = ctx.player();
               if(player instanceof ServerPlayer) {
                   Entity entity = player.level().getEntity(msg.id);
                   if(entity instanceof AbstractSteed abstractSteed) {
                       if(msg.isFoal) {
                           abstractSteed.setFoalCoat(msg.coat);
                       } else {
                           abstractSteed.setCoat(msg.coat);
                       }
                   }
               }
            });
        }
    }

    public record TextureRequest(int id, String folderName, boolean isFoal, boolean useServerCoats) implements CustomPacketPayload {
        public static final CustomPacketPayload.Type<TextureRequest> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(SwemAddendumMain.MODID, "texture_request"));
        public static final StreamCodec<FriendlyByteBuf, TextureRequest> STREAM_CODEC = new StreamCodec<>() {
            @Override
            public TextureRequest decode(FriendlyByteBuf buffer) {
                int id = buffer.readInt();
                String folderName = buffer.readUtf();
                boolean isFoal = buffer.readBoolean();
                boolean useServerCoats = buffer.readBoolean();
                return new TextureRequest(id, folderName, isFoal, useServerCoats);
            }

            @Override
            public void encode(FriendlyByteBuf buffer, TextureRequest value) {
                buffer.writeInt(value.id);
                buffer.writeUtf(value.folderName);
                buffer.writeBoolean(value.isFoal);
                buffer.writeBoolean(value.useServerCoats);
            }
        };

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }

        public static void handler(TextureRequest msg, IPayloadContext ctx) {
            ctx.enqueueWork(() -> {
                LocalPlayer player = Minecraft.getInstance().player;
                if(player != null) {
                    ResourceLocation coat = msg.useServerCoats ? TextureGen.getRandomServerCoat(msg.folderName) : TextureGen.getRandomClientCoat(msg.folderName);
                    PacketDistributor.sendToServer(new ApplyTexture(msg.id, coat, msg.isFoal));
                }
            });
        }
    }

    @SubscribeEvent
    public static void commonSetupEvent(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.commonBidirectional(ApplyTexture.TYPE, ApplyTexture.STREAM_CODEC, ApplyTexture::handler);
        registrar.commonBidirectional(TextureRequest.TYPE, TextureRequest.STREAM_CODEC, TextureRequest::handler);
    }
}
