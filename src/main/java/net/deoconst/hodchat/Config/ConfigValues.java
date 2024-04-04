package net.deoconst.hodchat.Config;
import net.deoconst.hodchat.HodChatPlugin;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;


public class ConfigValues {
    public Boolean USE_PLUGIN_RP;
    public Boolean TAB_NICKS;
    public Boolean JOIN_LEAVE_NICKS;
    public Boolean USE_DEFAULT_NICK_COLOR;
    public String DEFAULT_NICK_COLOR;
    public Boolean CHAT_FORMATTER;
    public String CHAT_FORMAT;
    //Global/Local chat
    public String LOCALCHAT_FROMAT;
    public int LOCALCHAT_RADIUS;
    // Player join/leave
    public String PLAYER_JOIN_FORMAT;
    public String PLAYER_QUIT_FORMAT;

    //Messages
    public String NO_PERMISSIONS;
    public String INVALID_SENDER;
    public String UNKNOWN_PLAYER;
    public ConfigValues(){

        this.reload();
    }
    public void reload(){
        USE_PLUGIN_RP = HodChatPlugin.core().getConfig().getBoolean("usePluginRP", true);
        //values
        TAB_NICKS = HodChatPlugin.core().getConfig().getBoolean("coloredNicksTabList", true);
        JOIN_LEAVE_NICKS = HodChatPlugin.core().getConfig().getBoolean("coloredNickLeaveJoinMessage", true);
        USE_DEFAULT_NICK_COLOR = HodChatPlugin.core().getConfig().getBoolean("useDefaultNickColor", true);
        DEFAULT_NICK_COLOR = HodChatPlugin.core().getConfig().getString("defaultNickColor", "#FFAE42");
        CHAT_FORMATTER = HodChatPlugin.core().getConfig().getBoolean("formattedChat", true);
        CHAT_FORMAT = HodChatPlugin.core().getConfig().getString("chatFormat", "{displayName}: {message}");
        LOCALCHAT_FROMAT = HodChatPlugin.core().getConfig().getString("localChatFormat", "{displayName}: {message}");
        LOCALCHAT_RADIUS = HodChatPlugin.core().getConfig().getInt("localChatRadius", 100);
        // Player join/leave
        PLAYER_JOIN_FORMAT = HodChatPlugin.core().getConfig().getString("playerJoinFormat", "<green><bold>[+] {displayName}");
        PLAYER_QUIT_FORMAT = HodChatPlugin.core().getConfig().getString("playerQuitFormat", "<red><bold>[-] {displayName}");

        //messages
        NO_PERMISSIONS = HodChatPlugin.core().getConfig().getString("messages.no_permissions", "У вас нет прав!");
        INVALID_SENDER = HodChatPlugin.core().getConfig().getString("messages.invalidSender", "Только игрок может использовать эту комманду");
        UNKNOWN_PLAYER = HodChatPlugin.core().getConfig().getString("messages.unknownPlayer", "Игрок не найден");
    }
    public static void generateConfig(ConfigValues values, File configFile){
        YamlConfiguration config = new YamlConfiguration();
        config.set("usePluginRP", values.USE_PLUGIN_RP);

        config.set("coloredNicksTabList", values.TAB_NICKS);
        config.set("coloredNickLeaveJoinMessage", values.JOIN_LEAVE_NICKS);
        config.set("useDefaultNickColor", values.USE_DEFAULT_NICK_COLOR);
        config.set("defaultNickColor", values.DEFAULT_NICK_COLOR);
        // Chat
        config.set("formattedChat", values.CHAT_FORMATTER);
        config.set("chatFormat", values.CHAT_FORMAT);
        config.set("localChatFormat", values.LOCALCHAT_FROMAT);
        config.set("localChatRadius", values.LOCALCHAT_RADIUS);
        // Player Join/Leave
        config.set("playerJoinFormat", values.PLAYER_JOIN_FORMAT);
        config.set("playerQuitFormat", values.PLAYER_QUIT_FORMAT);
        //messages
        config.set("messages.no_permissions", values.NO_PERMISSIONS);
        try{
            config.save(configFile);
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
