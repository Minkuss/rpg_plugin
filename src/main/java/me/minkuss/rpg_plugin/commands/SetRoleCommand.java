package me.minkuss.rpg_plugin.commands;

import me.minkuss.rpg_plugin.RoleManager;
import me.minkuss.rpg_plugin.Rpg_plugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SetRoleCommand implements CommandExecutor {
    private final Rpg_plugin _plugin;

    public SetRoleCommand(Rpg_plugin plugin) {
        _plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String str, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.BLACK + "Только игрок может использовать эту команду");
            return false;
        }

        if (!player.hasPermission("rpg_plugin.setrole")) {
            player.sendMessage(ChatColor.RED + "У вас нет прав на использование этой команды");
            return false;
        }
        if (args.length != 2) {
            sender.sendMessage(ChatColor.RED + "Введите команду полностью");
            return false;
        }

        Player player_for_set = _plugin.getServer().getPlayer(args[0]);
        List<String> roles = _plugin.getConfig().getStringList("role-names");

        if (player_for_set == null) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Не удалось найти игрока");
            return false;
        }

        for (String role : roles) {
            if (role.equals(args[1])) {

                if(role.equals("разведчик")) new RoleManager(_plugin).setScout(player_for_set.getUniqueId());
                else if(role.equals("рыцарь")) new RoleManager(_plugin).setKnight(player_for_set.getUniqueId());

                player_for_set.sendMessage(ChatColor.GREEN + "Вам присвоена роль: " + args[1]);
                return  true;
            }
        }

        sender.sendMessage(ChatColor.RED + "Такого класса не существует!");
        return false;
    }
}
