package com.navi92.cutscenemod.client.screen;

import com.navi92.cutscenemod.main.CutsceneMod;
import com.navi92.cutscenemod.util.CutsceneConfig;
import com.navi92.cutscenemod.util.CutsceneReader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Objects;

public class CutscenePlayerScreen extends Screen {
    private static final Component TITLE =
            Component.translatable("gui." + CutsceneMod.MOD_ID + ".cutsceneplayer");

    private static final DecimalFormat FORMATTER = new DecimalFormat("0000");

    private final Player player;

    private final String folder;

    private final String sound;

    private final int length;

    private final int imageWidth, imageHeight;

    private final boolean moving;

    private final boolean esc;

    private ResourceLocation currentFrameTexture;

    private int currentFrame;

    private int leftPos, topPos;

    public CutscenePlayerScreen(String name, int startingFrame) {
        super(TITLE);

        this.player = Objects.requireNonNull(Minecraft.getInstance().player);
        this.currentFrame = startingFrame;

        CutsceneConfig scene = null;
        try {
            scene = CutsceneReader.readJsonConfig().get(name);
        } catch (IOException e) {
            CutsceneMod.LOGGER.error("Unable to load cutscenes.json file.");
            player.sendSystemMessage(Component.literal("Unable to load cutscenes.json file."));
            this.onClose();
        }

        if (Objects.isNull(scene)) {
            CutsceneMod.LOGGER.error("Cutscene " + name + " does not exist!");
            player.sendSystemMessage(Component.literal("Cutscene " + name + " does not exist!"));
            this.onClose();
        }

        this.sound = scene.getSound();
        this.folder = scene.getFolder();
        this.imageWidth = scene.getWidth();
        this.length = scene.getLength();
        this.imageHeight = scene.getHeight();
        this.moving = scene.isMoving();
        this.esc = scene.canEsc();
    }

    @Override
    protected void init() {
        super.init();

        this.leftPos = (this.width - (height * imageWidth) / imageHeight) / 2;
        this.topPos = 0;

        updateFrame();

        if (!sound.isEmpty()) {
            CutsceneMod.LOGGER.info("Started playing sound for: " + sound);

            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(
                    SoundEvent.createVariableRangeEvent(
                            new ResourceLocation(CutsceneMod.MOD_ID, sound)),
                    1.0F
            ));
        }
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
        return esc;
    }

    @Override
    public boolean keyPressed(int p_96552_, int p_96553_, int p_96554_) {
        if (p_96552_ == 256 && this.shouldCloseOnEsc())
            Minecraft.getInstance().getSoundManager().stop();

        return super.keyPressed(p_96552_, p_96553_, p_96554_);
    }

    public void renderBackground(@NotNull GuiGraphics guiGraphics) {
        if (this.minecraft != null) {
            guiGraphics.fill(0, 0, this.width, this.height, -16777216);
            MinecraftForge.EVENT_BUS.post(new ScreenEvent.BackgroundRendered(this, guiGraphics));
        } else {
            this.renderDirtBackground(guiGraphics);
        }
    }

    private void blit(@NotNull GuiGraphics graphics, ResourceLocation frame,
                      int x, int y, int imageWidth, int imageHeight) {
        graphics.blit(frame,
                x, y,
                (height * imageWidth) / imageHeight, height,
                0F, 0F,
                imageWidth, imageHeight,
                imageWidth, imageHeight);
    }

    private void updateFrame() {
        if (moving) {
            currentFrame++;
            if (currentFrame <= length) {
                Minecraft.getInstance().getTextureManager().release(currentFrameTexture);
                currentFrameTexture = new ResourceLocation(CutsceneMod.MOD_ID,
                        String.format(folder,
                                FORMATTER.format(currentFrame)));
            } else {
                Minecraft.getInstance().getSoundManager().stop();
                this.onClose();
            }
        } else {
            currentFrame++;
            if (currentFrame <= length) {
            currentFrameTexture = new ResourceLocation(CutsceneMod.MOD_ID, folder);
            } else {
                Minecraft.getInstance().getSoundManager().stop();
                this.onClose();
            }
        }
    }
}
