package me.minkuss.rpg_plugin.commands;

import me.minkuss.rpg_plugin.Rpg_plugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ExpCommand extends AbstractCommand {
    public ExpCommand(String name) {super(name);}

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
            case "show" -> Show(player, plugin.getConfig());
            default -> player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Введены несуществующие аргументы");
        }
    }

    private void Show(Player player, FileConfiguration cfg) {
        int exp = cfg.getInt("players." + player.getName() + ".exp");
        int level = cfg.getInt("players." + player.getName() + ".level");
        int level_scale = cfg.getInt("exp-info.level-scale");
        int newLevelBarrier = (int)Math.pow(level, 2) * level_scale;

        player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Опыт: " + exp + "/" + newLevelBarrier);
    }

}
