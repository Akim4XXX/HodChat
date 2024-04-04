package net.deoconst.hodchat.Events;

import net.deoconst.hodchat.HodChatPlugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Objects;

public class MessageEvent implements Listener {
    @EventHandler
    public void onMessageSend(AsyncPlayerChatEvent event){
        String message = event.getMessage();
        Player player = event.getPlayer();
        if(Objects.equals(player.getMetadata("chatType").get(0).asString(), "local")){
            event.setCancelled(true);
            sendLocalMessage(player, message);
        }
        if(HodChatPlugin.config().CHAT_FORMATTER){
            event.setFormat(HodChatPlugin.config().CHAT_FORMAT
                    .replace("{displayName}", event.getPlayer().getDisplayName() + ChatColor.RESET)
                    .replace("{message}", message)
            );
        }
    }
    public void sendLocalMessage(Player eventPlayer, String message){
        String localMessage = HodChatPlugin.config().LOCALCHAT_FROMAT
                .replace("{displayName}", eventPlayer.getDisplayName() + ChatColor.RESET)
                .replace("{message}", message);
        boolean playerHeard = false;
        for (Player player : HodChatPlugin.core().getServer().getOnlinePlayers()) {
            if(!player.equals(eventPlayer)){
                if (player.getLocation().distance(eventPlayer.getLocation()) <= HodChatPlugin.config().LOCALCHAT_RADIUS) {
                    eventPlayer.sendMessage(localMessage);
                    player.sendMessage(localMessage);
                    playerHeard = true;
                }
            }
        }
        if(!playerHeard){
            eventPlayer.sendMessage(localMessage);
            eventPlayer.sendMessage(ChatColor.RED + "Вас никто не услышал!");
        }
    }
}
