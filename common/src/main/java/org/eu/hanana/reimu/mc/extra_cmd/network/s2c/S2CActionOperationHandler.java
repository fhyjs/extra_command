package org.eu.hanana.reimu.mc.extra_cmd.network.s2c;

import dev.architectury.networking.NetworkManager;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.InteractionHand;

import java.lang.management.MemoryUsage;

public class S2CActionOperationHandler {
    public static void receive(S2CActionOperationPayload payload, NetworkManager.PacketContext context) {
        if (payload.operation().equals("jump")){
            Minecraft.getInstance().player.jumpFromGround();
        }else if (payload.operation().equals("hotbar")){
            Minecraft.getInstance().player.getInventory().selected= Integer.parseInt(payload.data());
        }else if (payload.operation().equals("throw")){
            int i = Integer.parseInt(payload.data());
            var player = Minecraft.getInstance().player;
            for (int i1 = 0; i1 < i; i1++) {
                if (!player.isSpectator() && player.drop(false)) {
                    player.swing(InteractionHand.MAIN_HAND);
                }
            }
        }
    }
}
