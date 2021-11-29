package me.minkuss.rpg_plugin.listeners;

import me.minkuss.rpg_plugin.Rpg_plugin;
import me.minkuss.rpg_plugin.events.GainedExpEvent;
import org.bukkit.configuration.file.FileConfiguration;
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
        Player player = event.getEntity().getKiller();

        if (_plugin.getConfig().contains("players." + player.getUniqueId()) && player != null) {
            EntityType victim_type = event.getEntity().getType();
            int exp = GetVictimExp(victim_type);

            _plugin.getServer().getPluginManager().callEvent(new GainedExpEvent(player, exp));
        }
    }

    private int GetVictimExp(EntityType type) {
        FileConfiguration config = _plugin.getConfig();
        return switch (type) {
            case PLAYER -> config.getInt("exp-info.kill.player");
            case PILLAGER, WITCH, CREEPER,
                 SLIME, HUSK, CAVE_SPIDER,
                 SPIDER, ZOMBIE, ZOMBIE_VILLAGER,
                 SKELETON, ENDERMAN, PHANTOM -> config.getInt("exp-info.kill.monster");
            default -> 0;
        };
    }
}
