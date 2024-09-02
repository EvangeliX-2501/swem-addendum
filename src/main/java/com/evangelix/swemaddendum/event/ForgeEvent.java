package com.evangelix.swemaddendum.event;

import com.alaharranhonor.swem.forge.client.gui.screens.TackBoxBirthScreen;
import com.alaharranhonor.swem.forge.client.gui.screens.TackBoxDefaultScreen;
import com.alaharranhonor.swem.forge.client.gui.screens.TackBoxGeneticsScreen;
import com.alaharranhonor.swem.forge.client.gui.screens.TackBoxProgressionScreen;
import com.alaharranhonor.swem.forge.container.TackBoxContainer;
import com.evangelix.swemaddendum.SwemAddendumMain;
import com.evangelix.swemaddendum.abstract_steed.AbstractSteed;
import com.evangelix.swemaddendum.gui.tackbox.TackBoxTraitScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value= Dist.CLIENT)
public class ForgeEvent {
    public static final ResourceLocation ICONS = new ResourceLocation(SwemAddendumMain.MODID, "gui/icons.png");

    @SubscribeEvent
    public static void onScreenEventRenderEvent(ScreenEvent.Render event) {
        if(event.getScreen() instanceof TackBoxDefaultScreen tackBoxDefaultScreen) {
            if(tackBoxDefaultScreen.getMenu().horse instanceof AbstractSteed) {
                int leftPos = (event.getScreen().width - 176) / 2;
                int topPos = (event.getScreen().height - 245) / 2;
                event.getGuiGraphics().blit(ICONS, leftPos + 127, topPos, 144, 0, 24, 22);
            }
        } else if(event.getScreen() instanceof TackBoxBirthScreen || event.getScreen() instanceof TackBoxGeneticsScreen || event.getScreen() instanceof TackBoxProgressionScreen) {
            if(((TackBoxContainer) Minecraft.getInstance().player.containerMenu).horse instanceof AbstractSteed) {
                int leftPos = (event.getScreen().width - 250) / 2;
                int topPos = (event.getScreen().height - 209) / 2;
                event.getGuiGraphics().blit(ICONS, leftPos + 127, topPos, 144, 0, 24, 22);
            }
        }
    }

    @SubscribeEvent
    public static void onScreenEventMouseButtonPressedPost(ScreenEvent.MouseButtonPressed.Post event) {
        if(event.getScreen() instanceof TackBoxDefaultScreen tackBoxDefaultScreen) {
            if(tackBoxDefaultScreen.getMenu().horse instanceof AbstractSteed) {
                int leftPos = (tackBoxDefaultScreen.width - 176) / 2;
                int topPos = (tackBoxDefaultScreen.height - 245) / 2;

                double x = event.getMouseX() - leftPos;
                double y = event.getMouseY() - topPos;

                if((127 <= x && x <= 151) && (0 <= y && y <= 22)) {
                    tackBoxDefaultScreen.getMinecraft().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
                    tackBoxDefaultScreen.getMinecraft().setScreen(new TackBoxTraitScreen(tackBoxDefaultScreen.getMenu(), Minecraft.getInstance().player.getInventory(), Component.empty()));
                }
            }
        } else if(event.getScreen() instanceof TackBoxBirthScreen || event.getScreen() instanceof TackBoxGeneticsScreen || event.getScreen() instanceof TackBoxProgressionScreen) {
            if(((TackBoxContainer) Minecraft.getInstance().player.containerMenu).horse instanceof AbstractSteed) {
                int leftPos = (event.getScreen().width - 250) / 2;
                int topPos = (event.getScreen().height - 209) / 2;

                double x = event.getMouseX() - leftPos;
                double y = event.getMouseY() - topPos;

                if((127 <= x && x <= 151) && (0 <= y && y <= 22)) {
                    event.getScreen().getMinecraft().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
                    event.getScreen().getMinecraft().setScreen(new TackBoxTraitScreen((TackBoxContainer) Minecraft.getInstance().player.containerMenu, Minecraft.getInstance().player.getInventory(), Component.empty()));
                }
            }
        }
    }
}
