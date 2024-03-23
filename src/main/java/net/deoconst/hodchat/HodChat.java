package net.deoconst.hodchat;

import net.deoconst.hodchat.Commands.HodChatSetGradientCommand;
import net.deoconst.hodchat.Commands.HodChatTabCompleter;
import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;

public final class HodChat extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getServer().getPluginCommand("hodchat").setExecutor(new HodChatSetGradientCommand(this));
        this.getServer().getPluginCommand("hodchat").setTabCompleter(new HodChatTabCompleter());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
