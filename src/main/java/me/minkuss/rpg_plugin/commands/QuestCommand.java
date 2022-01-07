package me.minkuss.rpg_plugin.commands;

import me.minkuss.rpg_plugin.QuestManager;
import me.minkuss.rpg_plugin.Rpg_plugin;
import me.minkuss.rpg_plugin.events.quests.ClanQuestCompleteEvent;
import me.minkuss.rpg_plugin.events.quests.QuestCompleteEvent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Locale;

public class QuestCommand extends AbstractCommand {
    public QuestCommand(String name) {super(name);}

    @Override
    public void execute(Rpg_plugin plugin, CommandSender sender, String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Только игрок может использовать эту команду");
            return;
        }

        if(args.length < 1) {
            player.sendMessage(ChatColor.YELLOW + "[Warning] " + ChatColor.GOLD + "Требуются аргументы");
            return;
        }

        switch(args[0]) {
            case "show" -> Show(player, plugin);
            case "cancel" -> Cancel(player, plugin);
            case "get" -> Get(player, plugin, args);
            case "complete" -> Complete(player, args, plugin);
            default -> player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Введены несуществующие аргументы");
        }
    }

    private void Show(Player player, Rpg_plugin plugin) {
        FileConfiguration config = plugin.getConfig();
        List<String> params = config.getStringList("players." + player.getName() + ".quest.objective");

        if(params.isEmpty()) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "У тебя нет задания");
            return;
        }

        if(params.get(0).equals("kill")) {
            int goal = config.getInt("players." + player.getName() + ".quest.goal");
            int progress = config.getInt("players." + player.getName() + ".quest.progress");

            player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Задание: убить " + params.get(1).toLowerCase(Locale.ROOT) + ", " + progress + "/" + goal);
        }
        else if(params.get(0).equals("bring")) {
            int goal = config.getInt("players." + player.getName() + ".quest.goal");

            player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Задание: принести " + params.get(1).toLowerCase(Locale.ROOT) + ", в количестве: " + goal);
        }
    }

    private void Cancel(Player player, Rpg_plugin plugin) {
        if(plugin.getConfig().contains("players." + player.getName() + ".quest")) {
            player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Задание отмененно");

            plugin.getConfig().set("players." + player.getName() + ".quest", null);
            plugin.saveConfig();
        }
        else {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "У тебя нет задания");
        }
    }

    private void Get(Player player, Rpg_plugin plugin, String[] args) {
        QuestManager questManager = new QuestManager(plugin);

        if(args.length < 2) {
            if(player.isOp())
                questManager.trySetQuest(player.getName());
            else
                player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "У вас нет прав на использование этой команды");
        }
        else if(args.length == 2 && args[1].equals("forclan")) {
            questManager.TrySetClanQuest(player.getName());
        }
    }

    private void Complete(Player player, String[] args, Rpg_plugin plugin) {
        FileConfiguration config;
        boolean is_player_in_clan;
        boolean is_clan_have_quest;
        String clan_name;
        String player_name;

        if(!player.isOp())
            player.sendMessage("У вас нет прав на использование этой команды");

        if (args.length < 2) {
            plugin.getServer().getPluginManager().callEvent(new QuestCompleteEvent(player, 5));
        }
        else if (args.length == 2 && args[1].equals("forclan")){
            config = plugin.getConfig();
            player_name = player.getName();
            is_player_in_clan = config.contains("players." + player_name + ".clan");

            if(!is_player_in_clan) {
                player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Вы не состоите в клане");
                return;
            }

            clan_name = config.getString("players." + player_name + ".clan");
            is_clan_have_quest = config.contains("clans." + clan_name + ".quest");

            if(!is_clan_have_quest) {
                player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "У клана нет задания");
                return;
            }

            plugin.getServer().getPluginManager().callEvent(new ClanQuestCompleteEvent(clan_name, 120));
        }
    }

}
