package net.deoconst.hodchat.Config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.deoconst.hodchat.HodChatPlugin;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.*;


public class JsonStorage {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static void saveNick(@NotNull Player player, @NotNull String formattedColor,
                                @NotNull Boolean isBold, @NotNull Boolean isUnderlined,
                                @NotNull String... colors){
        try{
            JsonObject json = (JsonObject) JsonParser.parseReader(new FileReader(HodChatPlugin.getJsonFile()));
            JsonObject playerNickData = new JsonObject();
            //==
            playerNickData.addProperty("PlayerName", player.getName());
            playerNickData.add("color_list", GSON.toJsonTree(colors));
            playerNickData.addProperty("Bold", isBold);
            playerNickData.addProperty("Underlined", isUnderlined);
            playerNickData.addProperty("formatted_color", formattedColor);
            json.add(player.getUniqueId().toString(), playerNickData);
            //==
            final PrintWriter writer = new PrintWriter(HodChatPlugin.getJsonFile());
            writer.write(GSON.toJson(json));
            writer.flush();
            writer.close();
        } catch (final IOException ex){
            ex.printStackTrace();
        }
    }
    public static void removeNick(@NotNull Player player){
        try{
            JsonObject json = (JsonObject) JsonParser.parseReader(new FileReader(HodChatPlugin.getJsonFile()));
            String playerUUID = player.getUniqueId().toString();
            if (json.has(playerUUID)) {
                json.remove(playerUUID);
                PrintWriter writer = new PrintWriter(HodChatPlugin.getJsonFile());
                writer.write(GSON.toJson(json));
                writer.flush();
                writer.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static String getNick(Player player){
        try {
            JsonObject json = (JsonObject) JsonParser.parseReader(new FileReader(HodChatPlugin.getJsonFile()));
            JsonObject playerData = json.getAsJsonObject(player.getUniqueId().toString());
            return playerData.get("formatted_color").getAsString();
        } catch (final IOException | NullPointerException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public static String getName(Player player){
        if(HodChatPlugin.getJsonFile().equals(player.getUniqueId()))
        {
            try {
                JsonObject json = (JsonObject) JsonParser.parseReader(new FileReader(HodChatPlugin.getJsonFile()));
                JsonObject playerData = json.getAsJsonObject(player.getUniqueId().toString());
                return playerData.get("playerName").getAsString();
            } catch (final IOException | NullPointerException ex) {
                ex.printStackTrace();
                return null;
            }
        }
        return null;
    }
}
