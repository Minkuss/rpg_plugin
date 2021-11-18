package me.minkuss.rpg_plugin.listeners;

import me.minkuss.rpg_plugin.Rpg_plugin;
import me.minkuss.rpg_plugin.events.LevelupEvent;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class PlayersKillListener implements Listener {

    private final Rpg_plugin _plugin;

    public PlayersKillListener(Rpg_plugin plugin) {
        _plugin = plugin;
    }

    @EventHandler
    public void onKillPlayer(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null && event.getEntity().getKiller() instanceof Player) {

            Player player = event.getEntity().getKiller();
            Entity victim = event.getEntity();

            int exp = GetVictimExp(victim.getType());

            if (exp != 0) {
                FileConfiguration config = _plugin.getConfig();

                int level = config.getInt("players." + player.getUniqueId() + ".level");
                int start_value = config.getInt("exp-info.start-value");
                int level_scale = config.getInt("exp-info.level-scale");
                int newLevelBarrier = start_value * (level_scale * level);

                exp += config.getInt("players." + player.getUniqueId() + ".exp");

                if (newLevelBarrier <= exp) {
                    exp -= newLevelBarrier;

                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 1, 1);
                    level++;
                    player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "New level " + level);
                }

                newLevelBarrier = start_value * (level_scale * level);
                player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Your experience: " + exp + "/" + newLevelBarrier);

                config.set("players." + player.getUniqueId() + ".level", level);
                config.set("players." + player.getUniqueId() + ".exp", exp);
                _plugin.saveConfig();
                _plugin.getServer().getPluginManager().callEvent(new LevelupEvent(player, level));
            }
        }
    }

    private int GetVictimExp(EntityType type) {
        FileConfiguration config = _plugin.getConfig();
        switch(type) {
            case PHANTOM:
            case PLAYER:
                return config.getInt("exp-info.kill.player");
            case PILLAGER:
            case WITCH:
            case CREEPER:
            case SLIME:
            case HUSK:
            case CAVE_SPIDER:
            case SPIDER:
            case ZOMBIE:
            case ZOMBIE_VILLAGER:
            case SKELETON:
            case ENDERMAN:
                return config.getInt("exp-info.kill.monster");
            default:
                return 0;
        }
    }
}
