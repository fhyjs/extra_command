package org.eu.hanana.reimu.mc.extra_cmd.commands;

import com.mojang.brigadier.StringReader;
import dev.architectury.networking.NetworkManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.NbtPathArgument;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.eu.hanana.reimu.mc.extra_cmd.network.s2c.S2CActionOperationPayload;
import org.eu.hanana.reimu.mc.extra_cmd.network.s2c.S2CWindowOperationPayload;
import org.eu.hanana.reimu.mc.lcr.command.CommandBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RandomCommand extends CommandBase {
    @Override
    public int getPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommand() {
        return "random";
    }

    @Override
    public int execute(CommandSourceStack commandSourceStack, String s) throws Exception {
        var args = parseCommand(s);
        var commandStorage = commandSourceStack.getServer().getCommandStorage();
        var random = commandSourceStack.getLevel().getRandom();
        if (args.length<4){
            throw new RuntimeException("Incorrect Command Usage");
        }
        var outputPath = NbtPathArgument.nbtPath().parse(new StringReader(args[2]));
        var output = commandStorage.get(ResourceLocation.parse(args[1]));
        if (args[3].equals("float")){
            outputPath.set(output, FloatTag.valueOf(random.nextFloat()));
        }else if (args[3].equals("double")){
            outputPath.set(output, DoubleTag.valueOf(random.nextDouble()));
        }else if (args[3].equals("int")){
            outputPath.set(output, IntTag.valueOf(random.nextInt()));
        }else if (args[3].equals("long")){
            outputPath.set(output, LongTag.valueOf(random.nextLong()));
        }else if (args[3].equals("int_between")){
            outputPath.set(output, IntTag.valueOf(random.nextIntBetweenInclusive(Integer.parseInt(args[4]),Integer.parseInt(args[5]))));
        }
        String asString = outputPath.get(output).getFirst().getAsString();
        commandSourceStack.sendSuccess(()->Component.literal(asString),true);
        return 0;
    }

    @Override
    public String getSuggestion(String string, Player player) {
        var commandStorage = Objects.requireNonNull(player.getServer()).getCommandStorage();
        var args = parseCommand(string);
        if (args.length==1){
            return string+" ";
        }else if (args.length==2){
            var keyList= commandStorage.keys().sorted().toList();
            List<String> keyNameList = new ArrayList<>();
            for (ResourceLocation resourceLocation : keyList) {
                keyNameList.add(resourceLocation.toString());
            }
            player.sendSystemMessage(Component.literal("The §2location§f of §2output§f value."));
            return cycleTabSuggestion(player,args,keyNameList.toArray(new String[0]), true);
        }else if (args.length==3){
            player.sendSystemMessage(Component.literal("The §2path§f of §2output§f value."));
            return string;
        }else if (args.length==4){
            return cycleTabSuggestion(player,args,new String[]{"float","double","int","long","int_between"}, true);
        }
        return super.getSuggestion(string, player);
    }
}
