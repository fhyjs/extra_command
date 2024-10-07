package org.eu.hanana.reimu.mc.extra_cmd.network.s2c;

import dev.architectury.networking.NetworkManager;
import net.minecraft.client.Minecraft;

public class S2CWindowOperationHandler {
    public static void receive(S2CWindowOperationPayload payload, NetworkManager.PacketContext context) {
        if (payload.operation().equals("set_title")) {
            Minecraft.getInstance().getWindow().setTitle(payload.data());
        }else if (payload.operation().equals("reset_title")) {
            Minecraft.getInstance().updateTitle();
        }else if (payload.operation().equals("set_size")) {
            String[] xes = payload.data().split("x");
            Minecraft.getInstance().getWindow().setWindowed(Integer.parseInt(xes[0]),Integer.parseInt(xes[1]));
        }else if (payload.operation().equals("set_fulled")) {
            if (!Minecraft.getInstance().getWindow().isFullscreen()) {
                Minecraft.getInstance().getWindow().toggleFullScreen();
            }
        }else if (payload.operation().equals("set_windowed")) {
            if (Minecraft.getInstance().getWindow().isFullscreen()) {
                Minecraft.getInstance().getWindow().toggleFullScreen();
            }
        }
    }
}
