package me.minkuss.rpg_plugin.listeners;

import me.minkuss.rpg_plugin.Rpg_plugin;
import me.minkuss.rpg_plugin.events.GainedExpEvent;
import me.minkuss.rpg_plugin.events.LevelUpEvent;
import org.bukkit.ChatColor;
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
            EntityType victim = event.getEntity().getType();

            int exp = GetVictimExp(victim);
            _plugin.getServer().getPluginManager().callEvent(new GainedExpEvent(player, exp));
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
