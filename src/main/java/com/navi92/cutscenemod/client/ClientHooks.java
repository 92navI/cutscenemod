package com.navi92.cutscenemod.client;

import com.navi92.cutscenemod.client.screen.CutscenePlayerScreen;
import com.navi92.cutscenemod.util.CutsceneReader;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.io.IOException;

public class ClientHooks {
    public static void openCutscenePlayerScreen(String name) {
        Minecraft.getInstance()
                .setScreen(new CutscenePlayerScreen(name, 0));
    }

    public static void listCutscenes() {
        try {
            Minecraft.getInstance().player.sendSystemMessage(Component.literal(CutsceneReader.readJsonConfig().keySet().toString()));
        } catch (Exception e) {
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("An error occurred!"));
        }
    }
}
