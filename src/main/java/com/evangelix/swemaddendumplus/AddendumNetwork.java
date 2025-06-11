package com.evangelix.swemaddendumplus;

import com.evangelix.swemaddendumplus.abstract_steed.AbstractSteed;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = SwemAddendumPlusMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AddendumNetwork {
    public static class ApplyTexture {
        public final int id;
        public final ResourceLocation coat;
        public final boolean isFoal;
        public final boolean isPegasus;

        public ApplyTexture(int id, ResourceLocation coat, boolean isFoal, boolean isPegasus) {
            this.id = id;
            this.coat = coat;
            this.isFoal = isFoal;
            this.isPegasus = isPegasus;
        }

        public static void encode(ApplyTexture msg, FriendlyByteBuf buf) {
            buf.writeInt(msg.id);
            buf.writeResourceLocation(msg.coat);
            buf.writeBoolean(msg.isFoal);
            buf.writeBoolean(msg.isPegasus);
        }

        public static ApplyTexture decode(FriendlyByteBuf buf) {
            int id = buf.readInt();
            ResourceLocation coat = buf.readResourceLocation();
            boolean foal = buf.readBoolean();
            boolean isPegasus = buf.readBoolean();

            return new ApplyTexture(id, coat, foal, isPegasus);
        }

        public static void handle(ApplyTexture msg, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context ctx = contextSupplier.get();
            ctx.enqueueWork(() -> {
                ServerPlayer serverPlayer = ctx.getSender();
                if(serverPlayer != null) {
                    Entity entity = serverPlayer.level().getEntity(msg.id);
                    if(entity instanceof AbstractSteed abstractSteed) {
                        if(msg.isFoal) {
                            abstractSteed.setFoalCoat(msg.coat);
                        } else {
                            abstractSteed.setCoat(msg.coat);
                            abstractSteed.setPegasus(msg.isPegasus);
                        }
                    }
                }
            });
            ctx.setPacketHandled(true);
        }
    }

    public static class TextureRequest {
        public final int id;
        public final String folderName;
        public final boolean isFoal;
        public final boolean useServerCoats;

        public TextureRequest(int id, String folderName, boolean isFoal, boolean useServerCoats) {
            this.id = id;
            this.folderName = folderName;
            this.isFoal = isFoal;
            this.useServerCoats = useServerCoats;
        }

        public static void encode(TextureRequest msg, FriendlyByteBuf buf) {
            buf.writeInt(msg.id);
            buf.writeUtf(msg.folderName);
            buf.writeBoolean(msg.isFoal);
            buf.writeBoolean(msg.useServerCoats);
        }

        public static TextureRequest decode(FriendlyByteBuf buf) {
            int id = buf.readInt();
            String folderName = buf.readUtf();
            boolean isFoal = buf.readBoolean();
            boolean useServerCoats = buf.readBoolean();

            return new TextureRequest(id, folderName, isFoal, useServerCoats);
        }

        public static void handle(TextureRequest msg, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context ctx = contextSupplier.get();
            ctx.enqueueWork(() -> {
                LocalPlayer player = Minecraft.getInstance().player;
                if(player != null) {
                    ResourceLocation coat = msg.useServerCoats ? TextureGen.getRandomServerCoat(msg.folderName) : TextureGen.getRandomClientCoat(msg.folderName);

                    String wings = coat.getPath().replace(".png", "_wings.png");
                    ResourceLocation wingsLocation = new ResourceLocation(coat.getNamespace(), wings);
                    boolean isPegasus = Minecraft.getInstance().getResourceManager().getResource(wingsLocation).isPresent();

                    INSTANCE.sendToServer(new ApplyTexture(msg.id, coat, msg.isFoal, isPegasus));
                }
            });
            ctx.setPacketHandled(true);
        }
    }

    public static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(SwemAddendumPlusMain.MODID, "addendum_network"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    @SubscribeEvent
    public static void commonSetupEvent(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            INSTANCE.registerMessage(0, ApplyTexture.class, ApplyTexture::encode, ApplyTexture::decode, ApplyTexture::handle);
            INSTANCE.registerMessage(1, TextureRequest.class, TextureRequest::encode, TextureRequest::decode, TextureRequest::handle);
        });
    }
}
