package com.navi92.cutscenemod.sound;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.navi92.cutscenemod.CutsceneMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

public class ModSounds {

    private static final Gson GSON = new Gson();

    private static final Type TYPE = new TypeToken<HashMap<String, Object>>() {
    }.getType();

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, CutsceneMod.MOD_ID);

    public static HashMap<String, RegistryObject<SoundEvent>> sounds = new HashMap<>();

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(
                new ResourceLocation(CutsceneMod.MOD_ID, name)));
    }


    public static void register(IEventBus eventBus) {

        try (JsonReader reader = new JsonReader(
                new FileReader("./resourcepacks/cutscenepack/assets/cutscenemod/cutscenes/cutscenes.json"));) {

            HashMap<String, Object> map = GSON.fromJson(reader, TYPE);
            for (String name : map.keySet()) {
                sounds.put(name, registerSoundEvents(name));
            }

        } catch (IOException e) {
            CutsceneMod.LOGGER.error("Failed to load video sounds: Resourcepack not present");
        }

        SOUND_EVENTS.register(eventBus);
    }
}
