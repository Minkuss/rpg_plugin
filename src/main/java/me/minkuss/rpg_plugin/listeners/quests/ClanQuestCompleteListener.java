package me.minkuss.rpg_plugin.listeners.quests;

import me.minkuss.rpg_plugin.Rpg_plugin;
import me.minkuss.rpg_plugin.events.GainedExpEvent;
import me.minkuss.rpg_plugin.events.quests.ClanQuestCompleteEvent;
import me.minkuss.rpg_plugin.events.quests.QuestCompleteEvent;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.util.List;

public class ClanQuestCompleteListener implements Listener {

    private final Rpg_plugin _plugin;

    public ClanQuestCompleteListener(Rpg_plugin plugin) {
        _plugin = plugin;
    }

    @EventHandler
    public void onClanQuestComplete(ClanQuestCompleteEvent event) {
        Server server = _plugin.getServer();
        PluginManager pluginManager = server.getPluginManager();
        FileConfiguration config = _plugin.getConfig();
        String clan_name = event.getClanName();
        List<String> participants = config.getStringList("clans." + clan_name + ".participants");

        config.set("clans." + clan_name + ".quest", null);
        config.set("clans." + clan_name + ".exp", event.getExp());

        for(String participant : participants) {
            Player player = server.getPlayer(participant);

            if(player != null) {
                pluginManager.callEvent(new GainedExpEvent(player, event.getExp() / participants.size() ));
                player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Клановое задание вылненно");
            }
        }

        _plugin.saveConfig();
    }

}
