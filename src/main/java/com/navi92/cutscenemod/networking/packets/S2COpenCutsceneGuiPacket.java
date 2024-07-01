package com.navi92.cutscenemod.networking.packets;

import com.navi92.cutscenemod.client.ClientHooks;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2COpenCutsceneGuiPacket {
    private final String name;
    private final int imageWidth;
    private final int imageHeight;
    private final int maxFrames;

    public S2COpenCutsceneGuiPacket(String name, int imageWidth, int imageHeight, int maxFrames) {
        this.name = name;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.maxFrames = maxFrames;
    }

    public S2COpenCutsceneGuiPacket(FriendlyByteBuf buffer) {
        this(buffer.readUtf(), buffer.readInt(), buffer.readInt(), buffer.readInt());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUtf(name);
        buffer.writeInt(imageWidth);
        buffer.writeInt(imageHeight);
        buffer.writeInt(maxFrames);
    }

    public void handle (Supplier<NetworkEvent.Context> supplier) {
        var context = supplier.get();

        context.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                    ClientHooks.openCutscenePlayerScreen(
                            name,
                            imageWidth,
                            imageHeight,
                            maxFrames));
        });

        context.setPacketHandled(true);
    }
}
