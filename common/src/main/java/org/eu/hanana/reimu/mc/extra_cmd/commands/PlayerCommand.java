package org.eu.hanana.reimu.mc.extra_cmd.commands;

import dev.architectury.networking.NetworkManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.eu.hanana.reimu.mc.extra_cmd.network.S2CWindowOperationPayload;
import org.eu.hanana.reimu.mc.lcr.command.CommandBase;

public class PlayerCommand extends CommandBase {
    @Override
    public int getPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommand() {
        return "player";
    }

    @Override
    public int execute(CommandSourceStack commandSourceStack, String s) throws Exception {
        var args = parseCommand(s);
        var playerList = getPlayerBySelector(args[1],commandSourceStack);
        if (args[2].equals("window_title")){
            if (args[3].equals("set")) {
                for (ServerPlayer serverPlayer : playerList) {
                    NetworkManager.sendToPlayer(serverPlayer,new S2CWindowOperationPayload("set_title",args[4]));
                }
            }else if (args[3].equals("reset")) {
                for (ServerPlayer serverPlayer : playerList) {
                    NetworkManager.sendToPlayer(serverPlayer,new S2CWindowOperationPayload("reset_title",""));
                }
            }
        }else if (args[2].equals("window_size")){
            if (args[3].equals("set")) {
                for (ServerPlayer serverPlayer : playerList) {
                    NetworkManager.sendToPlayer(serverPlayer,new S2CWindowOperationPayload("set_size",Integer.parseInt(args[4])+"x"+Integer.parseInt(args[5])));
                }
            }else if (args[3].equals("set_fulled")) {
                for (ServerPlayer serverPlayer : playerList) {
                    NetworkManager.sendToPlayer(serverPlayer,new S2CWindowOperationPayload("set_fulled",""));
                }
            }else if (args[3].equals("set_windowed")) {
                for (ServerPlayer serverPlayer : playerList) {
                    NetworkManager.sendToPlayer(serverPlayer,new S2CWindowOperationPayload("set_windowed",""));
                }
            }
        }
        return playerList.size();
    }

    @Override
    public String getSuggestion(String string, Player player) {
        var args = parseCommand(string);
        if (args.length==1){
            return string+" ";
        }else if (args.length==2){
            return cycleTabSuggestion(player,args,selectorSuggestions(),true);
        }else if (args.length==3){
            return cycleTabSuggestion(player,args,new String[]{"window_title","window_size"},true);
        }else if (args.length==4){
            if (args[2].equals("window_title")){
                return cycleTabSuggestion(player,args,new String[]{"set","reset"},true);
            }else if (args[2].equals("window_size")){
                return cycleTabSuggestion(player,args,new String[]{"get_height","get_weight","set","set_fulled","set_windowed"},true);
            }
        }
        return super.getSuggestion(string, player);
    }
}
