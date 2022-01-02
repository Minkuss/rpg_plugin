package me.minkuss.rpg_plugin.listeners.clans;

import me.minkuss.rpg_plugin.Rpg_plugin;
import me.minkuss.rpg_plugin.events.clans.InviteEvent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class InviteEventListener implements Listener {
    private final Rpg_plugin _plugin;

    public InviteEventListener(Rpg_plugin plugin) {_plugin = plugin;}

    @EventHandler
    public void onInvitePlayer(InviteEvent event) {
        FileConfiguration config = _plugin.getConfig();
        Player player = event.getPlayer();

        new BukkitRunnable() {
            int accept_time = 30;

            @Override
            public void run() {
                if(accept_time == 0) {
                    config.set("players." + player.getName() + ".accepting", null);
                    config.set("players." + player.getName() + ".acceptingClan", null);
                    config.set("players." + player.getName() + ".inviter", null);
                    _plugin.saveConfig();

                    player.sendMessage(ChatColor.GREEN + "[INFO] " + ChatColor.GOLD + "Время приглашения истекло...");

                    cancel();
                }

                accept_time -= 5;
            }
        }.runTaskTimer(_plugin, 0, 100);
    }
}
