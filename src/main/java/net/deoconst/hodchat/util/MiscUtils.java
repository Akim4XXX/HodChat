package net.deoconst.hodchat.util;

import net.deoconst.hodchat.HodChatPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class MiscUtils {
    public static Component configString(String path, String def) {
        return MiniMessage.miniMessage().deserialize(HodChatPlugin.core().getConfig().getString(path, def));
    }
    public static void sendMessage(final @NotNull CommandSender recipient, final @NotNull Component message) {
        if (!PlainTextComponentSerializer.plainText().serialize(message).isEmpty()) {
            recipient.sendMessage(String.valueOf(message));
        }
    }
}
