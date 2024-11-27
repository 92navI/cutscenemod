package com.navi92.cutscenemod.util;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import it.unimi.dsi.fastutil.Hash;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;

//@Getter
public class ConfigReader {

    private static final Gson GSON = new Gson();

    private static final Type TYPE = new TypeToken<HashMap<String, CutsceneConfig>>() {}.getType();

    public static HashMap<String, CutsceneConfig> readJsonConfig() throws IOException {
        try (JsonReader reader = new JsonReader(
                new FileReader("./resourcepacks/cutscenepack/assets/cutscenemod/cutscenes/cutscenes.json"));) {
            return GSON.fromJson(reader, TYPE);
        }
    }
}
