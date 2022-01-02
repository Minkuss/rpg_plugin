package me.minkuss.rpg_plugin.listeners;

import me.minkuss.rpg_plugin.RoleManager;
import me.minkuss.rpg_plugin.Rpg_plugin;
import me.minkuss.rpg_plugin.events.LevelUpEvent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class LevelUpListener implements Listener {
    private final Rpg_plugin _plugin;

    public LevelUpListener(Rpg_plugin plugin) {
        _plugin = plugin;
    }

    @EventHandler
    public void onPlayerLevelUp(LevelUpEvent event) {
        RoleManager roleManager = new RoleManager(_plugin);
        UUID player_id = event.getPlayer().getUniqueId();
        FileConfiguration config = _plugin.getConfig();
        String role = config.getString("players." + player_id + ".class.name");

        if(role != null) {
            UUID id = event.getPlayer().getUniqueId();
            Player player = event.getPlayer();

            if (event.getLevel() == 5) {
                if (role.equals("разведчик")) {
                    config.set("players." + id + ".class.skills.sneaky-crouch.opened", true);
                    player.sendMessage(ChatColor.GOLD + "Вы открыли способность: скрытное подкрадывание");
                }
                else if (role.equals("рыцарь")) {
                    config.set("players." + id + ".class.skills.berserk.opened", true);
                    player.sendMessage(ChatColor.GOLD + "Вы открыли способность: берсерк");
                }
            }
            else if (event.getLevel() == 10) {
                if (role.equals("разведчик")) {
                    config.set("players." + id + ".class.skills.invisibility.opened", true);
                    player.sendMessage(ChatColor.GOLD + "Вы открыли способность: невидимость");
                }
                else if (role.equals("рыцарь")) {
                    config.set("players." + id + ".class.skills.lifesteal.opened", true);
                    player.sendMessage(ChatColor.GOLD + "Вы открыли способность: вампиризм");
                }
            }
            else if (event.getLevel() == 15) {
                if (role.equals("разведчик")) {
                    config.set("players." + id + ".class.skills.rat.opened", true);
                    player.sendMessage(ChatColor.GOLD + "Вы открыли способность: кража");
                }
            }

            _plugin.saveConfig();
        }
    }
}
