package me.minkuss.rpg_plugin.commands;

import me.minkuss.rpg_plugin.Rpg_plugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Locale;

public class ShowQuestCommand implements CommandExecutor {

    private final Rpg_plugin _plugin;

    public ShowQuestCommand(Rpg_plugin plugin) {
        _plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String str, String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Только игрок может использовать эту команду");
            return false;
        }

        if(args.length != 0) {
            player.sendMessage(ChatColor.YELLOW + "[Warning] " + ChatColor.GOLD + "Аргументы не требуются");
            return false;
        }

        FileConfiguration config = _plugin.getConfig();
        List<String> params = config.getStringList("players." + player.getUniqueId() + ".quest.objective");

        if(params.isEmpty()) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "У тебя нет задания");
            return false;
        }

        if(params.get(0).equals("kill")) {
            int goal = config.getInt("players." + player.getUniqueId() + ".quest.goal");
            int progress = config.getInt("players." + player.getUniqueId() + ".quest.progress");

            player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Задание: убить " + params.get(1).toLowerCase(Locale.ROOT) + ", " + progress + "/" + goal);
            return true;
        }
        else if(params.get(0).equals("bring")) {
            int goal = config.getInt("players." + player.getUniqueId() + ".quest.goal");

            player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Задание: принести " + params.get(1).toLowerCase(Locale.ROOT) + ", в количестве: " + goal);
            return true;
        }

        return false;
    }
}
