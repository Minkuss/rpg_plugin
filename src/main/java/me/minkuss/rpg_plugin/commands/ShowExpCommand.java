package me.minkuss.rpg_plugin.commands;

import me.minkuss.rpg_plugin.Rpg_plugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ShowExpCommand implements CommandExecutor {

    private final Rpg_plugin _plugin;

    public ShowExpCommand(Rpg_plugin plugin) {
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
            FileConfiguration config = _plugin.getConfig();

            int exp = config.getInt("players." + player.getUniqueId() + ".exp");
            int level = config.getInt("players." + player.getUniqueId() + ".level");
            int start_value = config.getInt("exp-info.start-value");
            int level_scale = config.getInt("exp-info.level-scale");
            int newLevelBarrier = start_value * (level * level_scale);

            player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Your experience: " + exp + "/" + newLevelBarrier);
        }

        return false;
    }
}
