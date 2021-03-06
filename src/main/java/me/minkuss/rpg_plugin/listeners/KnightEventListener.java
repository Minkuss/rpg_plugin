package me.minkuss.rpg_plugin.listeners;

import me.minkuss.rpg_plugin.RoleManager;
import me.minkuss.rpg_plugin.Rpg_plugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class KnightEventListener implements Listener {
    private final Rpg_plugin _plugin;

    public KnightEventListener(Rpg_plugin plugin) {
        _plugin = plugin;
    }

    @EventHandler
    public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player player && event.getEntity() instanceof Damageable entity) {
            boolean is_knight = new RoleManager(_plugin).hasRole(player.getName(), "рыцарь");

            if(is_knight) {
                FileConfiguration config = _plugin.getConfig();

                boolean is_berserk_opened = config.getBoolean("players." + player.getName() + ".class.skills.berserk.opened");
                boolean is_lifesteal_opened = config.getBoolean("players." + player.getName() + ".class.skills.lifesteal.opened");

                double hp = player.getHealth();
                double damage = (20 - hp) / 2;

                if(is_berserk_opened) {
                    entity.setHealth(entity.getHealth() - damage);
                }
                if (is_lifesteal_opened) {
                    boolean chance = Math.floor(Math.random()) * 100 <= player.getLastDamage() + damage;
                    if (chance) {
                        player.setHealth(hp + 2);
                    }
                }
            }

        }
    }

}
