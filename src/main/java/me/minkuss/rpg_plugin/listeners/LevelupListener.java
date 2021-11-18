package me.minkuss.rpg_plugin.listeners;

import me.minkuss.rpg_plugin.Rpg_plugin;
import me.minkuss.rpg_plugin.events.LevelupEvent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class LevelupListener implements Listener {
    private final Rpg_plugin _plugin;

    public LevelupListener(Rpg_plugin plugin) {
        _plugin = plugin;
    }

    @EventHandler

    public void onPlayerLevelup(LevelupEvent event) {
        if (event.getLevel() == 5) {
            FileConfiguration config = _plugin.getConfig();
            String role = config.getString("players." + event.getPlayer().getUniqueId() + ".class.name");
            UUID id = event.getPlayer().getUniqueId();
            Player player = event.getPlayer();
            if (role.equals("разведчик")) {
                if (config.getBoolean("players." + id + ".class.skills.sneaky-crouch.opened") == false) {
                    config.set("players." + id + ".class.skills.sneaky-crouch.opened", true);
                    player.sendMessage(ChatColor.GOLD + "Вы открыли способность: скрытное подкрадывание");
                }
            }
            else if (role.equals("рыцарь")) {
                if (config.getBoolean("players." + id + ".class.skills.berserk.opened") == false) {
                    config.set("players." + id + ".class.skills.berserk.opened", true);
                    player.sendMessage(ChatColor.GOLD + "Вы открыли способность: берсерк");
                }
            }
        }
        else if (event.getLevel() == 10) {
            FileConfiguration config = _plugin.getConfig();
            String role = config.getString("players." + event.getPlayer().getUniqueId() + ".class.name");
            UUID id = event.getPlayer().getUniqueId();
            Player player = event.getPlayer();
            if (role.equals("разведчик")) {
                if (config.getBoolean("players." + id + ".class.skills.invisibility.opened") == false) {
                    config.set("players." + id + ".class.skills.invisibility.opened", true);
                    player.sendMessage(ChatColor.GOLD + "Вы открыли способность: невидимость");
                }
            }
            else if (role.equals("рыцарь")) {
                if (config.getBoolean("players." + id + ".class.skills.lifesteal.opened") == false) {
                    config.set("players." + id + ".class.skills.lifesteal.opened", true);
                    player.sendMessage(ChatColor.GOLD + "Вы открыли способность: вампиризм");
                }
            }
        }
        else if (event.getLevel() == 15) {
            FileConfiguration config = _plugin.getConfig();
            String role = config.getString("players." + event.getPlayer().getUniqueId() + ".class.name");
            UUID id = event.getPlayer().getUniqueId();
            Player player = event.getPlayer();
            if (role.equals("разведчик")) {
                if (config.getBoolean("players." + id + ".class.skills.rat.opened") == false) {
                    config.set("players." + id + ".class.skills.rat.opened", true);
                    player.sendMessage(ChatColor.GOLD + "Вы открыли способность: кража");
                }
            }
        }
        _plugin.saveConfig();
    }
}
