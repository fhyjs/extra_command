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
import org.eu.hanana.reimu.mc.extra_cmd.data.ToastData;
import org.eu.hanana.reimu.mc.extra_cmd.network.s2c.S2CToastOperationPayload;
import org.eu.hanana.reimu.mc.lcr.command.CommandBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ToastCommand extends CommandBase {
    @Override
    public int getPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommand() {
        return "toast";
    }

    @Override
    public int execute(CommandSourceStack commandSourceStack, String s) throws Exception {
        var args = parseCommand(s);
        var commandStorage = commandSourceStack.getServer().getCommandStorage();
        var players = getPlayerBySelector(args[1],commandSourceStack);
        //var inputPath = NbtPathArgument.nbtPath().parse(new StringReader(args[2]));
        //var input = commandStorage.get(ResourceLocation.parse(args[1]));
        if (args[2].startsWith("show_sys")) {
            for (ServerPlayer player : players) {
                NetworkManager.sendToPlayer(player, new S2CToastOperationPayload(args[2], new ToastData(args[3], args[4], commandSourceStack.registryAccess()).toJson()));
            }
        }else if (args[2].startsWith("show_tutorial")) {
            for (ServerPlayer player : players) {
                NetworkManager.sendToPlayer(player, new S2CToastOperationPayload(args[2], new ToastData(args[3], args[4], commandSourceStack.registryAccess()).setIcon(args.length<6?null:args[5]).toJson()));
            }
        }else if (args[2].equals("clear_all")) {
            for (ServerPlayer player : players) {
                NetworkManager.sendToPlayer(player, new S2CToastOperationPayload(args[2], ""));
            }
        }
        commandSourceStack.sendSuccess(()->Component.empty().append(Component.literal("Sent toast operation to ")).append(String.valueOf(players.size())).append(Component.literal(" player(s).")),true);
        return players.size();
    }

    @Override
    public String getSuggestion(String string, Player player) {
        var commandStorage = Objects.requireNonNull(player.getServer()).getCommandStorage();
        var args = parseCommand(string);
        if (args.length==1){
            return string+" ";
        }else if (args.length==2){
            return cycleTabSuggestion(player,args,selectorSuggestions(), true);
        }else if (args.length==3){
            return cycleTabSuggestion(player,args,new String[]{"show_sys","show_tutorial","clear_all"}, true);
        }else if (args.length==4){

        }
        return super.getSuggestion(string, player);
    }
}
