package me.minkuss.rpg_plugin.listeners;

import me.minkuss.rpg_plugin.Rpg_plugin;
import me.minkuss.rpg_plugin.events.GainedExpEvent;
import org.bukkit.configuration.file.FileConfiguration;
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
        FileConfiguration config = _plugin.getConfig();
        Player player_killer = event.getEntity().getKiller();
        int exp;

        if(player_killer == null) return;

        if (config.contains("exp-info.kill." + event.getEntityType())) {
            exp = config.getInt("exp-info.kill." + event.getEntityType());
            _plugin.getServer().getPluginManager().callEvent(new GainedExpEvent(player_killer, exp));
        }
        else if(event.getEntity() instanceof Player player_victim) {
            String killer_clan_name;
            String victim_clan_name;

            boolean is_killer_in_clan = config.contains("players." + player_killer.getName() + ".clan");
            boolean is_victim_in_clan = config.contains("players." + player_victim.getName() + ".clan");

            if(!is_killer_in_clan || !is_victim_in_clan) return;

            killer_clan_name = config.getString("players." + player_killer.getName() + ".clan");
            victim_clan_name = config.getString("players." + player_victim.getName() + ".clan");

            if(!killer_clan_name.equals(victim_clan_name)) {
                exp = _plugin.getConfig().getInt("exp-info.kill.PLAYER");
                _plugin.getServer().getPluginManager().callEvent(new GainedExpEvent(player_killer, exp));
            }
        }
    }
}
