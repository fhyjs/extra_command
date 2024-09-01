package org.eu.hanana.reimu.mc.extra_cmd.command.string;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandExceptionType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.data.StorageDataAccessor;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class ToStringChildCommand implements Command<CommandSourceStack> {
    public static ToStringChildCommand command;
    public static ArgumentBuilder<CommandSourceStack, ?> reg() {
        command=new ToStringChildCommand();
        return literal("tostring").executes(command);
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var inputStorage = StorageDataAccessor.PROVIDER.apply("from").access(context);
        try {
            var str = inputStorage.getData().get(context.getArgument("input_storage_path",String.class)).toString();
            inputStorage.getData().putString(context.getArgument("output_storage_path",String.class),str);
        }catch (Throwable e){
            e.printStackTrace();
            throw new CommandSyntaxException(new SimpleCommandExceptionType(new LiteralMessage("error")),new LiteralMessage(e.toString()));
        }
        context.getSource().sendSuccess(()->{return Component.literal("success");},true);
        return Command.SINGLE_SUCCESS;
    }
}
