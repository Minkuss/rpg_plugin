package me.minkuss.rpg_plugin.commands;

import me.minkuss.rpg_plugin.Rpg_plugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CancelQuestCommand implements CommandExecutor {

    private final Rpg_plugin _plugin;

    public CancelQuestCommand(Rpg_plugin plugin) {
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

        if(config.contains("players." + player.getUniqueId() + ".quest")) {
            player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Задание отмененно");
            config.set("players." + player.getUniqueId() + ".quest", null);
            _plugin.saveConfig();
        }
        else {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "У тебя нет задания");
        }

        return true;
    }
}
