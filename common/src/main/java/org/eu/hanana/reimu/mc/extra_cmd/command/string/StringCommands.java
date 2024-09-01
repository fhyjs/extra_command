package org.eu.hanana.reimu.mc.extra_cmd.command.string;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.server.commands.data.DataCommands;
import net.minecraft.server.commands.data.StorageDataAccessor;

import java.util.ArrayList;
import java.util.function.Predicate;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;
public class StringCommands {
    @SuppressWarnings("unchecked")
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registry, Commands.CommandSelection selection) {
        var cmdB = (literal("string").requires(new Predicate<>() {
            @Override
            public boolean test(CommandSourceStack commandSourceStack) {
                return commandSourceStack.hasPermission(2);
            }
        }));
        var lArg = argument("output_storage_path",StringArgumentType.word());

        var children = new ArrayList<ArgumentBuilder<CommandSourceStack,?>>();
        children.add(ToStringChildCommand.reg());
        for (var s : children) {
            lArg.then(s);
        }
        cmdB.then(argument("from",ResourceLocationArgument.id()).then(argument("input_storage_path", StringArgumentType.word()).then(literal("operation").then(lArg))));
        dispatcher.register(cmdB);
    }
}
