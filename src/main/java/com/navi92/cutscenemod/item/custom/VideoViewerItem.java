package com.navi92.cutscenemod.item.custom;

import com.navi92.cutscenemod.client.ClientHooks;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class VideoViewerItem extends Item {

    public VideoViewerItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        if (pContext.getLevel().isClientSide) {
            if (pContext.getHand().equals(InteractionHand.MAIN_HAND)) {
                Objects.requireNonNull(pContext.getPlayer()).sendSystemMessage(Component.literal("Item 'Cutscene Viewer' was used!"));
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                        ClientHooks.openCutscenePlayerScreen(
                                "rick",
                                854,
                                480,
                                200));
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }
}