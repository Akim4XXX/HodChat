package net.deoconst.hodchat.util;

import me.clip.placeholderapi.PlaceholderAPI;
import net.deoconst.hodchat.HodChatPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MessagesManager {
    public static String joinLeftMessages(String message, Player player){
        @NotNull Component JLMessage = MiniMessage.miniMessage().deserialize(message);
        String rt = LegacyComponentSerializer.builder().useUnusualXRepeatedCharacterHexFormat().hexColors().build().serialize(JLMessage);
        return PlaceholderAPI.setPlaceholders(player, rt);
    }
    public static String chatMessages(String message, Player player){
        @NotNull Component ChatMessage = MiniMessage.miniMessage().deserialize(message);
        String rt = LegacyComponentSerializer.builder().useUnusualXRepeatedCharacterHexFormat().hexColors().build().serialize(ChatMessage);
        return PlaceholderAPI.setPlaceholders(player, rt);
    }
}
