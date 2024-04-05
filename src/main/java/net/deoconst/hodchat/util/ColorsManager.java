package net.deoconst.hodchat.util;

import me.clip.placeholderapi.PlaceholderAPI;
import net.deoconst.hodchat.Config.JsonStorage;
import net.deoconst.hodchat.HodChatPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ColorsManager {

    public static void resetNickName(Player player){
        String displayName;
        if(HodChatPlugin.config().USE_DEFAULT_NICK_COLOR){
            displayName = ("<color:" + HodChatPlugin.config().DEFAULT_NICK_COLOR + ">" + player.getName() + "</color>");
            @NotNull Component mmdisplayName = MiniMessage.miniMessage().deserialize(displayName);
            displayName = LegacyComponentSerializer.builder().useUnusualXRepeatedCharacterHexFormat().hexColors().build().serialize(mmdisplayName);
            player.setDisplayName(displayName);
            player.setPlayerListName(displayName);
        } else player.setDisplayName(player.getName());
    }

    public static void generateColoredNick(Player player, Boolean isBold, Boolean isUnderlined, String type, String... hexColors){
        String coloredName = "<bold><underlined><"+ type +":"+ getHexColors(hexColors) +">{displayName}";
        if(!isBold) coloredName = coloredName.replace("<bold>", "");
        if(!isUnderlined) coloredName = coloredName.replace("<underlined>", "");
        coloredName = coloredName.replace("{displayName}", player.getName());
        @NotNull Component mmdisplayName = MiniMessage.miniMessage().deserialize(coloredName);
        String displayName = LegacyComponentSerializer
                .builder().useUnusualXRepeatedCharacterHexFormat()
                .hexColors().build()
                .serialize(mmdisplayName);
        player.setDisplayName(displayName);
        player.setPlayerListName(PlaceholderAPI.setPlaceholders(player, HodChatPlugin.config().TAB_LIST_FORMAT));

        JsonStorage.saveNick(player, displayName, isBold, isUnderlined, hexColors);
    }

    public static String getHexColors(String[] colors){
        StringBuilder hexColors = new StringBuilder();
        for(int i = 0; i < colors.length; i++){
            hexColors.append(colors[i]);
            if(i < colors.length - 1){
                hexColors.append(":");
            }
        }
        return hexColors.toString();
    }
}
