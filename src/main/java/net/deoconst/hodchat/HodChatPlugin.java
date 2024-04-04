package net.deoconst.hodchat;

import net.deoconst.hodchat.Commands.GlobalLocalCommand;
import net.deoconst.hodchat.Commands.PlayerJoinAlertCommand;
import net.deoconst.hodchat.Config.StorageMethods;
import net.deoconst.hodchat.Events.MessageEvent;
import net.deoconst.hodchat.Events.PlayerJoinQuitEvents;
import net.deoconst.hodchat.Commands.NickCommand;
import net.deoconst.hodchat.Commands.TabCompleeters.NickTabCompleter;
import net.deoconst.hodchat.Config.ConfigValues;
import net.deoconst.hodchat.util.ColorsManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

public final class HodChatPlugin extends JavaPlugin {
    private static HodChatPlugin core;
    private static ConfigValues config;
    private static File jsonFile;
    private static File configFile;
    private final PluginDescriptionFile pdf = this.getDescription();

    public HodChatPlugin(){
        core = this;
        jsonFile = new File(this.getDataFolder(), "nicksColor.json");
        configFile = new File(this.getDataFolder(), "config.yml");
        config = new ConfigValues();
    }
    @Override
    public void onEnable() {
        boolean JSONCreated = getJsonFile().exists();
        boolean YMLCreated = getConfigFile().exists();

        if(!JSONCreated){
            try{
                getServer().getConsoleSender().sendMessage(coloredMessage("Load json configuration", "#DB8220", "#EAC97F"));
                JSONCreated = getJsonFile().createNewFile();
                if(JSONCreated){
                    final PrintWriter writer = new PrintWriter(getJsonFile());
                    writer.write("{ }");
                    writer.flush();
                    writer.close();
                }
            } catch (final IOException ex){
                ex.printStackTrace();
            }
        }
        if(!YMLCreated){
            getServer().getConsoleSender().sendMessage(coloredMessage("Load yml configuration", "#DB8220", "#EAC97F"));
            ConfigValues.generateConfig(config, new File(this.getDataFolder(), "config.yml"));
        }
        if(!config().USE_PLUGIN_RP){
            getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "HodChat required resource pack - https://www.dropbox.com/scl/fi/twbjet0cl840r2dasqhxh/HodChatRP.zip?rlkey=70v26gw9rribsp5wv8g8megs6&dl=1");
        }
        registerCommands();
        registerEvents(new PlayerJoinQuitEvents(), new MessageEvent());
        StorageMethods.updatePlayers(Bukkit.getOnlinePlayers());
        loadMessage();
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "HodChat disabled");
    }

    public static HodChatPlugin core() {
        return core;
    }
    public static @NotNull File getJsonFile(){
        return jsonFile;
    }
    public static @NotNull File getConfigFile(){
        return configFile;
    }
    public static ConfigValues config(){
        return config;
    }
    // =========================================
    private void registerCommands(){
        Objects.requireNonNull(getCommand("nick")).setExecutor(new NickCommand());
        Objects.requireNonNull(getCommand("local")).setExecutor(new GlobalLocalCommand());
        Objects.requireNonNull(getCommand("global")).setExecutor(new GlobalLocalCommand());
        Objects.requireNonNull(getCommand("JQAlert")).setExecutor(new PlayerJoinAlertCommand());

        Objects.requireNonNull(getServer().getPluginCommand("nick")).setTabCompleter(new NickTabCompleter());
    }
    private void registerEvents(Listener... listeners){
        for(final Listener listener: listeners){
            this.getServer().getPluginManager().registerEvents(listener, this);
        }
    }
    private void loadMessage(){

        getServer().getConsoleSender().sendMessage(coloredMessage("HodChat " + pdf.getVersion() + " loaded", "#DB8220", "#EAC97F"));
        for(Player player : getServer().getOnlinePlayers()){
            if(player.isOp()){
                getServer().broadcastMessage(coloredMessage("HodChat " + pdf.getVersion() + " loaded", "#DB8220", "#EAC97F"));
            }
        }
    }
    private String coloredMessage(String message, String... colors){
        String consoleMessage = "<b><color:" + ColorsManager.getHexColors(colors) + ">" + message;
        @NotNull Component mmdisplayName = MiniMessage.miniMessage().deserialize(consoleMessage);
        return LegacyComponentSerializer
                .builder().useUnusualXRepeatedCharacterHexFormat()
                .hexColors().build()
                .serialize(mmdisplayName);
    }
}
