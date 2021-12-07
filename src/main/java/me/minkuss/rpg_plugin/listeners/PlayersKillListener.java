package me.minkuss.rpg_plugin.listeners;

import me.minkuss.rpg_plugin.Rpg_plugin;
import me.minkuss.rpg_plugin.events.GainedExpEvent;
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

        if (player != null && _plugin.getConfig().contains("exp-info.kill." + event.getEntityType())) {
            int exp = _plugin.getConfig().getInt("exp-info.kill." + event.getEntityType());
            _plugin.getServer().getPluginManager().callEvent(new GainedExpEvent(player, exp));
        }
    }
}
