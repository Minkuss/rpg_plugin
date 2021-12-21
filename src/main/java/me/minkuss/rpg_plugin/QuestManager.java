package me.minkuss.rpg_plugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class QuestManager {

    private final Rpg_plugin _plugin;

    public QuestManager(Rpg_plugin plugin) {
        _plugin = plugin;
    }

    public boolean trySetQuest(UUID player_id) {
        FileConfiguration config = _plugin.getConfig();
        Player player = _plugin.getServer().getPlayer(player_id);

        if(config.contains("players." + player_id + ".quest")) {
            player.sendMessage(ChatColor.YELLOW + "[Warning] " + ChatColor.GOLD + "У тебя уже есть задание");
            return false;
        }

        int questType = (int)Math.floor(Math.random() * 2);

        switch (questType) {
            case 0 -> SetBringQuest(player_id);
            case 1 -> SetKillQuest(player_id);
            default -> {
                return false;
            }
        }

        return true;
    }

    private void SetKillQuest(UUID player_id) {
        FileConfiguration config = _plugin.getConfig();
        List<String> targets = config.getStringList("kill-quest-targets");
        List<String> params = List.of("kill", targets.get((int)Math.floor(Math.random() * targets.size())));

        int goal = (int)(Math.floor(Math.random() * 10) + 5);

        config.set("players." + player_id + ".quest.objective", params);
        config.set("players." + player_id + ".quest.goal", goal);
        config.set("players." + player_id + ".quest.progress", 0);

        _plugin.getServer().getPlayer(player_id).sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Задание: убить " + params.get(1).toLowerCase(Locale.ROOT) + ", " + goal + " раз");
        _plugin.saveConfig();
    }

    private void SetBringQuest(UUID player_id) {
        Player player = _plugin.getServer().getPlayer(player_id);

        FileConfiguration config = _plugin.getConfig();

        List<String> targets = config.getStringList("bring-quest-targets");
        String target = targets.get((int)Math.floor(Math.random() * targets.size()));

        List<String> params = List.of("bring", target);

        int goal = (int)(Math.floor(Math.random() * 10) + 5);

        config.set("players." + player_id + ".quest.objective", params);
        config.set("players." + player_id + ".quest.goal", goal);

        player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Задание: принести " + params.get(1).toLowerCase(Locale.ROOT) + ", в количестве: " + goal);
        _plugin.saveConfig();
    }

}
