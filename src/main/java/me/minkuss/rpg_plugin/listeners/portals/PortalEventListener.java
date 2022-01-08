package me.minkuss.rpg_plugin.listeners.portals;

import me.minkuss.rpg_plugin.Rpg_plugin;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

public class PortalEventListener implements Listener {
    private final Rpg_plugin _plugin;

    public PortalEventListener(Rpg_plugin plugin) {_plugin = plugin;}

    @EventHandler
    public void OnPlayerPortal(PlayerPortalEvent event) {
        Player player = event.getPlayer();
        WorldCreator world_creator;
        World world;

        if(_plugin.getServer().getWorlds().get(0) == player.getWorld()) {
            world_creator = new WorldCreator("game");
            world = _plugin.getServer().createWorld(world_creator);

            event.setCancelled(true);

            player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Генерация мира...");
            player.teleport(world.getSpawnLocation());
            player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Вы телепортированы");
        }

    }
}
