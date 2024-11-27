package com.navi92.cutscenemod;

import com.mojang.logging.LogUtils;
import com.navi92.cutscenemod.command.PlayCommand;
import com.navi92.cutscenemod.item.ModCreativeModeTabs;
import com.navi92.cutscenemod.item.ModItems;
import com.navi92.cutscenemod.networking.PacketHandler;
import com.navi92.cutscenemod.sound.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.server.command.ConfigCommand;
import org.slf4j.Logger;
import org.slf4j.event.Level;

import java.io.File;

@Mod(CutsceneMod.MOD_ID)
public class CutsceneMod {
    public static final String MOD_ID = "cutscenemod";
    public static final Logger LOGGER = LogUtils.getLogger();
    public CutsceneMod() {
        LogUtils.configureRootLoggingLevel(Level.DEBUG);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);
        ModSounds.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {});
        PacketHandler.register();
    }

    public void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.CUTSCENE_VIEWER);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {}

    @Mod.EventBusSubscriber(modid = MOD_ID)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onPlayerLoggerInEvent(PlayerEvent.PlayerLoggedInEvent event) {
            if (!new File("./resourcepacks/cutscenepack/assets/cutscenemod/cutscenes/cutscenes.json").exists()) {
                event.getEntity().sendSystemMessage(Component.literal("Cutscenes.json and/or the resourcepack does not exist").withStyle(ChatFormatting.RED));
                CutsceneMod.LOGGER.error("The config file and/or the resourcepack does not exist");
            }
        }

        @SubscribeEvent
        public static void onCommandsRegister(RegisterCommandsEvent event) {
            new PlayCommand(event.getDispatcher());

            ConfigCommand.register(event.getDispatcher());
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {}
    }
}
