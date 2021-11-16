package me.minkuss.rpg_plugin.commands;

import me.minkuss.rpg_plugin.Rpg_plugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class GiveClassCommand implements CommandExecutor {
    private Rpg_plugin _plugin;
    public GiveClassCommand(Rpg_plugin plugin) {
        _plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String str, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.BLACK + "Эту команду может отправить только игрок, чел...");
            return  false;
        }
        Player p = (Player)sender;
        if (!p.hasPermission("rpg_plugin.giveclass")) {
            return  false;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.BLACK + "Введите команду полностью");
            return  false;
        }
        if (args.length == 1) {
            List<String> roles = _plugin.getConfig().getStringList("role-names");
            for (String role : roles) {
                if (role.equals(args[0])) {
                    _plugin.getConfig().set("players." + p.getUniqueId() + ".class.name", args[0]);
                    _plugin.getConfig().set("players." + p.getUniqueId() + ".class.opened", true);
                    _plugin.saveConfig();
                    p.sendMessage(ChatColor.GREEN + "Вам присвоена роль: " + args[0]);
                    return true;
                }
            }
            p.sendMessage(ChatColor.RED + "Такого класса не существует!");
        }

        return false;
    }
}
