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
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Only player can use that command");
        }

        Player player = (Player)sender;

        if(args.length > 0) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Too many arguments");
        }
        else {
            int level = _plugin.getConfig().getInt("players." + player.getUniqueId() + ".level");
            player.sendMessage(ChatColor.GOLD + "Ваш уровень: " + level);
        }

        return false;
    }
}
