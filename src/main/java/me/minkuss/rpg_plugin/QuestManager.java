package me.minkuss.rpg_plugin;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Locale;

public class QuestManager {

    private final Rpg_plugin _plugin;

    public QuestManager(Rpg_plugin plugin) {
        _plugin = plugin;
    }

    public boolean trySetQuest(String player_name) {
        FileConfiguration config = _plugin.getConfig();
        Player player = _plugin.getServer().getPlayer(player_name);

        if(config.contains("players." + player_name + ".quest")) {
            player.sendMessage(ChatColor.YELLOW + "[Warning] " + ChatColor.GOLD + "У тебя уже есть задание");
            return false;
        }

        int questType = (int)Math.floor(Math.random() * 2);

        switch (questType) {
            case 0 -> SetBringQuest(player_name);
            case 1 -> SetKillQuest(player_name);
            default -> {
                return false;
            }
        }

        return true;
    }

    public void TrySetClanQuest(String player_name) {
        Server server;
        Player player = _plugin.getServer().getPlayer(player_name);
        FileConfiguration config = _plugin.getConfig();
        boolean is_player_in_clan = config.contains("players." + player_name + ".clan");
        boolean is_clan_have_quest;
        int quest_goal;
        String clan_name;
        List<String> targets;
        List<String> params;

        if(!is_player_in_clan) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Вы не состоите в клане");
            return;
        }

        clan_name = config.getString("players." + player_name + ".clan");
        is_clan_have_quest = config.contains("clans." + clan_name + ".quest");

        if(is_clan_have_quest) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "У клана уже есть задание");
        }

        targets = config.getStringList("kill-quest-targets");
        params = List.of("kill", targets.get((int)Math.floor(Math.random() * targets.size())));
        quest_goal = (int)(Math.floor(Math.random() * 200) + 50);

        config.set("clans." + clan_name + ".quest.objective", params);
        config.set("clans." + clan_name + ".quest.goal", quest_goal);
        config.set("clans." + clan_name + ".quest.progress", 0);

        server = _plugin.getServer();

        for(String participant : config.getStringList("clans." + clan_name + ".participants")) {
            Player player_in_clan = server.getPlayer(participant);

            if(player_in_clan != null)
                player_in_clan.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Получено клановое задание: убить " + params.get(1).toLowerCase(Locale.ROOT) + ", " + quest_goal + " раз");
        }
    }

    private void SetKillQuest(String player_name) {
        FileConfiguration config = _plugin.getConfig();

        List<String> targets = config.getStringList("kill-quest-targets");
        List<String> params = List.of("kill", targets.get((int)Math.floor(Math.random() * targets.size())));

        int player_level = config.getInt("players." + player_name + ".level");
        int goal = (int)(Math.floor(Math.random() * player_level) + 5);

        config.set("players." + player_name + ".quest.objective", params);
        config.set("players." + player_name + ".quest.goal", goal);
        config.set("players." + player_name + ".quest.progress", 0);

        _plugin.getServer().getPlayer(player_name).sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Получено задание: убить " + params.get(1).toLowerCase(Locale.ROOT) + ", " + goal + " раз");
        _plugin.saveConfig();
    }

    private void SetBringQuest(String player_name) {
        FileConfiguration config = _plugin.getConfig();
        Player player = _plugin.getServer().getPlayer(player_name);

        List<String> targets = config.getStringList("bring-quest-targets");
        String target = targets.get((int)Math.floor(Math.random() * targets.size()));
        List<String> params = List.of("bring", target);

        int player_level = config.getInt("players." + player_name + ".level");
        int goal = (int)(Math.floor(Math.random() * player_level) + 5);

        config.set("players." + player_name + ".quest.objective", params);
        config.set("players." + player_name + ".quest.goal", goal);

        player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Задание: принести " + params.get(1).toLowerCase(Locale.ROOT) + ", в количестве: " + goal);
        _plugin.saveConfig();
    }

}
