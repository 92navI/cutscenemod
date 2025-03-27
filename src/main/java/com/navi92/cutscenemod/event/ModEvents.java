package com.navi92.cutscenemod.event;

import com.navi92.cutscenemod.command.PlayCommand;
import com.navi92.cutscenemod.main.CutsceneMod;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = CutsceneMod.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
//            if (!new File(Config.cutscenes_path).exists()) {
//                event.getEntity().sendSystemMessage(Component.literal("Cutscenes.json and/or the resourcepack does not exist").withStyle(ChatFormatting.RED));
//                CutsceneMod.LOGGER.error("The config file and/or the resourcepack does not exist");
//            }
    }

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        new PlayCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }
}