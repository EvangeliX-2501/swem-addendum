package com.evangelix.swemaddendum.gui.inventory;

import com.evangelix.swemaddendum.AddendumNetwork;
import com.evangelix.swemaddendum.SwemAddendumMain;
import com.evangelix.swemaddendum.TraitRegistrar;
import com.evangelix.swemaddendum.abstract_steed.AbstractSteed;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TraitScreen extends AbstractContainerScreen<TraitMenu> {

    public static final ResourceLocation TRAIT_GUI = new ResourceLocation(SwemAddendumMain.MODID, "gui/inventory_traits.png");
    public static final ResourceLocation ICONS = new ResourceLocation(SwemAddendumMain.MODID, "gui/icons.png");

    public final AbstractSteed abstractSteed;
    public final Component breedName;
    public final Inventory inventory;

    public float zoom = 16;
    public float xRot = 0;
    public float yRot;
    public boolean isSpinning = false;
    public int currentTick = 0;
    public int cleanlinessTick = 0;
    public int personalityTick = 0;


    public TraitScreen(TraitMenu traitMenu, Inventory inventory, Component component) {
        super(traitMenu, inventory, Component.translatable("screen.traits"));
        this.abstractSteed = traitMenu.abstractSteed;
        this.breedName = traitMenu.abstractSteed.getName();
        this.inventory = inventory;

        this.yRot = this.abstractSteed.yBodyRot;
        this.imageHeight = 222;
        this.inventoryLabelY = 128;
        this.titleLabelX = 15;
    }

    @Override
    public void containerTick() {
        super.containerTick();
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
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0, 0, 200);
        guiGraphics.fillGradient(0, 0, this.leftPos + 80, this.height, -1072689136, -804253680);
        guiGraphics.fillGradient(this.leftPos + 168, 0, this.width, this.height, -1072689136, -804253680);
        guiGraphics.fillGradient(this.leftPos + 80, 0, this.leftPos + 168, this.topPos + 16, -1072689136, -804253680);
        guiGraphics.fillGradient(this.leftPos + 80, this.topPos + 75, this.leftPos + 168, this.height, -1072689136, -804253680);
        guiGraphics.pose().popPose();
    }

    @Override
    public void renderBg(@NotNull GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        this.renderBackground(guiGraphics);

        if(this.isSpinning) {
            this.abstractSteed.yBodyRot += (1 + partialTicks);
        }

        guiGraphics.fill(this.leftPos + 81, this.topPos + 16, this.leftPos + 168, this.topPos + 75, 0xFF000000);

        if(this.abstractSteed != null) {
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(this.leftPos + 124, this.topPos + 45.5, 100);
            guiGraphics.pose().scale(this.zoom, -this.zoom, 1);
            guiGraphics.pose().rotateAround(Axis.XP.rotationDegrees(this.xRot), 0, 0, 0);
            guiGraphics.pose().rotateAround(Axis.YP.rotationDegrees(this.yRot), 0, 0, 0);
            EntityRenderDispatcher entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
            entityRenderDispatcher.render(this.abstractSteed, 0, -this.abstractSteed.getBbHeight() / 2, 0, 0, 1, guiGraphics.pose(), guiGraphics.bufferSource(), 15728880);
            guiGraphics.pose().popPose();
        }

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0, 0, 200);
        guiGraphics.blit(TRAIT_GUI, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);



        int cleanlinessIndex = this.abstractSteed.getCleanliness().ordinal();
        guiGraphics.blit(ICONS, this.leftPos + 7, this.topPos + 102 + this.cleanlinessTick, 144, 22 + 18 * cleanlinessIndex, 18, 18);

        guiGraphics.drawWordWrap(this.font, this.abstractSteed.getPersonality().description, this.leftPos + 80, this.topPos + 80, 88, 4210752);
        if(this.abstractSteed.getPersonality() != TraitRegistrar.Personality.NONE) {
            int personalityIndex = this.abstractSteed.getPersonality().ordinal();
            guiGraphics.blit(ICONS, this.leftPos + 7 + (personalityIndex % 3) * 21, this.topPos + 16 + (personalityIndex / 3) * 21, this.personalityTick, 18 * personalityIndex, 18, 18);

            if(this.isHovering(7, 16, 18, 18, mouseX, mouseY)) {
                guiGraphics.renderTooltip(this.font, TraitRegistrar.Personality.VICIOUS.name, mouseX, mouseY);
            } else if(this.isHovering(28, 16, 18, 18, mouseX, mouseY)) {
                guiGraphics.renderTooltip(this.font, TraitRegistrar.Personality.DOWN_TRODDEN.name, mouseX, mouseY);
            } else if(this.isHovering(49, 16, 18, 18, mouseX, mouseY)) {
                guiGraphics.renderTooltip(this.font, TraitRegistrar.Personality.WILD.name, mouseX, mouseY);
            } else if(this.isHovering(7, 37, 18, 18, mouseX, mouseY)) {
                guiGraphics.renderTooltip(this.font, TraitRegistrar.Personality.ENERGETIC.name, mouseX, mouseY);
            } else if(this.isHovering(28, 37, 18, 18, mouseX, mouseY)) {
                guiGraphics.renderTooltip(this.font, TraitRegistrar.Personality.SLOTHFUL.name, mouseX, mouseY);
            } else if(this.isHovering(49, 37, 18, 18, mouseX, mouseY)) {
                guiGraphics.renderTooltip(this.font, TraitRegistrar.Personality.GLUTTONOUS.name, mouseX, mouseY);
            } else if(this.isHovering(7, 58, 18, 18, mouseX, mouseY)) {
                guiGraphics.renderTooltip(this.font, TraitRegistrar.Personality.BRAVE.name, mouseX, mouseY);
            } else if(this.isHovering(28, 58, 18, 18, mouseX, mouseY)) {
                guiGraphics.renderTooltip(this.font, TraitRegistrar.Personality.BOUNCY.name, mouseX, mouseY);
            } else if(this.isHovering(49, 58, 18, 18, mouseX, mouseY)) {
                guiGraphics.renderTooltip(this.font, TraitRegistrar.Personality.STUBBORN.name, mouseX, mouseY);
            } else if(this.isHovering(7, 103, 18, 18, mouseX, mouseY)) {
                guiGraphics.renderTooltip(this.font, this.abstractSteed.getCleanliness().name, mouseX, mouseY);
            }
        }

        guiGraphics.pose().popPose();
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double direction) {
        double x = mouseX - this.leftPos;
        double y = mouseY - this.topPos;

        if((80 < x && x < 168) && (16 < y && y < 76)) {
            float zoom = this.zoom + (float)direction;
            this.zoom = zoom < 4 ? 4 : zoom;
        }
        return super.mouseScrolled(mouseX, mouseY, direction);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int keyCode) {
        if(this.isHovering(80, 16, 88, 60, mouseX, mouseY)) {
            if(keyCode == InputConstants.MOUSE_BUTTON_MIDDLE) {
                this.isSpinning = !this.isSpinning;
            }
        }

        if(keyCode == InputConstants.MOUSE_BUTTON_LEFT) {
            TraitRegistrar.Personality personality = this.abstractSteed.getPersonality();

            if(this.isHovering(7, 16, 18, 18, mouseX, mouseY)) {
                this.getMinecraft().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
                AddendumNetwork.INSTANCE.sendToServer(new AddendumNetwork.SetPersonalityRequest(this.abstractSteed.getId(), TraitRegistrar.Personality.VICIOUS == personality ? TraitRegistrar.Personality.NONE : TraitRegistrar.Personality.VICIOUS));
            } else if(this.isHovering(28, 16, 18, 18, mouseX, mouseY)) {
                this.getMinecraft().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
                AddendumNetwork.INSTANCE.sendToServer(new AddendumNetwork.SetPersonalityRequest(this.abstractSteed.getId(), TraitRegistrar.Personality.DOWN_TRODDEN == personality ? TraitRegistrar.Personality.NONE : TraitRegistrar.Personality.DOWN_TRODDEN));
            } else if(this.isHovering(49, 16, 18, 18, mouseX, mouseY)) {
                this.getMinecraft().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
                AddendumNetwork.INSTANCE.sendToServer(new AddendumNetwork.SetPersonalityRequest(this.abstractSteed.getId(), TraitRegistrar.Personality.WILD == personality ? TraitRegistrar.Personality.NONE : TraitRegistrar.Personality.WILD));
            } else if(this.isHovering(7, 37, 18, 18, mouseX, mouseY)) {
                this.getMinecraft().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
                AddendumNetwork.INSTANCE.sendToServer(new AddendumNetwork.SetPersonalityRequest(this.abstractSteed.getId(), TraitRegistrar.Personality.ENERGETIC == personality ? TraitRegistrar.Personality.NONE : TraitRegistrar.Personality.ENERGETIC));
            } else if(this.isHovering(28, 37, 18, 18, mouseX, mouseY)) {
                this.getMinecraft().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
                AddendumNetwork.INSTANCE.sendToServer(new AddendumNetwork.SetPersonalityRequest(this.abstractSteed.getId(), TraitRegistrar.Personality.SLOTHFUL == personality ? TraitRegistrar.Personality.NONE : TraitRegistrar.Personality.SLOTHFUL));
            } else if(this.isHovering(49, 37, 18, 18, mouseX, mouseY)) {
                this.getMinecraft().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
                AddendumNetwork.INSTANCE.sendToServer(new AddendumNetwork.SetPersonalityRequest(this.abstractSteed.getId(), TraitRegistrar.Personality.GLUTTONOUS == personality ? TraitRegistrar.Personality.NONE : TraitRegistrar.Personality.GLUTTONOUS));
            } else if(this.isHovering(7, 58, 18, 18, mouseX, mouseY)) {
                this.getMinecraft().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
                AddendumNetwork.INSTANCE.sendToServer(new AddendumNetwork.SetPersonalityRequest(this.abstractSteed.getId(), TraitRegistrar.Personality.BRAVE == personality ? TraitRegistrar.Personality.NONE : TraitRegistrar.Personality.BRAVE));
            } else if(this.isHovering(28, 58, 18, 18, mouseX, mouseY)) {
                this.getMinecraft().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
                AddendumNetwork.INSTANCE.sendToServer(new AddendumNetwork.SetPersonalityRequest(this.abstractSteed.getId(), TraitRegistrar.Personality.BOUNCY == personality ? TraitRegistrar.Personality.NONE : TraitRegistrar.Personality.BOUNCY));
            } else if(this.isHovering(49, 58, 18, 18, mouseX, mouseY)) {
                this.getMinecraft().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
                AddendumNetwork.INSTANCE.sendToServer(new AddendumNetwork.SetPersonalityRequest(this.abstractSteed.getId(), TraitRegistrar.Personality.STUBBORN == personality ? TraitRegistrar.Personality.NONE : TraitRegistrar.Personality.STUBBORN));
            } else if(this.isHovering(7, 103, 18, 18, mouseX, mouseY)) {
                this.getMinecraft().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
                AddendumNetwork.INSTANCE.sendToServer(new AddendumNetwork.SetCleanlinessRequest(this.abstractSteed.getId(), TraitRegistrar.getNext(this.abstractSteed.getCleanliness())));
            }
        }

        return super.mouseClicked(mouseX, mouseY, keyCode);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int keyCode, double xDir, double yDir) {
        double x = mouseX - this.leftPos;
        double y = mouseY - this.topPos;

        if(keyCode == InputConstants.MOUSE_BUTTON_LEFT && (80 < x && x < 168) && (16 < y && y < 76)) {
            float xRot = this.xRot + (float)yDir;
            xRot = Math.abs(xRot) > 90 ? 90 * Math.signum(xRot) : xRot;

            this.xRot = xRot;
            this.yRot += xDir;
        }

        return super.mouseDragged(mouseX, mouseY, keyCode, xDir, yDir);
    }

    @Override
    public void renderLabels(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0, 0, 200);
        super.renderLabels(guiGraphics, mouseX, mouseY);
        guiGraphics.drawString(this.font, this.breedName, 81, 6, 4210752, false);
        guiGraphics.pose().popPose();
    }

    @Override
    public boolean isHovering(int ox, int oy, int w, int h, double mouseX, double mouseY) {
        double x = mouseX - this.leftPos;
        double y = mouseY - this.topPos;

        return ((ox < x && x < ox + w) && (oy < y && y < oy + h));
    }

    @Override
    public boolean hasClickedOutside(double mouseX, double mouseY, int width, int height, int keyCode) {
        return super.hasClickedOutside(mouseX, mouseY, width, height, keyCode);
    }

    @Override
    public void slotClicked(Slot slot, int x, int y, ClickType clickType) {
        super.slotClicked(slot, x, y, clickType);
    }

    @Override
    public boolean checkHotbarKeyPressed(int x, int y) {
        return super.checkHotbarKeyPressed(x, y);
    }
}
