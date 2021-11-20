package me.minkuss.rpg_plugin.listeners;

import me.minkuss.rpg_plugin.Rpg_plugin;
import me.minkuss.rpg_plugin.runnables.CooldownCounter;
import me.minkuss.rpg_plugin.runnables.SneakChecker;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ScoutEventListener implements Listener {

    private final Rpg_plugin _plugin;

    public ScoutEventListener(Rpg_plugin plugin) {
        _plugin = plugin;
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        boolean isScout = false;
        String role_name = _plugin.getConfig().getString("players." + event.getPlayer().getUniqueId() + ".class.name");

        if(role_name != null)
            isScout = role_name.equals("разведчик");

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
    @EventHandler
    public  void onEntityDamageEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof  Player && event.getDamager() instanceof  Player) {
            Player damager = (Player) event.getDamager();
            Player entity = (Player) event.getEntity();
            FileConfiguration config = _plugin.getConfig();

            boolean isScout = config.getString("players." + damager.getUniqueId() + ".class.name").equals("разведчик");
            boolean isSkillOpened = config.getBoolean("players." + damager.getUniqueId() + ".class.skills.rat.opened");

            boolean hasInvisibility = damager.hasPotionEffect(PotionEffectType.INVISIBILITY);
            boolean isCoolDowned = config.getLong("players." + damager.getUniqueId() + ".skills.rat.time-left") == 0;

            if (isScout && isSkillOpened && damager.isSneaking() && hasInvisibility && isCoolDowned) {
                damager.sendMessage("Ты вор получается)))))))))");
                Inventory inventory_entity = entity.getInventory();
                Inventory inventory_damager = damager.getInventory();

                int index = (int)Math.floor(Math.random() * (inventory_entity.getSize()-1));
                ItemStack item = inventory_entity.getItem(index);

                inventory_entity.removeItem(item);
                inventory_damager.addItem(item);

                new CooldownCounter(damager, _plugin, "rat").runTaskTimer(_plugin, 0, 20);
            }
        }
    }
}
