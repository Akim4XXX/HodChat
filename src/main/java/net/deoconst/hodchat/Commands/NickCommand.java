package net.deoconst.hodchat.Commands;

import net.deoconst.hodchat.Config.JsonStorage;
import net.deoconst.hodchat.HodChatPlugin;
import net.deoconst.hodchat.util.ColorsManager;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NickCommand implements CommandExecutor
{
    public boolean onCommand(final @NotNull CommandSender sender, final @NotNull Command command, final @NotNull String label, final String[] args) {
       if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Только игроки могут использовать эту команду!");
            return true;
        }
        if (args.length < 2) {
            return false;
        }
        String subCommand = args[0];
        String targetPlayerName = args[1];
        Player targetPlayer = HodChatPlugin.core().getServer().getPlayer(args[1]);
        Player commandSender = ((Player) sender).getPlayer();

        if (targetPlayer == null || !targetPlayer.isOnline()) {
            sender.sendMessage(ChatColor.RED + "Игрок " + ChatColor.RESET + targetPlayerName + ChatColor.RED + " не найден!");
            return true;
        }

        if(!sender.hasPermission("hodchat.nick")){
            sender.sendMessage("У вас нет прав");
            return true;
        } else {
            if (subCommand.equalsIgnoreCase("setGradient")) {             // /nick setGradient <playerName> <bold|underlined> <colors...>
                if (!sender.hasPermission("hodchat.setGradient")) {
                    sender.sendMessage(ChatColor.RED + "У вас нет прав!");
                    return true;
                } else{
                    if(targetPlayer != commandSender){
                        assert commandSender != null;
                        if(!commandSender.hasPermission("hodchat.setGradientOther")){
                            commandSender.sendMessage(ChatColor.RED + "У вас нет прав на это действие");
                            return true;
                        }
                    }
                    setGradient(sender, args);
                }
            }
            else if (subCommand.equalsIgnoreCase("setColor")) {             // /nick setGradient <playerName> <bold|underlined> <colors...>
                if (!sender.hasPermission("hodchat.setColor")) {
                    sender.sendMessage(ChatColor.RED + "У вас нет прав!");
                    return true;
                } else{
                    if(targetPlayer != commandSender){
                        assert commandSender != null;
                        if(!commandSender.hasPermission("hodchat.setColorOther")){
                            commandSender.sendMessage(ChatColor.RED + "У вас нет прав на это действие");
                            return true;
                        }
                    }
                    setColor(sender, args);
                }
            }
            else if(subCommand.equalsIgnoreCase("clear")){
                if(!sender.hasPermission("hodchat.clear")){
                    sender.sendMessage(ChatColor.RED + "У вас нет прав!");
                    return true;
                } else {
                    if(targetPlayer != commandSender){
                        assert commandSender != null;
                        if(!commandSender.hasPermission("hodchat.clearOther")){
                            commandSender.sendMessage(ChatColor.RED + "У вас нет прав на это действие");
                            return true;
                        }
                    }
                    clear(sender, args);
                }
            }
            else sender.sendMessage(ChatColor.RED + "Неверная подкомманда!");
        }
        return true;
    }
    // /hodchat setGradient <name> <deco> <color1> <color2> .. ..
    private void setGradient(final CommandSender sender, final String[] args) {
        if (args.length < 4 || args.length > 8) {
            sender.sendMessage(ChatColor.RED + "Использование: /nick setGradient <player> decorations [bold] [underlined] #colors2..4");
            return;
        }

        final String playerName = args[1];
        final Player targetPlayer = HodChatPlugin.core().getServer().getPlayer(playerName);

        if (targetPlayer == null) {
            sender.sendMessage(ChatColor.RED + "Игрок " + playerName + " не найден!");
            return;
        }

        int startColor = 2;
        boolean isBold = false;
        boolean isUnderlined = false;

        for (int i = 2; i < args.length; i++) {
            if (args[i].equalsIgnoreCase("bold")) {
                isBold = true;
                startColor++;
            } else if (args[i].equalsIgnoreCase("underlined")) {
                isUnderlined = true;
                startColor++;
            }
        }

        final String[] colors = new String[args.length - startColor];
        System.arraycopy(args, startColor, colors, 0, args.length - startColor);
        if (isValidHexColor(colors, startColor - 1)) {
            sender.sendMessage(ChatColor.RED + "Неверный формат цвета! Пожалуйста, используйте формат HEX для указания цветов.");
            return;
        }

        if (colors.length < 2 || colors.length > 4) {
            sender.sendMessage(ChatColor.RED + "Неверное количество цветов! Допустимо от 2 до 4 цветов.");
            return;
        }

        ColorsManager.generateColoredNick(targetPlayer, isBold, isUnderlined, "gradient", colors);
        targetPlayer.sendMessage("Новый цвет ника успешно установлен - " + targetPlayer.getDisplayName());
    }
    // /nick setColor <playerName> <deco> <color>
    private void setColor(final CommandSender sender, final String[] args) {
        if (args.length < 3 || args.length > 6) {
            sender.sendMessage(ChatColor.RED + "Использование: /nick setColor <player> [bold] [underlined] <color>");
            return;
        }

        final String playerName = args[1];
        final Player targetPlayer = HodChatPlugin.core().getServer().getPlayer(playerName);

        if (targetPlayer == null) {
            sender.sendMessage(ChatColor.RED + "Игрок " + playerName + " не найден!");
            return;
        }

        boolean isBold = false;
        boolean isUnderlined = false;
        int startColor = 2;
        String color;

        for (int i = 2; i < args.length; i++) {
            if (args[i].equalsIgnoreCase("bold")) {
                isBold = true;
            } else if (args[i].equalsIgnoreCase("underlined")) {
                isUnderlined = true;
            } else {
                startColor = i;
                break;
            }
        }

        color = args[startColor];
        startColor++; // Сдвигаем индекс начала цветов на следующий аргумент после цвета

        final String[] colors = new String[args.length - startColor];
        System.arraycopy(args, startColor, colors, 0, args.length - startColor);
        if (color == null || isValidHexColor(colors, startColor - 1)) {
            sender.sendMessage(ChatColor.RED + "Неверный формат цвета! Пожалуйста, используйте формат HEX для указания цветов.");
            return;
        }

        ColorsManager.generateColoredNick(targetPlayer, isBold, isUnderlined, color, colors);
        targetPlayer.sendMessage("Новый цвет ника успешно установлен - " + targetPlayer.getDisplayName());
    }
    private void clear(final CommandSender sender, final String[] args){
        final String argsPlayerName = args[1];
        final Player targetPlayer = HodChatPlugin.core().getServer().getPlayer(argsPlayerName);
        final Player senderPlayer = (Player) sender;
        if(args.length > 2){
            return;
        }
        assert targetPlayer != null;
        ColorsManager.resetNickName(targetPlayer);
        JsonStorage.removeNick(targetPlayer);
        if(!senderPlayer.getName().equals(targetPlayer.getName())){
            sender.sendMessage("Цвет ника игрока " + targetPlayer.getName() + " был сброшен.");
            targetPlayer.sendMessage("Цвет вашего ника был сброшен!");
            return;
        }
        senderPlayer.sendMessage("Цвет вашего ника был сброшен!");
    }

    public static boolean isValidHexColor(String[] args, int startIndex) {
        // Регулярное выражение для проверки формата HEX-кода
        String hexPattern = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";
        // Создание объекта Pattern с указанным регулярным выражением
        Pattern pattern = Pattern.compile(hexPattern);
        // Проверка соответствия строк формату HEX-кода, начиная с startIndex
        for (int i = startIndex; i < args.length; i++) {
            // Создание объекта Matcher для текущей строки
            Matcher matcher = pattern.matcher(args[i]);
            if (!matcher.matches()) {
                return true; // Если строка не соответствует формату HEX-кода, возвращаем false
            }
        }
        return false; // Если все строки соответствуют формату HEX-кода, возвращаем true
    }
}
