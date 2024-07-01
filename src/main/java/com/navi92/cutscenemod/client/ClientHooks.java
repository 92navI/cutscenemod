package com.navi92.cutscenemod.client;

import com.navi92.cutscenemod.client.screen.CutscenePlayerScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class ClientHooks {
    public static void openCutscenePlayerScreen(String name,
                                                int imageWidth, int imageHeight, int maxFrames) {
        Minecraft.getInstance()
                .setScreen(new CutscenePlayerScreen(name, imageWidth, imageHeight, maxFrames));
    }
}
