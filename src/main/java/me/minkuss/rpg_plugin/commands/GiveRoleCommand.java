package me.minkuss.rpg_plugin.commands;

import me.minkuss.rpg_plugin.Rpg_plugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class GiveRoleCommand implements CommandExecutor {
    private Rpg_plugin _plugin;
    public GiveRoleCommand(Rpg_plugin plugin) {
        _plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String str, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.BLACK + "Эту команду может отправить только игрок, чел...");
            return  false;
        }
        Player player = (Player)sender;
        if (!player.hasPermission("rpg_plugin.giveclass")) {
            return  false;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.BLACK + "Введите команду полностью");
            return  false;
        }
        if (args.length == 1) {
            FileConfiguration config = _plugin.getConfig();
            List<String> roles = config.getStringList("role-names");
            for (String role : roles) {
                if (role.equals(args[0])) {

                    if(role.equals("разведчик")) SetScoutRole(player);
                    else if(role.equals("рыцарь")) SetKnightRole(player);

                    player.sendMessage(ChatColor.GREEN + "Вам присвоена роль: " + args[0]);
                    return true;
                }
            }
            player.sendMessage(ChatColor.RED + "Такого класса не существует!");
        }

        return false;
    }

    private void SetKnightRole(Player player) {
        FileConfiguration config = _plugin.getConfig();
        config.set("players." + player.getUniqueId() + ".class.name", "рыцарь");
        config.createSection("players." + player.getUniqueId() + ".class.skills.berserk");
        config.set("players." + player.getUniqueId() + ".class.skills.berserk.opened", true);
        _plugin.saveConfig();
    }

    private void SetScoutRole(Player player) {
        FileConfiguration config = _plugin.getConfig();
        config.set("players." + player.getUniqueId() + ".class.name", "разведчик");

        config.createSection("players." + player.getUniqueId() + ".class.skills.invisibility");
        config.set("players." + player.getUniqueId() + ".class.skills.invisibility.opened", true);
        config.set("players." + player.getUniqueId() + ".class.skills.invisibility.cd", 60);

        config.createSection("players." + player.getUniqueId() + ".class.skills.sneaky-crouch");
        config.set("players." + player.getUniqueId() + ".class.skills.sneaky-crouch.opened", true);

        _plugin.saveConfig();
    }


}
