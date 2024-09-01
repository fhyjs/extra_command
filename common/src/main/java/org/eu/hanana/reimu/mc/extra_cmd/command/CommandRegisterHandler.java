package org.eu.hanana.reimu.mc.extra_cmd.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.eu.hanana.reimu.mc.extra_cmd.command.string.StringCommands;

import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;

public class CommandRegisterHandler {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registry, Commands.CommandSelection selection){
        StringCommands.register(dispatcher,registry,selection);
    }
}
