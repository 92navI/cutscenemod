package com.navi92.cutscenemod.networking.packets;

import com.navi92.cutscenemod.client.ClientHooks;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2COpenCutsceneGuiPacket {
    private final String name;

    public S2COpenCutsceneGuiPacket(String name) {
        this.name = name;
    }

    public S2COpenCutsceneGuiPacket(FriendlyByteBuf buffer) {
        this(buffer.readUtf());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUtf(name);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        var context = supplier.get();

        context.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                    ClientHooks.openCutscenePlayerScreen(name));
        });

        context.setPacketHandled(true);
    }
}
