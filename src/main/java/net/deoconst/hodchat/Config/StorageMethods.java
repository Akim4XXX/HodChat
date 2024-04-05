package net.deoconst.hodchat.Config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.clip.placeholderapi.PlaceholderAPI;
import net.deoconst.hodchat.HodChatPlugin;
import net.kyori.adventure.Adventure;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

public class StorageMethods {
    public static void updatePlayers(Collection<? extends Player> players){
        for(Player player: players){
            player.setMetadata("chatType", new FixedMetadataValue(HodChatPlugin.core(), "global"));
            player.setMetadata("JQAlert", new FixedMetadataValue(HodChatPlugin.core(), "on"));
            if(ifPlayerInJson(player)){
                player.setDisplayName(JsonStorage.getNick(player));
            } else {
                if(HodChatPlugin.config().USE_DEFAULT_NICK_COLOR){
                    player.setDisplayName(setNick(player));
                }
                else{
                    player.setDisplayName(player.getName());
                }
            }
            player.setPlayerListName(PlaceholderAPI.setPlaceholders(player, HodChatPlugin.config().TAB_LIST_FORMAT));
        }
    }
    public static void updatePlayer(Player player){
        player.setMetadata("chatType", new FixedMetadataValue(HodChatPlugin.core(), "global"));
        player.setMetadata("JQAlert", new FixedMetadataValue(HodChatPlugin.core(), "on"));
        if(ifPlayerInJson(player)){
            player.setDisplayName(JsonStorage.getNick(player));
        } else {
            if(HodChatPlugin.config().USE_DEFAULT_NICK_COLOR){
                player.setDisplayName(setNick(player));
            }
            else{
                player.setDisplayName(player.getName());
            }
        }
        player.setPlayerListName(PlaceholderAPI.setPlaceholders(player, HodChatPlugin.config().TAB_LIST_FORMAT));
    }
    public static String setNick(@NotNull Player player){
        String toSerialize = ("<color:" + HodChatPlugin.config().DEFAULT_NICK_COLOR + ">" + player.getName() + "</color>");
        @NotNull Component mmdisplayName = MiniMessage.miniMessage().deserialize(toSerialize);
        return LegacyComponentSerializer
                .builder().
                useUnusualXRepeatedCharacterHexFormat().hexColors()
                .build()
                .serialize(mmdisplayName);
    }
    public static boolean ifPlayerInJson(Player player){
        try {
            JsonElement jsonElement = JsonParser.parseReader(new FileReader(HodChatPlugin.getJsonFile()));
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            for(String key : jsonObject.keySet()){
                JsonObject playerObject = jsonObject.getAsJsonObject(key);
                JsonElement playerNameElement = playerObject.get("PlayerName");
                if(playerNameElement != null && playerNameElement.getAsString().equals(player.getName())){
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
