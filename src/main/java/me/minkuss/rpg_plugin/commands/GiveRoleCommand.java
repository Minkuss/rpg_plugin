package me.minkuss.rpg_plugin.commands;

import me.minkuss.rpg_plugin.RoleManager;
import me.minkuss.rpg_plugin.Rpg_plugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class GiveRoleCommand implements CommandExecutor {
    private final Rpg_plugin _plugin;

    public GiveRoleCommand(Rpg_plugin plugin) {
        _plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String str, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Эту команду может отправить только игрок, чел...");
            return  false;
        }

        Player player = (Player)sender;

        if (!player.hasPermission("rpg_plugin.giveclass")) {
            return  false;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Мало аргументов");
            return  false;
        }
        if (args.length == 1) {
            FileConfiguration config = _plugin.getConfig();
            List<String> roles = config.getStringList("role-names");

            for (String role : roles) {
                if (role.equals(args[0])) {

                    if(role.equals("разведчик")) new RoleManager(_plugin).setScout(player.getUniqueId());
                    else if(role.equals("рыцарь")) new RoleManager(_plugin).setKnight(player.getUniqueId());

                    player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Вам присвоена роль: " + args[0]);
                    return true;
                }
            }
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Такого класса не существует!");
        }

        return false;
    }

}
