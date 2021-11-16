package me.minkuss.rpg_plugin.commands;

import me.minkuss.rpg_plugin.Rpg_plugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SetRoleCommand implements CommandExecutor {
    private Rpg_plugin _plugin;
    public SetRoleCommand(Rpg_plugin plugin) {
        _plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String str, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.BLACK + "Эту команду может отправить только игрок, чел...");
            return  false;
        }
        Player p = (Player)sender;
        if (!p.hasPermission("rpg_plugin.setclass")) {
            p.sendMessage(ChatColor.RED + "У вас нет прав на использование этой команды");
            return  false;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Введите команду полностью");
            return  false;
        }
        if (args.length == 2) {
            Player playerTest = _plugin.getServer().getPlayer(args[0]);
            List<String> roles = _plugin.getConfig().getStringList("role-names");
            if (playerTest == null) {
                return false;
            }
            for (String role : roles) {
                if (role.equals(args[1])) {
                    _plugin.getConfig().set("players." + playerTest.getUniqueId() + ".class.name", args[1]);
                    _plugin.getConfig().set("players." + playerTest.getUniqueId() + ".class.opened?", true);
                    _plugin.saveConfig();
                    playerTest.sendMessage(ChatColor.GREEN + "Вам присвоена роль: " + args[1]);
                    return  true;
                }
            }
            sender.sendMessage(ChatColor.RED + "Такого класса не существует!");
        }
        return false;
    }
}
