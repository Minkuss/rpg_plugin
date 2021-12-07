package me.minkuss.rpg_plugin.commands;

import me.minkuss.rpg_plugin.Rpg_plugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShowRoleCommand implements CommandExecutor {
    private final Rpg_plugin _plugin;

    public ShowRoleCommand(Rpg_plugin plugin) {
        _plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage();
            return false;
        }

        String role = _plugin.getConfig().getString("players." + player.getUniqueId() + ".class.name");

        if (role == null) {
            player.sendMessage(ChatColor.YELLOW + "[Warning]" + ChatColor.GOLD + "У тебя нет роли, чтобы её получить используй команду \"/giverole <название роли>\"");
            return false;
        }

        player.sendMessage(ChatColor.GOLD + "Твоя роль: " + role);
        return true;
    }
}
