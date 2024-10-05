package org.eu.hanana.reimu.mc.extra_cmd.commands;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;
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
        if (args[2].equals("title")){
            if (args[3].equals("set")) {

            }else if (args[3].equals("get")) {

            }else if (args[3].equals("store")) {

            }
        }
        return playerList.size();
    }

    @Override
    public String getSuggestion(String string, Player player) {
        var args = parseCommand(string);
        if (args.length==1){
            return cycleTabSuggestion(player,args,selectorSuggestions(),true);
        }else if (args.length==2){
            return cycleTabSuggestion(player,args,new String[]{"title"},true);
        }else if (args.length==3){
            if (args[2].equals("title")){
                return cycleTabSuggestion(player,args,new String[]{"set","get","store"},true);
            }
        }
        return super.getSuggestion(string, player);
    }
}
