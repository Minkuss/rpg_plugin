package me.minkuss.rpg_plugin.commands;

import me.minkuss.rpg_plugin.Rpg_plugin;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class ReqCommand extends AbstractCommand {
    public ReqCommand(String name) {
        super(name);
    }

    @Override
    public void execute(Rpg_plugin plugin, CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Эту команду может отправить только игрок.");
            return;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Чтобы увидеть перечень всех комманд, напишите /clan help");
            return;
        }

        boolean has_joins = plugin.getConfig().contains("players." + sender.getName() + ".joiners");
        if (!has_joins) {
            sender.sendMessage(ChatColor.GOLD + "У вас нет активных запросов");
            return;
        }

        switch (args[0]) {
            case "clean" -> Clean(player, plugin);
            case "accept" -> Accept(player, plugin, args);
        }
    }

    private void Clean(Player player, Rpg_plugin plugin) {
        FileConfiguration config = plugin.getConfig();

        config.set("players." + player.getName() + ".joiners", null);
        player.sendMessage(ChatColor.GREEN + "Вы успешно очистили список запросов");
        List<String> joins = config.getStringList("players." + player.getName() + ".joiners");

        for (String item : joins) {
            if (plugin.getServer().getPlayer(item) != null) {
                plugin.getServer().getPlayer(item).sendMessage(ChatColor.RED + "К сожалению, ваш запрос на вступление в клан отклонили");
            } else {
                config.set("players." + item + ".message", "К сожалению, ваш запрос на вступление в клан отклонили");
            }
        }

        plugin.saveConfig();
    }

    private void Accept(Player player, Rpg_plugin plugin, String[] args) {
        FileConfiguration config = plugin.getConfig();

        if (args.length == 2) {

            String player_name = args[1];
            List<String> joins = config.getStringList("players." + player.getName() + ".joiners");
            boolean is_joiner = joins.contains(player_name);

            if (is_joiner) {
                String clanName = config.getString("players." + player.getName() + ".clan");
                int mates_nubmer = config.getInt("clans." + clanName + ".clanmates");
                List<String> participants = config.getStringList("clans." + clanName + ".participants");

                config.set("clans." + clanName + ".clanmates", mates_nubmer + 1);
                participants.add(player_name);
                config.set("clans." + clanName + ".participants", participants);
                config.set("players." + player_name + ".clan", clanName);

                player.sendMessage(ChatColor.GREEN + "Вы успешно добавили игрока");

                joins.remove(player_name);
                config.set("players." + player.getName() + ".joiners", joins);

                if (plugin.getServer().getPlayer(player_name) != null) {
                    plugin.getServer().getPlayer(player_name).sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Вас приняли в клан - " + clanName + "!");
                } else if (plugin.getServer().getPlayer(player_name) == null) {
                    config.set("players." + player_name + ".message", "Вас приняли в клан - " + clanName);
                }

                plugin.saveConfig();
            }
            else {
                player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Такого игрока нет в запросах");
                return;
            }
            return;
        }

        if (args.length == 1) {
            List<String> joins = config.getStringList("players." + player.getName() + ".joiners");
            String clanName = config.getString("players." + player.getName() + ".clan");
            int mates_nubmer = config.getInt("clans." + clanName + ".clanmates");
            List<String> clanplayers = config.getStringList("clans." + clanName + ".participants");
            int playersnum = joins.size();

            clanplayers.addAll(joins);

            config.set("clans." + clanName + ".participants", clanplayers);
            config.set("clans." + clanName + ".clanmates", mates_nubmer + playersnum);

            player.sendMessage(ChatColor.GREEN + "Вы успешно добавили всех игроков");

            for (String item : joins) {
                config.set("players." + item + ".clan", clanName);

                if (plugin.getServer().getPlayer(item) != null) {
                    plugin.getServer().getPlayer(item).sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Вас приняли в клан - " + clanName + "!");
                } else if (plugin.getServer().getPlayer(item) == null) {
                    config.set("players." + item + ".message", "Вас приняли в клан - " + clanName);
                }
            }

            config.set("players." + player.getName() + ".joiners", null);

            plugin.saveConfig();
        }
    }

}


