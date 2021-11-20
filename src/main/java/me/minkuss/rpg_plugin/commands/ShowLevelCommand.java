package me.minkuss.rpg_plugin.commands;

import me.minkuss.rpg_plugin.Rpg_plugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShowLevelCommand implements CommandExecutor {
    private final Rpg_plugin _plugin;

    public ShowLevelCommand(Rpg_plugin plugin) {
        _plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Только игрок может использовать эту команду");
            return false;
        }

        if(args.length != 0) {
            player.sendMessage(ChatColor.YELLOW + "[Warning] " + ChatColor.GOLD + "Аргументы не требуются");

            int level = _plugin.getConfig().getInt("players." + player.getUniqueId() + ".level");
            player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Уровень: " + level);
        }
        return true;
    }
}
