package me.minkuss.rpg_plugin.commands;

import me.minkuss.rpg_plugin.RoleManager;
import me.minkuss.rpg_plugin.Rpg_plugin;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class RoleCommand extends AbstractCommand {
    public RoleCommand(String name) {super(name);}

    @Override
    public void execute(Rpg_plugin plugin, CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Только игрок может использовать эту команду");
            return;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Требуются аргументы");
            return;
        }

        switch (args[0]) {
            case "show" -> Show(player, plugin.getConfig());
            case "give" -> Give(player, new RoleManager(plugin), args[1]);
            case "set" -> Set(player, plugin, args[1],args[2]);
            default -> player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Введены несуществующие аргументы");
        }
    }

    private void Show(Player player, FileConfiguration cfg) {
        String role = cfg.getString("players." + player.getName() + ".class.name");

        if (role == null) {
            player.sendMessage(ChatColor.YELLOW + "[Warning] " + ChatColor.GOLD + "У тебя нет роли, чтобы её получить используй команду \"/giverole <название роли>\"");
            return;
        }

        player.sendMessage(ChatColor.GOLD + "Твоя роль: " + role);
    }

    private void Give(Player player, RoleManager roleManager, String role) {
        switch (role) {
            case "разведчик" -> {
                roleManager.setScout(player.getName());
                player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Вам присвоена роль: " + role);
                return;
            }
            case "рыцарь" -> {
                roleManager.setKnight(player.getName());
                player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Вам присвоена роль: " + role);
                return;
            }
        }

        player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Такого класса не существует!");
    }

    private void Set(Player player, Rpg_plugin plugin, String player_name,  String role) {
        Player player_for_set = plugin.getServer().getPlayer(player_name);

        if(player_for_set == null) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Не удалось найти игрока");
            return;
        }

        switch (role) {
            case "разведчик" -> {
                new RoleManager(plugin).setScout(player.getName());
                player_for_set.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Вам присвоена роль: " + role);
                player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Игроку " + player_name + " присвоена роль: " + role);
                return;
            }
            case "рыцарь" -> {
                new RoleManager(plugin).setKnight(player.getName());
                player_for_set.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Вам присвоена роль: " + role);
                player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Игроку " + player_name + " присвоена роль: " + role);
                return;
            }
        }

        player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Такого класса не существует!");
    }
}
