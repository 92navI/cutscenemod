package com.navi92.cutscenemod.item;

import com.navi92.cutscenemod.CutsceneMod;
import com.navi92.cutscenemod.item.custom.VideoViewerItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CutsceneMod.MOD_ID);

    public static final RegistryObject<Item> CUTSCENE_VIEWER = ITEMS.register(
            "cutsceneviewer",
            () -> new VideoViewerItem(new Item.Properties()));

    public static void register (IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
