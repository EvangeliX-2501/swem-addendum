package com.evangelix.swemaddendum.gui.tackbox;

import com.alaharranhonor.swem.forge.client.gui.screens.TackBoxBirthScreen;
import com.alaharranhonor.swem.forge.client.gui.screens.TackBoxDefaultScreen;
import com.alaharranhonor.swem.forge.client.gui.screens.TackBoxGeneticsScreen;
import com.alaharranhonor.swem.forge.client.gui.screens.TackBoxProgressionScreen;
import com.alaharranhonor.swem.forge.container.TackBoxContainer;
import com.evangelix.swemaddendum.AddendumNetwork;
import com.evangelix.swemaddendum.SwemAddendumMain;
import com.evangelix.swemaddendum.TraitRegistrar;
import com.evangelix.swemaddendum.abstract_steed.AbstractSteed;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;

public class TackBoxTraitScreen extends Screen {

    public static final ResourceLocation TACKBOX_TRAIT_TEXTURE = new ResourceLocation(SwemAddendumMain.MODID, "gui/tackbox_traits.png");
    public static final ResourceLocation BACKGROUND = new ResourceLocation(SwemAddendumMain.MODID, "gui/tackbox_background.png");
    public static final ResourceLocation ICONS = new ResourceLocation(SwemAddendumMain.MODID, "gui/icons.png");

    public final TackBoxContainer menu;
    public final Inventory inventory;
    public int leftPos;
    public int topPos;
    public final int imageWidth;
    public final int imageHeight;

    public final AbstractSteed abstractSteed;
    public float zoom = 30;
    public float xRot = 0;
    public float yRot;
    public boolean isSpinning = false;
    public int currentTick = 0;
    public int personalityTick = 0;
    public int cleanlinessTick = 0;

    public TackBoxTraitScreen(TackBoxContainer tackBoxContainer, Inventory inventory, Component component) {
        super(tackBoxContainer.horse == null ? Component.empty() : tackBoxContainer.horseName);
        this.menu = tackBoxContainer;
        this.inventory = inventory;
        this.imageWidth = 250;
        this.imageHeight = 209;

        this.abstractSteed = (AbstractSteed) tackBoxContainer.horse;
        if(this.abstractSteed != null) {
            this.yRot = this.abstractSteed.yBodyRot;
        }
    }

