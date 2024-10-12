package org.eu.hanana.reimu.mc.extra_cmd.network.s2c;

import dev.architectury.networking.NetworkManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.gui.components.toasts.TutorialToast;
import net.minecraft.resources.ResourceLocation;
import org.eu.hanana.reimu.mc.extra_cmd.data.ToastData;
import sun.misc.Unsafe;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;

public class S2CToastOperationHandler {
    public static void receive(S2CToastOperationPayload payload, NetworkManager.PacketContext context) {
        var toast = ToastData.fromJson(payload.data());
        var sys = Minecraft.getInstance().getToasts();
        if (payload.operation().equals("show_sys")) {
            sys.addToast(new SystemToast(new SystemToast.SystemToastId(),toast.getTitle(context.registryAccess()),toast.getMessage(context.registryAccess())));
        }else if (payload.operation().equals("clear_all")) {
            sys.clear();
        }else if (payload.operation().equals("show_tutorial")) {
            TutorialToast.Icons icons = TutorialToast.Icons.RECIPE_BOOK;
            if (toast.icon!=null) {
                try {
                    Constructor<?> c = TutorialToast.Icons.class.getDeclaredConstructors()[0];
                    c.setAccessible(true);
                    MethodHandle h = MethodHandles.lookup().unreflectConstructor(c);
                    int ordinal=0;
                    for (TutorialToast.Icons value : TutorialToast.Icons.values()) {
                        if (value.ordinal()>ordinal) ordinal=value.ordinal();
                    }
                    ordinal++;
                    icons = (TutorialToast.Icons) h.invokeExact("DYNAMIC",ordinal, ResourceLocation.parse(toast.icon));
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
            sys.addToast(new TutorialToast(icons,toast.getTitle(context.registryAccess()),toast.getMessage(context.registryAccess()),true){
                private float progress=0;
                @Override
                public Visibility render(GuiGraphics guiGraphics, ToastComponent toastComponent, long timeSinceLastVisible) {
                    progress=timeSinceLastVisible/5000f;
                    this.updateProgress(progress);
                    if (progress>1)
                        this.hide();
                    return super.render(guiGraphics, toastComponent, timeSinceLastVisible);
                }
            });
        }
    }
}
