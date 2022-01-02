package me.minkuss.rpg_plugin.listeners;

import me.minkuss.rpg_plugin.Rpg_plugin;
import me.minkuss.rpg_plugin.runnables.CooldownCounter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import java.util.List;

public class JoinPlayerListener implements Listener {
    private final Rpg_plugin _plugin;

    public JoinPlayerListener(Rpg_plugin plugin) {
        _plugin = plugin;
    }

    @EventHandler
    public void onJoinServer(PlayerJoinEvent event) {
        FileConfiguration config = _plugin.getConfig();
        Player player = event.getPlayer();
        String player_name = player.getName();

        if(!config.contains("players." + player_name)) {
            config.set("players." + player_name + ".exp", 0);
            config.set("players." + player_name + ".level", 1);
            config.createSection("players." + player_name + ".class");
            _plugin.saveConfig();
        }
        else if(config.contains("players." + player_name + ".class.skills")) {
            String role_name = config.getString("players." + player_name + ".class.name");
            List<String> skills = config.getStringList("role-skills." + role_name);

            for(String skill : skills) {
                if(config.getLong("players." + player_name + ".class.skills." + skill + ".time-left") > 0) {
                    new CooldownCounter(player_name, _plugin, skill).runTaskTimer(_plugin, 0, 20);
                }
            }
        }
        if(config.contains("players." + player.getName() + ".message")) {
            player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + config.getString("players." + player.getName() + ".message"));
            config.set("players." + player.getName() + ".message", null);
            _plugin.saveConfig();
        }
    }

}
