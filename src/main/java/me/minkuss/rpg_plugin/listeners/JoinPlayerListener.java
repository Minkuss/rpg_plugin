package me.minkuss.rpg_plugin.listeners;

import me.minkuss.rpg_plugin.Rpg_plugin;
import me.minkuss.rpg_plugin.runnables.CooldownCounter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
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
        String id = _plugin.getConfig().getString("players." + player.getUniqueId());

        if(id == null) {
            config.set("players." + player.getUniqueId() + ".exp", 0);
            config.set("players." + player.getUniqueId() + ".level", 1);
            config.createSection("players." + player.getUniqueId() + ".class");

            _plugin.saveConfig();
        }
        else if(config.contains("players." + player.getUniqueId() + ".class.skills")) {
            String role = config.getString("players." + player.getUniqueId() + ".class.name");
            List<String> skills = config.getStringList("role-skills." + role);

            for(String skill : skills) {
                if(config.getLong("players." + player.getUniqueId() + ".class.skills." + skill + ".time-left") > 0) {
                    new CooldownCounter(player, _plugin, skill).runTaskTimer(_plugin, 0, 20);
                }
            }
        }
    }

}
