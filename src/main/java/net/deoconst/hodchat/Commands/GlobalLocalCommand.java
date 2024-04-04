package net.deoconst.hodchat.Commands;

import net.deoconst.hodchat.HodChatPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;

public class GlobalLocalCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Только игроки могут использовать эту команду!");
            return true;
        }
        Player player = (Player) sender;
        if (command.getName().equalsIgnoreCase("global")) {
            if(!sender.hasPermission("hodchat.global")){
                sender.sendMessage(ChatColor.RED + "У вас нет прав!");
                return true;
            }
            player.setMetadata("chatType", new FixedMetadataValue(HodChatPlugin.core(), "global"));
            player.sendMessage("Вы переключены на " + ChatColor.GREEN + "глобальный чат");
            player.sendMessage(player.getMetadata("chatType").get(0).asString());
        } else if (command.getName().equalsIgnoreCase("local")) {
            if(!sender.hasPermission("hodchat.global")){
                sender.sendMessage(ChatColor.RED + "У вас нет прав!");
                return true;
            }
            player.setMetadata("chatType", new FixedMetadataValue(HodChatPlugin.core(), "local"));
            player.sendMessage("Вы переключены на " + ChatColor.GREEN + "локальный чат");
        }
        return true;
    }
}
