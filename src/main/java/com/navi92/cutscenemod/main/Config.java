package com.navi92.cutscenemod.main;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = CutsceneMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.ConfigValue<String> CUTSCENES_PATH = BUILDER
            .comment("The velocity of a blaster charge")
            .define("cutscenes_path", "./resourcepacks/cutscenepack/assets/cutscenemod/cutscenes/");

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static String cutscenes_path;

    private static boolean validateItemName(final Object obj) {
        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {

        cutscenes_path = CUTSCENES_PATH.get();
    }
}
