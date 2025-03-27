package com.navi92.cutscenemod.networking.packets;

import com.navi92.cutscenemod.client.ClientHooks;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2CListCutscenesPacket {

    public S2CListCutscenesPacket() {
    }

    public S2CListCutscenesPacket(FriendlyByteBuf buffer) {
    }

    public void encode(FriendlyByteBuf buffer) {
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        var context = supplier.get();

        context.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ClientHooks::listCutscenes);
        });

        context.setPacketHandled(true);
    }
}
