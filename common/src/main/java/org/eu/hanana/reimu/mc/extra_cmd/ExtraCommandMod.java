package org.eu.hanana.reimu.mc.extra_cmd;

import com.mojang.brigadier.CommandDispatcher;
import dev.architectury.event.events.common.CommandRegistrationEvent;
import dev.architectury.event.events.common.EntityEvent;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.eu.hanana.reimu.mc.extra_cmd.command.CommandRegisterHandler;

public class ExtraCommandMod {
    public static final String MOD_ID = "extra_command";

    public static void init() {
        CommandRegistrationEvent.EVENT.register(new CommandRegistrationEvent() {
            @Override
            public void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registry, Commands.CommandSelection selection) {
                CommandRegisterHandler.register(dispatcher, registry, selection);
            }
        });
    }
}
