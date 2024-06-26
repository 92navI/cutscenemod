package com.navi92.cutscenemod.client;

import com.navi92.cutscenemod.client.screen.CutscenePlayerScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class ClientHooks {
    public static void openCutscenePlayerScreen(Player player, String folder,
                                                int imageWidth, int imageHeight, long maxFrames) {
        Minecraft.getInstance()
                .setScreen(new CutscenePlayerScreen(player, folder, imageWidth, imageHeight, maxFrames));
    }
}
