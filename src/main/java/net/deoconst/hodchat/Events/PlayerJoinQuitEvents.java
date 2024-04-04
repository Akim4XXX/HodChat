package net.deoconst.hodchat.Events;

import net.deoconst.hodchat.Config.StorageMethods;
import net.deoconst.hodchat.HodChatPlugin;
import net.deoconst.hodchat.util.MessagesManager;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

import java.util.Objects;

public class PlayerJoinQuitEvents implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        StorageMethods.updatePlayer(event.getPlayer());
        if(HodChatPlugin.config().USE_PLUGIN_RP){
            event.getPlayer().setResourcePack("https://www.dropbox.com/scl/fi/twbjet0cl840r2dasqhxh/HodChatRP.zip?rlkey=70v26gw9rribsp5wv8g8megs6&dl=1");
        }
        Player player = event.getPlayer();
        event.setJoinMessage(MessagesManager.joinLeftMessages(HodChatPlugin.config().PLAYER_JOIN_FORMAT,player));
        for(Player onlinePlayer : HodChatPlugin.core().getServer().getOnlinePlayers()){
            if(onlinePlayer.getMetadata("JQAlert").get(0).asString().equals("off")) continue;
            onlinePlayer.playSound(onlinePlayer, "join", SoundCategory.BLOCKS, 0.43f ,1);
        }
    }
    @EventHandler
    public void onPlayerLeft(PlayerQuitEvent event){
        StorageMethods.updatePlayer(event.getPlayer());
        Player player = event.getPlayer();
        event.setQuitMessage(MessagesManager.joinLeftMessages(HodChatPlugin.config().PLAYER_QUIT_FORMAT,player));
        for(Player onlinePlayer : HodChatPlugin.core().getServer().getOnlinePlayers()){
            if(onlinePlayer.getMetadata("JQAlert").get(0).asString().equals("off")) continue;
            onlinePlayer.playSound(onlinePlayer, "quit", SoundCategory.BLOCKS, 0.4f ,1);
        }
    }

}