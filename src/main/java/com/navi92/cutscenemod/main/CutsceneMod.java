package com.navi92.cutscenemod.main;

import com.mojang.logging.LogUtils;
import com.navi92.cutscenemod.networking.PacketHandler;
import com.navi92.cutscenemod.networking.PacketHandler1;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.event.Level;

@Mod(CutsceneMod.MOD_ID)
public class CutsceneMod {
    public static final String MOD_ID = "cutscenemod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CutsceneMod() {
        LogUtils.configureRootLoggingLevel(Level.DEBUG);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        PacketHandler.register();
        PacketHandler1.register();
    }

}
