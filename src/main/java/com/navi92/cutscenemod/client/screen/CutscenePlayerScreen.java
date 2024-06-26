package com.navi92.cutscenemod.client.screen;

import com.navi92.cutscenemod.CutsceneMod;
import com.navi92.cutscenemod.sound.ModSounds;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public class CutscenePlayerScreen extends Screen {
    private static final Component TITLE =
            Component.translatable("gui." + CutsceneMod.MOD_ID + ".cutsceneplayer");

    private final Player player;

    private final String folder;

    private ResourceLocation currentFrameTexture;

    private long currentFrame = 0;

    private final long maxFrames;

    private final int imageWidth, imageHeight;

    private int leftPos, topPos;

    private ImageWidget image;

    public CutscenePlayerScreen(Player player, String folder, int imageWidth, int imageHeight, long maxFrames) {
        super(TITLE);
        this.player = player;
        this.folder = "textures/cutscenes/" + folder + "/";
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.maxFrames = maxFrames;
    }

    @Override
    protected void init() {
        super.init();

        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        updateFrame();

        player.playSound(ModSounds.VIDEO_SOUND.get(), 1f, 1f);
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        renderBackground(graphics);

        blit(graphics, currentFrameTexture, leftPos, topPos, imageWidth, imageHeight);

        super.render(graphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public void tick() {
        updateFrame();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    public void renderBackground(@NotNull GuiGraphics guiGraphics) {
        if (this.minecraft.level != null) {
            guiGraphics.fill(0, 0, this.width, this.height, -16777216);
            MinecraftForge.EVENT_BUS.post(new ScreenEvent.BackgroundRendered(this, guiGraphics));
        } else {
            this.renderDirtBackground(guiGraphics);
        }

    }

    private void blit(@NotNull GuiGraphics graphics, ResourceLocation frame, int x, int y, int width, int height) {
        graphics.blit(frame,
                x, y,
                0,
                0F, 0F,
                width, height,
                width, height);
    }

    private void updateFrame() {
        currentFrame++;
        if (currentFrame <= maxFrames) {
            currentFrameTexture = new ResourceLocation(
                    CutsceneMod.MOD_ID,
                    String.format(folder + "ezgif-frame-%s.png",
                            new DecimalFormat("000").format(currentFrame)));
        } else {
            this.onClose();
        }

    }
}
