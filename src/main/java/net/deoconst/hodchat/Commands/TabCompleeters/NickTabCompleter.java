package net.deoconst.hodchat.Commands.TabCompleeters;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class NickTabCompleter implements TabCompleter {
    /**
     * Requests a list of possible completions for a command argument.
     *
     * @param sender  Source of the command.  For players tab-completing a
     *                command inside of a command block, this will be the player, not
     *                the command block.
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    The arguments passed to the command, including final
     *                partial argument to be completed
     * @return A List of possible completions for the final argument, or null
     * to default to the command executor
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1){
                ArrayList<String> subCommands = new ArrayList<String>(Arrays.asList("setGradient", "setColor", "clear"));
                return subCommands;
        }
        if(args[0].equalsIgnoreCase("setGradient")){
            switch (args.length){
            case 2:
                Collection<?extends Player> onlinePlayers = Bukkit.getServer().getOnlinePlayers();
                ArrayList<String> onlinePlayerName = new ArrayList<>();
                for(Player onlinePlayer : onlinePlayers){
                    String name = onlinePlayer.getName();
                    onlinePlayerName.add(name);
                }
                return onlinePlayerName;
            case 3:
                ArrayList<String> decorations = new ArrayList<>(Arrays.asList("Bold", "Underlined"));
                return decorations;
            }
        }
        if(args[0].equalsIgnoreCase("setColor")){
            switch (args.length){
                case 2:
                    Collection<?extends Player> onlinePlayers = Bukkit.getServer().getOnlinePlayers();
                    ArrayList<String> onlinePlayerName = new ArrayList<>();
                    for(Player onlinePlayer : onlinePlayers){
                        String name = onlinePlayer.getName();
                        onlinePlayerName.add(name);
                    }
                    return onlinePlayerName;
                case 3:
                    ArrayList<String> decorations = new ArrayList<>(Arrays.asList("Bold", "Underlined"));
                    return decorations;
            }
        }
        if(args[0].equalsIgnoreCase("clear")){
            switch (args.length){
                case 2:
                    Collection<?extends Player> onlinePlayers = Bukkit.getServer().getOnlinePlayers();
                    ArrayList<String> onlinePlayerName = new ArrayList<>();
                    for(Player onlinePlayer : onlinePlayers){
                        String name = onlinePlayer.getName();
                        onlinePlayerName.add(name);
                    }
                    return onlinePlayerName;
            }
        }
        return new ArrayList<>();
    }
}
