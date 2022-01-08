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
        WorldCreator worldCreator = new WorldCreator("game");
        World world = _plugin.getServer().createWorld(worldCreator);
        event.setCancelled(true);
        player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Generating world...");
        player.teleport(world.getSpawnLocation());
        player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "You teleported");
    }
}
