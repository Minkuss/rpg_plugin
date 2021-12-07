package me.minkuss.rpg_plugin.listeners.quests;

import me.minkuss.rpg_plugin.Rpg_plugin;
import me.minkuss.rpg_plugin.events.quests.QuestCompleteEvent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.List;

public class KillListener implements Listener {

    private final Rpg_plugin _plugin;

    public KillListener(Rpg_plugin plugin) {
        _plugin = plugin;
    }

    @EventHandler
    public void onPlayerKill(EntityDeathEvent event) {

        Player player = event.getEntity().getKiller();
        List<String> params = _plugin.getConfig().getStringList("players." + player.getUniqueId() + ".quest.objective");

        if(!params.isEmpty() && params.get(0).equals("kill")) {
            if(event.getEntityType().toString().equals(params.get(1))) {
                FileConfiguration config = _plugin.getConfig();
                int progress = config.getInt("players." + player.getUniqueId() + ".quest.progress");

                player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Прогресс задания: " + (progress + 1));
                config.set("players." + player.getUniqueId() + ".quest.progress", (progress + 1));
                _plugin.saveConfig();

                int goal = config.getInt("players." + player.getUniqueId() + ".quest.goal");

                if(progress + 1 >= goal) {
                    int exp = config.getInt("exp-info." + params.get(1)) * goal;
                    _plugin.getServer().getPluginManager().callEvent(new QuestCompleteEvent(player, exp));
                }
            }
        }

    }

}
