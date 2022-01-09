package me.minkuss.rpg_plugin.listeners;

import me.minkuss.rpg_plugin.RoleManager;
import me.minkuss.rpg_plugin.Rpg_plugin;
import me.minkuss.rpg_plugin.runnables.CooldownCounter;
import me.minkuss.rpg_plugin.runnables.SneakChecker;
import org.bukkit.ChatColor;
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

        boolean is_scout = new RoleManager(_plugin).hasRole(event.getPlayer().getName(), "разведчик");

        if(is_scout) {
            FileConfiguration config = _plugin.getConfig();
            Player player = event.getPlayer();

            boolean is_skill_opened = config.getBoolean("players." + player.getName() + ".class.skills.sneaky-crouch.opened");

            if(is_skill_opened) new SneakChecker(player).runTaskTimer(_plugin, 20, 40);
        }
    }

    @EventHandler
    public void onEntityGotDamage(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player player) {
            String player_name = player.getName();
            boolean is_scout = new RoleManager(_plugin).hasRole(player_name, "разведчик");

            if (is_scout) {
                FileConfiguration config = _plugin.getConfig();

                double hp = player.getHealth() - event.getDamage();

                boolean is_skill_opened = config.getBoolean("players." + player_name + ".class.skills.invisibility.opened");
                boolean is_cool_downed = config.getLong("players." + player_name + ".class.skills.invisibility.time-left") == 0;

                if (is_cool_downed && hp <= 6 && is_skill_opened) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 300, 1, false, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 1, false, false));
                    player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Активирована способность 'невидимость'");

                    new CooldownCounter(player_name, _plugin, "invisibility").runTaskTimer(_plugin, 0, 20);
                }
            }

        }

    }

    @EventHandler
    public  void onEntityDamageEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player entity && event.getDamager() instanceof Player damager) {
            FileConfiguration config = _plugin.getConfig();

            String damager_name = damager.getName();

            boolean is_scout = new RoleManager(_plugin).hasRole(damager_name, "разведчик");
            boolean is_skill_opened = config.getBoolean("players." + damager_name + ".class.skills.rat.opened");
            boolean has_invisibility = damager.hasPotionEffect(PotionEffectType.INVISIBILITY);
            boolean is_cool_downed = config.getLong("players." + damager_name + ".skills.rat.time-left") == 0;

            if (is_scout && is_skill_opened && damager.isSneaking() && has_invisibility && is_cool_downed) {
                //damager.sendMessage("Ты вор получается)))))))))");
                Inventory inventory_entity = entity.getInventory();
                Inventory inventory_damager = damager.getInventory();

                int index = (int)Math.floor(Math.random() * (inventory_entity.getSize()-1));
                ItemStack item = inventory_entity.getItem(index);

                inventory_entity.removeItem(item);
                inventory_damager.addItem(item);

                new CooldownCounter(damager_name, _plugin, "rat").runTaskTimer(_plugin, 0, 20);
            }
        }
    }
}
