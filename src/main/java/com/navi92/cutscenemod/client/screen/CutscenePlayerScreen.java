package com.navi92.cutscenemod.client.screen;

import com.navi92.cutscenemod.main.CutsceneMod;
import com.navi92.cutscenemod.sound.ModSounds;
import com.navi92.cutscenemod.util.ConfigReader;
import com.navi92.cutscenemod.util.CutsceneConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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

    private final Player player;

    private final String folder;

    private final String sound;

    private final int length;

    private final int imageWidth, imageHeight;

    private ResourceLocation currentFrameTexture;

    private long currentFrame = 0;

    private int leftPos, topPos;

    public CutscenePlayerScreen(String name) {
        super(TITLE);

        this.player = Objects.requireNonNull(Minecraft.getInstance().player);

        CutsceneConfig scene = null;
        try {
            scene = ConfigReader.readJsonConfig().get(name);
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
    }

    @Override
    protected void init() {
        super.init();

        this.leftPos = (this.width - (height * imageWidth) / imageHeight) / 2;
        this.topPos = 0;

        updateFrame();

        if (Minecraft.getInstance().player != null) {
            CutsceneMod.LOGGER.info("Started playing sound for: " + sound);
            Player player = Minecraft.getInstance().player;
            player.playSound(ModSounds.sounds.get(sound).get(), 1f, 1f);
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
    public void onClose() {
        Minecraft.getInstance().getSoundManager().stop();
        super.onClose();
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
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
                0,
                0F, 0F,
                imageWidth, imageHeight,
                (height * imageWidth) / imageHeight, height);
    }

    private void updateFrame() {
        currentFrame++;
        if (currentFrame <= length) {
            currentFrameTexture = new ResourceLocation(CutsceneMod.MOD_ID,
                    String.format(folder + "frames/ezgif-frame-%s.png",
                            new DecimalFormat("000").format(currentFrame)));
        } else {
            this.onClose();
        }
    }
}
