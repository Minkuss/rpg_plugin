package me.minkuss.rpg_plugin.commands;

import me.minkuss.rpg_plugin.Rpg_plugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LevelCommand extends AbstractCommand {
    public LevelCommand(String name) {
        super(name);
    }

    @Override
    public void execute(Rpg_plugin plugin, CommandSender sender, String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Только игрок может использовать эту команду");
            return;
        }

        if(args.length < 1) {
            player.sendMessage(ChatColor.YELLOW + "[Warning] " + ChatColor.GOLD + "Требуются аргументы");
            return;
        }

        switch(args[0]) {
            case "show" -> Show(player, plugin);
            default -> player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Введены несуществующие аргументы");
        }
    }

    private void Show(Player player, Rpg_plugin plugin) {
        int level = plugin.getConfig().getInt("players." + player.getName() + ".level");
        player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Уровень: " + level);
    }
}
