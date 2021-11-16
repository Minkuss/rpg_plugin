package me.minkuss.rpg_plugin.listeners;

import me.minkuss.rpg_plugin.Rpg_plugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinPlayerListener implements Listener {

    private final Rpg_plugin _plugin;

    public JoinPlayerListener(Rpg_plugin plugin) {
        _plugin = plugin;
    }

    @EventHandler
    public void onJoinServer(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String id = _plugin.getConfig().getString("players." + player.getUniqueId());

        if(id == null) {
            _plugin.getConfig().set("players." + player.getUniqueId() + ".exp", 0);
            _plugin.getConfig().set("players." + player.getUniqueId() + ".level", 1);
            _plugin.getConfig().set("players." + player.getUniqueId() + ".class", null);
            _plugin.getConfig().set("players." + player.getUniqueId() + ".class.skills.invisibility.last-use", null);
            _plugin.saveConfig();
        }
    }

}
