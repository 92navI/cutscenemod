package com.navi92.cutscenemod.util;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.navi92.cutscenemod.main.CutsceneMod;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CutsceneReader {

    private static final Gson GSON = new Gson();

    private static final TypeToken<Map<String, CutsceneConfig>> TYPE_TOKEN = new TypeToken<>() {
    };

    private static final Type TYPE = new TypeToken<HashMap<String, CutsceneConfig>>() {
    }.getType();

    public static Map<String, CutsceneConfig> readJsonConfig() throws IOException {
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        ResourceLocation resourceLocation = new ResourceLocation(CutsceneMod.MOD_ID, "cutscenes.json");

        Optional<Resource> optionalResource = resourceManager.getResource(resourceLocation);
        if (optionalResource.isPresent()) {
            InputStreamReader reader = new InputStreamReader(optionalResource.get().open(), StandardCharsets.UTF_8);

            Map<String, CutsceneConfig> map = GSON.fromJson(reader, TYPE_TOKEN.getType());

            reader.close();
            return map;
        } else {
            CutsceneMod.LOGGER.error("Resource not found. Try running in client side.");
        }

        return null;
    }
}
