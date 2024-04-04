package net.deoconst.hodchat.Config;

import net.deoconst.hodchat.util.MiscUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.command.CommandSender;

import java.io.File;

public interface Messages {
    Args0 INVALID_SENDER = () -> MiscUtils.configString("messages.invalidSender",
            "<red>Только игрок может использовать эту комманду");
    Args1<String> UNKNOWN_PLAYER = playerName -> MiscUtils.configString("messages.unknownPlayer",
            "<red>Игрок не найден! %player%.").replaceText(TextReplacementConfig.builder()
            .matchLiteral("%player%").replacement(playerName).build());

    interface Args0 {
        Component build();
        default void send(CommandSender sender) {
            MiscUtils.sendMessage(sender, build());
        }
    }

    /**
     * A message that has one argument that needs to be replaced.
     */
    interface Args1<A0> {
        Component build(A0 arg0);
    }
}
