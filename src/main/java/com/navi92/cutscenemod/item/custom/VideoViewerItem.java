package com.navi92.cutscenemod.item.custom;

import com.navi92.cutscenemod.client.ClientHooks;
import com.navi92.cutscenemod.utils.FileImporter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class VideoViewerItem extends Item {

    public VideoViewerItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (pContext.getLevel().isClientSide) {
            if (pContext.getHand().equals(InteractionHand.MAIN_HAND)) {
                pContext.getPlayer().sendSystemMessage(Component.literal("Item 'Cutscene Viewer' was used!"));
//                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
//                        ClientHooks.openCutscenePlayerScreen(
//                                pContext.getPlayer(),
//                                "rick",
//                                854,
//                                480,
//                                200));
                FileImporter.importFrames("C:\\Users\\ikidu\\Downloads\\Telegram Desktop\\rick.mp4",
                        "main\\resources\\assets\\cutscenemod\\textures\\cutscenes");
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }
}