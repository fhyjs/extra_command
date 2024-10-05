package org.eu.hanana.reimu.mc.extra_cmd.commands;

import com.mojang.brigadier.StringReader;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.NbtPathArgument;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.eu.hanana.reimu.mc.lcr.command.CommandBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StringCommand extends CommandBase {
    @Override
    public int getPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommand() {
        return "string";
    }

    @Override
    public int execute(CommandSourceStack commandSourceStack, String command) throws Exception {
        var args = parseCommand(command);
        var commandStorage = commandSourceStack.getServer().getCommandStorage();
        if (args.length<6){
            throw new RuntimeException("Incorrect Command Usage");
        }
        var input = NbtPathArgument.nbtPath().parse(new StringReader(args[2])).get(commandStorage.get(ResourceLocation.parse(args[1])));
        var outputPath = NbtPathArgument.nbtPath().parse(new StringReader(args[4]));
        var output = commandStorage.get(ResourceLocation.parse(args[3]));
        if (args[5].equals("tostring")){
            String string = input.get(0).getAsString();
            outputPath.set(output,StringTag.valueOf(string));
        } else if (args[5].equals("toarray")){
            String string = input.get(0).getAsString();
            ListTag tags = new ListTag();
            for (char c : string.toCharArray()) {
                tags.add(StringTag.valueOf(String.valueOf(c)));
            }
            outputPath.set(output,tags);
        } else if (args[5].equals("mergearray")){
            var data = input.get(0);
            var strBuilder = new StringBuilder();
            if (data.getType() != ListTag.TYPE){
                throw new Exception("Input tag is NOT a array!");
            }
            for (Tag datum : ((ListTag) data)) {
                strBuilder.append(datum.getAsString());
            }
            outputPath.set(output,StringTag.valueOf(strBuilder.toString()));
        } else if (args[5].equals("toint")){
            String string = input.get(0).getAsString();
            outputPath.set(output, IntTag.valueOf(Integer.parseInt(string)));
        } else if (args[5].equals("tofloat")){
            String string = input.get(0).getAsString();
            outputPath.set(output, FloatTag.valueOf(Float.parseFloat(string)));
        } else if (args[5].equals("todouble")){
            String string = input.get(0).getAsString();
            outputPath.set(output, DoubleTag.valueOf(Double.parseDouble(string)));
        } else if (args[5].equals("tobyte")){
            String string = input.get(0).getAsString();
            outputPath.set(output, ByteTag.valueOf(Byte.parseByte(string)));
        } else if (args[5].equals("length")) {
            String string = input.get(0).getAsString();
            commandSourceStack.sendSuccess(()->Component.literal(String.valueOf(string.length())),true);
            return string.length();
        } else if (args[5].equals("runascmd")) {
            String string = input.get(0).getAsString();
            commandSourceStack.getServer().getCommands().performPrefixedCommand(commandSourceStack,string);
        }
        return 0;
    }

    @Override
    public String getSuggestion(String string, Player player) {
        var commandStorage = Objects.requireNonNull(player.getServer()).getCommandStorage();
        var args = parseCommand(string);
        if (args.length==1){
            return string+" ";
        } else if (args.length==2){
            var keyList= commandStorage.keys().sorted().toList();
            List<String> keyNameList = new ArrayList<>();
            for (ResourceLocation resourceLocation : keyList) {
                keyNameList.add(resourceLocation.toString());
            }
            player.sendSystemMessage(Component.literal("The §2location§f of §2input§f value."));
            return cycleTabSuggestion(player,args,keyNameList.toArray(new String[0]), true);
        } else if (args.length==3){
            player.sendSystemMessage(Component.literal("The §2path§f of §2input§f value."));
            return string;
        } else if (args.length==4){
            var keyList= commandStorage.keys().sorted().toList();
            List<String> keyNameList = new ArrayList<>();
            for (ResourceLocation resourceLocation : keyList) {
                keyNameList.add(resourceLocation.toString());
            }
            player.sendSystemMessage(Component.literal("The §2location§f of §2output§f value."));
            return cycleTabSuggestion(player,args,keyNameList.toArray(new String[0]), true);
        }else if (args.length==5){
            player.sendSystemMessage(Component.literal("The §2path§f of §2output§f value."));
            return string;
        }else if (args.length==6){
            return cycleTabSuggestion(player,args,new String[]{"tostring","toarray","mergearray","toint","tofloat","todouble","tobyte","length","runascmd"}, true);
        }
        return super.getSuggestion(string, player);
    }
}
