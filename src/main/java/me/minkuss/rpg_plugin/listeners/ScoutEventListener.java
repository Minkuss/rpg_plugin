package me.minkuss.rpg_plugin.listeners;

import me.minkuss.rpg_plugin.Rpg_plugin;
import me.minkuss.rpg_plugin.runnables.CooldownCounter;
import me.minkuss.rpg_plugin.runnables.SneakChecker;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ScoutEventListener implements Listener {

    private final Rpg_plugin _plugin;

    public ScoutEventListener(Rpg_plugin plugin) {
        _plugin = plugin;
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        boolean isScout = _plugin.getConfig().getString("players." + event.getPlayer().getUniqueId() + ".class.name").equals("разведчик");

        if(isScout) {
            FileConfiguration config = _plugin.getConfig();
            Player player = event.getPlayer();

            boolean isSkillOpened = config.getBoolean("players." + player.getUniqueId() + ".class.skills.sneaky-crouch.opened");

            if(isSkillOpened) new SneakChecker(player).runTaskTimer(_plugin, 20, 40);
        }
    }

    @EventHandler
    public void onEntityGotDamage(EntityDamageEvent event) {

        if(event.getEntity() instanceof Player) {
            boolean isScout = _plugin.getConfig().getString("players." + event.getEntity().getUniqueId() + ".class.name").equals("разведчик");

            if (isScout) {
                FileConfiguration config = _plugin.getConfig();
                Player player = (Player) event.getEntity();

                double hp = player.getHealth() - event.getDamage();

                boolean isSkillOpened = config.getBoolean("players." + player.getUniqueId() + ".class.skills.invisibility.opened");
                boolean isCoolDowned = config.getLong("players." + player.getUniqueId() + ".class.skills.invisibility.time-left") == 0;

                if (isCoolDowned && hp <= 6 && isSkillOpened) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 300, 1, false, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 1, false, false));
                    player.sendMessage("Активирована способность 'бегу до дому'");

                    new CooldownCounter(player, _plugin, "invisibility").runTaskTimer(_plugin, 0, 20);
                }
            }

        }

    }
}
