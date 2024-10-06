package org.eu.hanana.reimu.mc.extra_cmd.network;

import com.mojang.blaze3d.platform.WindowEventHandler;
import dev.architectury.networking.NetworkManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.ChatScreen;
import org.eu.hanana.reimu.mc.lcr.network.S2CPayloadSendSetCommandField;
import org.lwjgl.glfw.GLFW;

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
