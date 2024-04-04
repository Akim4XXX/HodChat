package net.deoconst.hodchat.util;

import net.deoconst.hodchat.HodChatPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MessagesManager {
    public static String joinLeftMessages(String message, Player player){
        String displayName = player.getName();
        if(HodChatPlugin.config().JOIN_LEAVE_NICKS){
            displayName = player.getDisplayName();
        }
        @NotNull Component JLMessage = MiniMessage.miniMessage().deserialize(message);
        return LegacyComponentSerializer.builder().useUnusualXRepeatedCharacterHexFormat().hexColors().build().serialize(JLMessage)
                .replace("{displayName}", ChatColor.RESET + displayName);
    }
}
