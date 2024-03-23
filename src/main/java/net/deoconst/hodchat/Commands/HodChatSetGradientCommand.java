package net.deoconst.hodchat.Commands;

import net.deoconst.hodchat.util.GradientGenerator;
import org.bukkit.plugin.java.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class HodChatSetGradientCommand implements CommandExecutor
{
    private final JavaPlugin plugin;

    public HodChatSetGradientCommand(final JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Только игроки могут использовать эту команду!");
            return true;
        }
        if (!sender.hasPermission("HodChat.hodchat")) {
            sender.sendMessage(ChatColor.RED + "У вас нет прав!");
            return true;
        }
        if (args.length <= 2) {
            return false;
        }
        String subCommand = args[0];
        if(subCommand.equalsIgnoreCase("setGradient")){
            setGradient(sender, args);
        }

        return true;
    }
    private void setGradient(final CommandSender sender, final String[] args){
        final String argsPlayerName = args[1];
        final Player targetPlayer = this.plugin.getServer().getPlayer(argsPlayerName);
        if (args.length >= 4 & args.length <= 6) {
            try {
                if (targetPlayer == null) {
                    sender.sendMessage(ChatColor.RED + "Игрок не найден!");
                    return;
                }
                final String[] colors = new String[args.length - 2];
                for (int i = 2; i < args.length; ++i) {
                    colors[i - 2] = args[i];
                }
                final GradientGenerator gradientGenerator = new GradientGenerator(argsPlayerName, colors);
                //Изменение цвета ника игрока
                targetPlayer.setDisplayName(gradientGenerator.getNickGradient(argsPlayerName));
                targetPlayer.setPlayerListName(gradientGenerator.getNickGradient(argsPlayerName));
                //Отправка сообщения игроку
                targetPlayer.sendMessage(ChatColor.GREEN + "Цвет вашего ника был изменен: " + gradientGenerator.getNickGradient(argsPlayerName));
            }
            catch (Exception ignored) {
            }
        }
    }
}