package com.evangelix.swemaddendum;

import com.alaharranhonor.swem.forge.keys.Keys;
import com.alaharranhonor.swem.forge.util.PermissionNodes;
import com.evangelix.swemaddendum.abstract_steed.AbstractSteed;
import com.evangelix.swemaddendum.gui.inventory.AddendumMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = SwemAddendumMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AddendumNetwork {
    public static class ApplyTexture {
        public final int id;
        public final ResourceLocation coat;
        public final boolean isFoal;

        public ApplyTexture(int id, ResourceLocation coat, boolean isFoal) {
            this.id = id;
            this.coat = coat;
            this.isFoal = isFoal;
        }

        public static void encode(ApplyTexture msg, FriendlyByteBuf buf) {
            buf.writeInt(msg.id);
            buf.writeResourceLocation(msg.coat);
            buf.writeBoolean(msg.isFoal);
        }

        public static ApplyTexture decode(FriendlyByteBuf buf) {
            int id = buf.readInt();
            ResourceLocation coat = buf.readResourceLocation();
            boolean foal = buf.readBoolean();

            return new ApplyTexture(id, coat, foal);
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
                    INSTANCE.sendToServer(new ApplyTexture(msg.id, coat, msg.isFoal));
                }
            });
            ctx.setPacketHandled(true);
        }
    }

    public static class SetActiveTabRequest {
        public final boolean isSwemTab;

        public SetActiveTabRequest(boolean isSwemTab) {
            this.isSwemTab = isSwemTab;
        }

        public static void encode(SetActiveTabRequest msg, FriendlyByteBuf buf) {
            buf.writeBoolean(msg.isSwemTab);
        }

        public static SetActiveTabRequest decode(FriendlyByteBuf buf) {
            boolean isSwemTab = buf.readBoolean();
            return new SetActiveTabRequest(isSwemTab);
        }

        public static void handle(SetActiveTabRequest msg, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context ctx = contextSupplier.get();
            ctx.enqueueWork(() -> {
                ServerPlayer player = ctx.getSender();
                if(player != null) {
                    AbstractContainerMenu containerMenu = player.containerMenu;
                    if(containerMenu instanceof AddendumMenu addendumMenu) {
                        addendumMenu.setSwemTab(msg.isSwemTab);
                    }
                }
            });
            ctx.setPacketHandled(true);
        }
    }

    public static class SetPersonalityRequest {
        public final int id;
        public final TraitRegistrar.Personality personality;

        public SetPersonalityRequest(int id, TraitRegistrar.Personality personality) {
            this.id = id;
            this.personality = personality;
        }

        public static void encode(SetPersonalityRequest msg, FriendlyByteBuf buf) {
            buf.writeInt(msg.id);
            buf.writeEnum(msg.personality);
        }

        public static SetPersonalityRequest decode(FriendlyByteBuf buf) {
            int id = buf.readInt();
            TraitRegistrar.Personality personality = buf.readEnum(TraitRegistrar.Personality.class);
            return new SetPersonalityRequest(id, personality);
        }

        public static void handle(SetPersonalityRequest msg, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context ctx = contextSupplier.get();
            ctx.enqueueWork(() -> {
                ServerPlayer player = ctx.getSender();
                if(player != null && Keys.hasPermission(player, PermissionNodes.CAN_MODIFY) && Keys.hasPermission(player, PermissionNodes.CAN_LEVEL) && Keys.hasPermission(player, PermissionNodes.CAN_SUMMON)) {
                    Entity entity = player.level().getEntity(msg.id);
                    if(entity instanceof AbstractSteed abstractSteed) {
                        abstractSteed.setPersonality(msg.personality);
                    }
                }
            });
            ctx.setPacketHandled(true);
        }
    }

    public static class SetCleanlinessRequest {
        public final int id;
        public final TraitRegistrar.Cleanliness cleanliness;

        public SetCleanlinessRequest(int id, TraitRegistrar.Cleanliness cleanliness) {
            this.id = id;
            this.cleanliness = cleanliness;
        }

        public static void encode(SetCleanlinessRequest msg, FriendlyByteBuf buf) {
            buf.writeInt(msg.id);
            buf.writeEnum(msg.cleanliness);
        }

        public static SetCleanlinessRequest decode(FriendlyByteBuf buf) {
            int id = buf.readInt();
            TraitRegistrar.Cleanliness cleanliness = buf.readEnum(TraitRegistrar.Cleanliness.class);
            return new SetCleanlinessRequest(id, cleanliness);
        }

        public static void handle(SetCleanlinessRequest msg, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context ctx = contextSupplier.get();
            ctx.enqueueWork(() -> {
                ServerPlayer player = ctx.getSender();
                if(player != null && Keys.hasPermission(player, PermissionNodes.CAN_MODIFY) && Keys.hasPermission(player, PermissionNodes.CAN_LEVEL) && Keys.hasPermission(player, PermissionNodes.CAN_SUMMON)) {
                    Entity entity = player.level().getEntity(msg.id);
                    if(entity instanceof AbstractSteed abstractSteed) {
                        abstractSteed.setCleanliness(msg.cleanliness);
                    }
                }
            });
            ctx.setPacketHandled(true);
        }
    }


    public static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(SwemAddendumMain.MODID, "addendum_network"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    @SubscribeEvent
    public static void commonSetupEvent(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            INSTANCE.registerMessage(0, ApplyTexture.class, ApplyTexture::encode, ApplyTexture::decode, ApplyTexture::handle);
            INSTANCE.registerMessage(1, TextureRequest.class, TextureRequest::encode, TextureRequest::decode, TextureRequest::handle);
            INSTANCE.registerMessage(2, SetActiveTabRequest.class, SetActiveTabRequest::encode, SetActiveTabRequest::decode, SetActiveTabRequest::handle);
            INSTANCE.registerMessage(3, SetPersonalityRequest.class, SetPersonalityRequest::encode, SetPersonalityRequest::decode, SetPersonalityRequest::handle);
            INSTANCE.registerMessage(4, SetCleanlinessRequest.class, SetCleanlinessRequest::encode, SetCleanlinessRequest::decode, SetCleanlinessRequest::handle);
        });
    }
}
