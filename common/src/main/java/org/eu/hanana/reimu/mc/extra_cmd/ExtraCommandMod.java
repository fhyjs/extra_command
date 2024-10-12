package org.eu.hanana.reimu.mc.extra_cmd;

import com.mojang.brigadier.CommandDispatcher;
import dev.architectury.event.events.common.CommandRegistrationEvent;
import dev.architectury.event.events.common.EntityEvent;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.eu.hanana.reimu.mc.extra_cmd.commands.PlayerCommand;
import org.eu.hanana.reimu.mc.extra_cmd.commands.RandomCommand;
import org.eu.hanana.reimu.mc.extra_cmd.commands.StringCommand;
import org.eu.hanana.reimu.mc.extra_cmd.commands.ToastCommand;
import org.eu.hanana.reimu.mc.extra_cmd.network.NetworkRegister;
import org.eu.hanana.reimu.mc.lcr.CommandManager;
import org.eu.hanana.reimu.mc.lcr.events.LegacyCommandRegistrationEvent;

public class ExtraCommandMod {
    public static final String MOD_ID = "extra_command";

    public static void init() {
        LegacyCommandRegistrationEvent.EVENT.register(new LegacyCommandRegistrationEvent() {
            @Override
            public void register(CommandManager commandManager) {
                commandManager.register(new StringCommand());
                commandManager.register(new PlayerCommand());
                commandManager.register(new RandomCommand());
                commandManager.register(new ToastCommand());
            }
        });
        NetworkRegister.register();
    }
}
