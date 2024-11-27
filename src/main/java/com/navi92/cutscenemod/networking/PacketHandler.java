package com.navi92.cutscenemod.networking;

import com.navi92.cutscenemod.CutsceneMod;
import com.navi92.cutscenemod.networking.packets.S2COpenCutsceneGuiPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void register() {
        INSTANCE = NetworkRegistry.ChannelBuilder.named(
                        new ResourceLocation(CutsceneMod.MOD_ID, "main"))
                .serverAcceptedVersions(a -> true)
                .clientAcceptedVersions(a -> true)
                .networkProtocolVersion(() -> "1.0")
                .simpleChannel();

        INSTANCE.messageBuilder(S2COpenCutsceneGuiPacket.class, id())
                .encoder(S2COpenCutsceneGuiPacket::encode)
                .decoder(S2COpenCutsceneGuiPacket::new)
                .consumerMainThread(S2COpenCutsceneGuiPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToAllPlayers(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
}
