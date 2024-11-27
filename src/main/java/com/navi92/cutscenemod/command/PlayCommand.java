package com.navi92.cutscenemod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.navi92.cutscenemod.CutsceneMod;
import com.navi92.cutscenemod.networking.PacketHandler;
import com.navi92.cutscenemod.networking.packets.S2COpenCutsceneGuiPacket;
import com.navi92.cutscenemod.util.ConfigReader;
import com.navi92.cutscenemod.util.CutsceneConfig;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

public class PlayCommand {

    public PlayCommand(@NotNull CommandDispatcher<CommandSourceStack> dispatcher) {
        var literalcommandnode = dispatcher.register(Commands.literal("cutscenes")
                .then(Commands.literal("play")
                        .then(Commands.argument("name", StringArgumentType.word())
                                .executes(this::playVideo)
                                .then(Commands.argument("players", EntityArgument.players())
                                        .executes(this::playVideoFor)
                                )
                        )
                ).then(Commands.literal("list").executes(this::listCutscenes))
        );
        dispatcher.register(Commands.literal("cs").redirect(literalcommandnode));
    }

    private int playVideoFor(@NotNull CommandContext<CommandSourceStack> command) throws CommandSyntaxException {
        var video_name = StringArgumentType.getString(command, "name");

        Collection<ServerPlayer> player_names = EntityArgument.getPlayers(command, "players");
        player_names.forEach((player) -> sendPlayVideoPacket(player, video_name));

        return 0;
    }


    private int playVideo(@NotNull CommandContext<CommandSourceStack> command) {
        var player = command.getSource().getPlayer();
        var video_name = StringArgumentType.getString(command, "name");

        if (player != null) {
            sendPlayVideoPacket(player, video_name);
        }

        return 0;
    }

    private int listCutscenes(@NotNull CommandContext<CommandSourceStack> command) {
        try {
        command.getSource().sendSystemMessage(Component.literal(ConfigReader.readJsonConfig().keySet().toString()));
        } catch (IOException e) {
            CutsceneMod.LOGGER.error("Unable to load cutscenes.json file.");
        }

        return 0;
    }

    private static void sendPlayVideoPacket(@NotNull ServerPlayer player, String video_name) {
        CutsceneMod.LOGGER.info("Playing cutscene " + video_name + " for player " + player.getName());

        PacketHandler.sendToPlayer(
                new S2COpenCutsceneGuiPacket(video_name),
                player);
    }
}
