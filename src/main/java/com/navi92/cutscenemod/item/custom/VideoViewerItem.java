package com.navi92.cutscenemod.item.custom;

import com.navi92.cutscenemod.CutsceneMod;
import com.navi92.cutscenemod.client.ClientHooks;
import com.navi92.cutscenemod.networking.PacketHandler;
import com.navi92.cutscenemod.networking.packets.S2COpenCutsceneGuiPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class VideoViewerItem extends Item {

    public VideoViewerItem(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        if (!pLevel.isClientSide()) {
            if (pHand.equals(InteractionHand.MAIN_HAND)) {
                var tag = pPlayer.getItemInHand(pHand).getTag();
                if (Objects.nonNull(tag)) {
                    pPlayer.getCooldowns().addCooldown(this, 50);
                    var name = tag.getString("scene");

                    CutsceneMod.LOGGER.info("Playing cutscene " + name + " for player " + pPlayer.getName());

                    PacketHandler.sendToPlayer(
                            new S2COpenCutsceneGuiPacket(name),
                            pPlayer.getServer().getPlayerList().getPlayer(pPlayer.getUUID()));

                } else {
                    CutsceneMod.LOGGER.info("No tag available");
                    pPlayer.sendSystemMessage(Component.literal("No tag available"));
                }
            }
        }

        return super.use(pLevel, pPlayer, pHand);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
    }
}