    @Override
    public void init() {
        super.init();
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0, 0, 200);
        guiGraphics.fillGradient(0, 0, this.leftPos + 10, this.height, -1072689136, -804253680);
        guiGraphics.fillGradient(this.leftPos + 215, 0, this.width, this.height, -1072689136, -804253680);
        guiGraphics.fillGradient(this.leftPos + 10, 0, this.leftPos + 215, this.topPos + 38, -1072689136, -804253680);
        guiGraphics.fillGradient(this.leftPos + 10, this.topPos + 172, this.leftPos + 215, this.height, -1072689136, -804253680);
        guiGraphics.pose().popPose();
    }

    @Override
    public void tick() {
        if(this.currentTick % 2 == 0) {
            this.personalityTick = (this.personalityTick + 18) % 144;
        }

        if(this.currentTick % 24 == 0) {
            this.cleanlinessTick = (this.cleanlinessTick + 1) % 2;
        }

        this.currentTick++;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        if(this.isSpinning) {
            this.yRot += (1 + partialTick);
        }

        guiGraphics.blit(BACKGROUND, this.leftPos + 11, this.topPos + 38, 0, 0, 240, 133);

        if(this.abstractSteed != null) {
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(this.leftPos + 114.5, this.topPos + 105.5, 100);
            guiGraphics.pose().scale(this.zoom, -this.zoom, 1);
            guiGraphics.pose().rotateAround(Axis.XP.rotationDegrees(this.xRot), 0, 0, 0);
            guiGraphics.pose().rotateAround(Axis.YP.rotationDegrees(this.yRot), 0, 0, 0);
            EntityRenderDispatcher entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
            entityRenderDispatcher.render(this.abstractSteed, 0, -this.abstractSteed.getBbHeight() / 2, 0, 0, 1, guiGraphics.pose(), guiGraphics.bufferSource(), 15728880);
            guiGraphics.pose().popPose();
        }

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0, 0, 200);
        guiGraphics.blit(TACKBOX_TRAIT_TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        if(this.abstractSteed != null) {
            int cleanlinessIndex = this.abstractSteed.getCleanliness().ordinal() * 18;
            guiGraphics.blit(ICONS, this.leftPos + 10, this.topPos + 182 + this.cleanlinessTick, 144, 22 + cleanlinessIndex, 18, 18);
            guiGraphics.drawWordWrap(this.font, this.abstractSteed.getPersonality().description, this.leftPos + 48, this.topPos + 175, 168, 4210752);

            double x = mouseX - this.leftPos;
            double y = mouseY - this.topPos;
            if(this.abstractSteed.getPersonality() != TraitRegistrar.Personality.NONE) {
                int personalityIndex = this.abstractSteed.getPersonality().ordinal() * 18;
                guiGraphics.blit(ICONS, this.leftPos + 221, this.topPos + 38 + personalityIndex, this.personalityTick, personalityIndex, 18, 18);

                if((221 <= x && x <= 238) && (38 <= y && y <= 199)) {
                    int index = (int)((y - 38) / 18);
                    TraitRegistrar.Personality personality = TraitRegistrar.Personality.values()[index];
                    guiGraphics.renderTooltip(this.font, personality.name, mouseX, mouseY);
                }
            }

            if((10 <= x && x <= 28) && (182 <= y && y <= 199)) {
                guiGraphics.renderTooltip(this.font, this.abstractSteed.getCleanliness().name, mouseX, mouseY);
            }
        }

        guiGraphics.pose().popPose();
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double direction) {
        double x = mouseX - this.leftPos;
        double y = mouseY - this.topPos;

        if((10 <= x && x <= 215) && (38 <= y && y <= 172)) {
            float zoom = this.zoom + (float)direction;
            this.zoom = zoom < 1 ? 1 : zoom;
        }
        return super.mouseScrolled(mouseX, mouseY, direction);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int keyCode) {
        double x = mouseX - this.leftPos;
        double y = mouseY - this.topPos;

        if(keyCode == InputConstants.MOUSE_BUTTON_MIDDLE && (10 <= x && x <= 215 && 38 <= y && y <= 172)) {
            this.isSpinning = !this.isSpinning;
        }

        if(keyCode == InputConstants.MOUSE_BUTTON_LEFT) {
            if (this.abstractSteed != null) {
                if ((221 <= x && x <= 238) && (38 <= y && y <= 199)) {
                    int index = (int) ((y - 38) / 18);
                    TraitRegistrar.Personality currentPersonality = this.abstractSteed.getPersonality();
                    TraitRegistrar.Personality clickedPersonality = TraitRegistrar.Personality.values()[index];
                    this.getMinecraft().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
                    AddendumNetwork.INSTANCE.sendToServer(new AddendumNetwork.SetPersonalityRequest(this.abstractSteed.getId(), currentPersonality == clickedPersonality ? TraitRegistrar.Personality.NONE : clickedPersonality));
                }

                if ((10 <= x && x <= 28) && (182 <= y && y <= 199)) {
                    this.getMinecraft().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
                    AddendumNetwork.INSTANCE.sendToServer(new AddendumNetwork.SetCleanlinessRequest(this.abstractSteed.getId(), TraitRegistrar.getNext(this.abstractSteed.getCleanliness())));
                }
            }

            if (0 <= y && y <= 22) {
                if (3 <= x && x <= 27) {
                    this.getMinecraft().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
                    this.getMinecraft().setScreen(new TackBoxDefaultScreen(this.menu, this.inventory, Component.empty()));
                    return true;
                } else if (34 <= x && x <= 56) {
                    this.getMinecraft().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
                    this.getMinecraft().setScreen(new TackBoxBirthScreenProxy(this.menu, this.inventory, Component.translatable("container.swem.tack_box_certificate")));
                    return true;
                } else if (65 <= x && x <= 87) {
                    this.getMinecraft().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
                    this.getMinecraft().setScreen(new TackBoxGeneticsScreenProxy(this.menu, this.inventory, Component.translatable("container.swem.tack_box_genetics")));
                    return true;
                } else if (96 <= x && x <= 118) {
                    this.getMinecraft().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
                    this.getMinecraft().setScreen(new TackBoxProgressionScreen(this.menu, this.inventory, Component.translatable("container.swem.tack_box_progression")));
                    return true;
                } else if (127 <= x && x <= 151) {
                    this.getMinecraft().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, keyCode);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int keyCode, double xDir, double yDir) {
        double x = mouseX - this.leftPos;
        double y = mouseY - this.topPos;

        if(keyCode == InputConstants.MOUSE_BUTTON_LEFT && (10 <= x && x <= 215) && (38 <= y && y <= 172)) {
            float xRot = this.xRot + (float)yDir;
            xRot = Math.abs(xRot) > 90 ? 90 * Math.signum(xRot) : xRot;

            this.xRot = xRot;
            this.yRot += xDir;
        }

        return super.mouseDragged(mouseX, mouseY, keyCode, xDir, yDir);
    }
}
