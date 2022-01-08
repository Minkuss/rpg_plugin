package me.minkuss.rpg_plugin.listeners.quests;

import me.minkuss.rpg_plugin.Rpg_plugin;
import me.minkuss.rpg_plugin.events.GainedExpEvent;
import me.minkuss.rpg_plugin.events.quests.QuestCompleteEvent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class QuestCompleteListener implements Listener {

    private final Rpg_plugin _plugin;

    public QuestCompleteListener(Rpg_plugin plugin) {
        _plugin = plugin;
    }

    @EventHandler
    public void onQuestComplete(QuestCompleteEvent event) {

        FileConfiguration config = _plugin.getConfig();
        Player player = event.getPlayer();
        int money = config.getInt("players." + player.getName() + ".money");

        config.set("players." + player.getName() + ".quest", null);
        config.set("players." + player.getName() + ".money", event.getMoney() + money);
        _plugin.saveConfig();
        int balance = config.getInt("players." + player.getName() + ".money");

        player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Задание вылненно");
        player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Вы получили - " + event.getMoney() + ". Ваш баланс - " + balance);
        _plugin.getServer().getPluginManager().callEvent(new GainedExpEvent(player, event.getExp()));
    }

}
