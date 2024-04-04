package net.deoconst.hodchat.Commands;

import net.deoconst.hodchat.HodChatPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;

public class PlayerJoinAlertCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Только игроки могут использовать эту команду!");
            return true;
        }
        if(args.length < 1){
            return false;
        }
        String commandArg = args[0];
        if(commandArg.equalsIgnoreCase("on")){
            ((Player) sender).setMetadata("JQAlert", new FixedMetadataValue(HodChatPlugin.core(), "on"));
            sender.sendMessage(ChatColor.GREEN + "Опвощения включены!");
        }
        if(commandArg.equalsIgnoreCase("off")){
            ((Player) sender).setMetadata("JQAlert", new FixedMetadataValue(HodChatPlugin.core(), "off"));
            sender.sendMessage(ChatColor.GREEN + "Опвощения выключены!");
        }
        return true;
    }
}
