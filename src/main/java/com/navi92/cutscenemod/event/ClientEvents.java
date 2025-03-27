package com.navi92.cutscenemod.event;

import com.navi92.cutscenemod.client.screen.CutscenePlayerScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ClientEvents {
    private static boolean isInitial = true;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getInstance();

        if (isInitial && mc.player != null && mc.level != null) {
            isInitial = false;

            CompoundTag persistentData = mc.player.getPersistentData();
            if (!persistentData.getBoolean("hasJoined")) {
                persistentData.putBoolean("hasJoined", true);

                Minecraft.getInstance().setScreen(new CutscenePlayerScreen("intro", 0));
            }
        }
    }
}