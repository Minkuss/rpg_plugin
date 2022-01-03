package me.minkuss.rpg_plugin.listeners.clans;

import me.minkuss.rpg_plugin.Rpg_plugin;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

public class AsyncPlayerChatEventListener implements Listener {
    private final Rpg_plugin _plugin;

    public AsyncPlayerChatEventListener(Rpg_plugin plugin) {_plugin = plugin;}

    @EventHandler
    public void OnMassageEvent(AsyncPlayerChatEvent event) {
        FileConfiguration config = _plugin.getConfig();
        Player sender = event.getPlayer();
        boolean in_clan = config.contains("players." + sender.getName() + ".clan");
        boolean in_clan_chat = config.getBoolean("players." + sender.getName() + ".chat");
        String clanName;
        List<String> playersList;

        if (in_clan && in_clan_chat) {
            clanName = config.getString("players." + sender.getName() + ".clan");
            playersList = config.getStringList("clans." + clanName + ".participants");

            for (String item : playersList) {
                if (_plugin.getServer().getPlayer(item) != null) {
                    _plugin.getServer().getPlayer(item).sendMessage(ChatColor.RED + "[" + sender.getName() + "] " + ChatColor.GOLD + event.getMessage());
                }
            }
        }
        else {
            int blockDistance = 10;

            Location senderLocation = sender.getLocation();

            for (Player item : event.getRecipients()) {
                if (item.getLocation().distance(senderLocation) <= blockDistance) {
                    item.sendMessage(ChatColor.GREEN + "[" + sender.getName() + "] " + ChatColor.GOLD + event.getMessage());
                }
            }
        }
        event.setCancelled(true);
    }
}
