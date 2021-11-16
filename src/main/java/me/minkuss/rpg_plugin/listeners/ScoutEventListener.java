package me.minkuss.rpg_plugin.listeners;

import me.minkuss.rpg_plugin.Rpg_plugin;
import me.minkuss.rpg_plugin.runnables.CooldownCounter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ScoutEventListener implements Listener {

    private final Rpg_plugin _plugin;

    public ScoutEventListener(Rpg_plugin plugin) {
        _plugin = plugin;
    }

    @EventHandler
    public void onScoutGetDamage(EntityDamageEvent event) {
        if(!(event.getEntity() instanceof Player)){
            return;
        }

        Player player = (Player) event.getEntity();
        FileConfiguration config = _plugin.getConfig();
        String role = config.getString("players." + player.getUniqueId() + ".class.name");

        double hp = player.getHealth();
        boolean hasInvisibility = player.hasPotionEffect(PotionEffectType.INVISIBILITY);
        boolean hasSpeed = player.hasPotionEffect(PotionEffectType.SPEED);

        if (hp <= 10 && !hasInvisibility && role.equals("разведчик") && !hasSpeed) {
            if (config.getBoolean("players." + player.getUniqueId() + ".class.skills.invisibility.opened")){

                if (config.getLong("players." + player.getUniqueId() + ".class.разведчик.time-left") == 0) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 600, 1, false, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 1, false, false));
                    player.sendMessage("Активирована способность 'бегу до дому'");

                    new CooldownCounter(player, _plugin, "invisibility").run();

                    _plugin.saveConfig();
                }
            }
        }
    }
}
