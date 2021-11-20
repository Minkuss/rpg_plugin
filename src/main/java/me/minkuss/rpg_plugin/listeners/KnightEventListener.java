package me.minkuss.rpg_plugin.listeners;

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

        if(event.getDamager() instanceof Player && event.getEntity() instanceof Damageable) {

            boolean isKnight = false;
            String role_name = _plugin.getConfig().getString("players." + event.getDamager().getUniqueId() + ".class.name");

            if(role_name != null)
                isKnight = role_name.equals("рыцарь");

            if(isKnight) {
                FileConfiguration config = _plugin.getConfig();

                Damageable entity = ((Damageable) event.getEntity());
                Player player = (Player) event.getDamager();

                boolean isBerserkOpened = config.getBoolean("players." + event.getDamager().getUniqueId() + ".class.skills.berserk.opened");
                boolean isLifeStealOpened = config.getBoolean("players." + event.getDamager().getUniqueId() + ".class.skills.lifesteal.opened");

                double hp = player.getHealth();
                double damage = (20 - hp) / 2;

                if(isBerserkOpened) {
                    entity.setHealth(entity.getHealth() - damage);
                }
                if (isLifeStealOpened) {
                    boolean chance = Math.floor(Math.random())*100 <= player.getLastDamage() + damage;
                    if (chance) {
                        player.setHealth(hp + 2);
                    }
                }
            }
        }

    }
}
