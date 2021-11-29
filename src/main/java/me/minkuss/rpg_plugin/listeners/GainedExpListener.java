package me.minkuss.rpg_plugin.listeners;

import me.minkuss.rpg_plugin.Rpg_plugin;
import me.minkuss.rpg_plugin.events.GainedExpEvent;
import me.minkuss.rpg_plugin.events.LevelUpEvent;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GainedExpListener implements Listener {

    private final Rpg_plugin _plugin;

    public GainedExpListener(Rpg_plugin plugin) { _plugin = plugin; }

    @EventHandler
    public void onPlayerGainedExp(GainedExpEvent event) {

        if(event.getExperience() > 0) {
            FileConfiguration config = _plugin.getConfig();
            Player player = event.getPlayer();

            int exp = event.getExperience();
            int level = config.getInt("players." + player.getUniqueId() + ".level");
            int start_value = config.getInt("exp-info.start-value");
            int level_scale = config.getInt("exp-info.level-scale");
            int newLevelBarrier = start_value * (level_scale * level);

            player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Experience +" + exp);

            exp += config.getInt("players." + player.getUniqueId() + ".exp");

            while(exp >= newLevelBarrier) {
                exp -= newLevelBarrier;
                level += 1;

                _plugin.getServer().getPluginManager().callEvent(new LevelUpEvent(player, level));

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 1, 1);
                player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "New level " + level);
            }

            config.set("players." + player.getUniqueId() + ".level", level);
            config.set("players." + player.getUniqueId() + ".exp", exp);
            _plugin.saveConfig();
        }

    }

}
