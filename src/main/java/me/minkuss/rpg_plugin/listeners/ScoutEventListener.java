package me.minkuss.rpg_plugin.listeners;

import me.minkuss.rpg_plugin.Rpg_plugin;
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
        FileConfiguration config = _plugin.getConfig();
        if(!(event.getEntity() instanceof Player)){
            return;
        }
        Player player = (Player) event.getEntity();
        double hp = player.getHealth();
        String role = config.getString("players." + player.getUniqueId() + ".class.name");
        if (hp <= 10 && !player.hasPotionEffect(PotionEffectType.INVISIBILITY) && role.equals("разведчик") && !player.hasPotionEffect(PotionEffectType.SPEED)) {
            if (config.getBoolean("players." + player.getUniqueId() + ".class.skills.invisibility.opened") == true){
                player.sendMessage("Бебра");
                Long kd = config.getLong("players." + player.getUniqueId() + ".class.skills.invisibility.kd");
                Long lst_use = config.getLong("players." + player.getUniqueId() + ".class.skills.invisibility.last-use");
                Long crnt_use = player.getPlayerTime();
                if ((crnt_use - lst_use) >= kd || (crnt_use - lst_use) + 24000 >= kd || lst_use == null) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 600, 1, false, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 1, false, false));
                    player.sendMessage("Активирована способность 'бегу до дому'");
                    config.set("players." + player.getUniqueId() + ".class.skills.invisibility.last-use", crnt_use);
                    _plugin.saveConfig();
                }
            }
            // x - время использования
            // y - текущее время
            // y - x >= 1200
            // x = 24000
            // y = 1200
            // 1200 - 24000 = -22800
        }
    }
}
