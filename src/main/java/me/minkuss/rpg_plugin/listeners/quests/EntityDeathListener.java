package me.minkuss.rpg_plugin.listeners.quests;

import me.minkuss.rpg_plugin.Rpg_plugin;
import me.minkuss.rpg_plugin.events.quests.ClanQuestCompleteEvent;
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

public class EntityDeathListener implements Listener {

    private final Rpg_plugin _plugin;

    public EntityDeathListener(Rpg_plugin plugin) {
        _plugin = plugin;
    }

    @EventHandler
    public void onPlayerKillEntity(EntityDeathEvent event) {
        FileConfiguration config = _plugin.getConfig();
        Player player = event.getEntity().getKiller();
        List<String> params = List.of();
        String clan_name = null;
        String player_name = null;
        boolean is_player_in_clan = false;
        boolean is_clan_have_quest = false;

        if(player != null) {
            player_name = player.getName();
            params = config.getStringList("players." + player_name + ".quest.objective");
            is_player_in_clan = config.contains("players." + player_name + ".clan");
            clan_name = config.getString("players." + player_name + ".clan");
            is_clan_have_quest = config.contains("clans." + clan_name + ".quest");
        }

        if(!params.isEmpty() && params.get(0).equals("kill")) {
            if(event.getEntityType().toString().equals(params.get(1))) {
                int progress = config.getInt("players." + player_name + ".quest.progress");
                int goal = config.getInt("players." + player_name + ".quest.goal");

                player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Прогресс задания: " + (progress + 1) + "/" + goal);
                config.set("players." + player_name + ".quest.progress", (progress + 1));
                _plugin.saveConfig();


                if(progress + 1 >= goal) {
                    int exp = config.getInt("exp-info.kill." + params.get(1)) * goal;
                    int money = goal * 10;
                    _plugin.getServer().getPluginManager().callEvent(new QuestCompleteEvent(player, exp, money));
                }
            }
        }

        if(is_player_in_clan && is_clan_have_quest) {
            params = config.getStringList("clans." + clan_name + ".quest.objective");

            if(event.getEntityType().toString().equals(params.get(1))) {
                int progress = config.getInt("clans." + clan_name + ".quest.progress");
                int goal = config.getInt("clans." + clan_name + ".quest.goal");

                player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Прогресс кланового задания: " + (progress + 1) + "/" + goal);
                config.set("clans." + clan_name + ".quest.progress", (progress + 1));
                _plugin.saveConfig();


                if(progress + 1 >= goal) {
                    int exp = config.getInt("exp-info.kill." + params.get(1)) * goal;
                    _plugin.getServer().getPluginManager().callEvent(new ClanQuestCompleteEvent(clan_name, exp));
                }
            }
        }

    }

}
