package com.navi92.cutscenemod.item;

import com.navi92.cutscenemod.CutsceneMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CutsceneMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> CUTSCENES_TAB = CREATIVE_MODE_TABS.register(
            "cutscenes_tab",
            ()-> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.CUTSCENE_VIEWER.get()))
                    .title(Component.translatable("creativetab."+CutsceneMod.MOD_ID+".cutscenes_tab"))
                    .displayItems(((itemDisplayParameters, output) -> {
                        output.accept(ModItems.CUTSCENE_VIEWER.get());

                    })).build());

    public static void register (IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
