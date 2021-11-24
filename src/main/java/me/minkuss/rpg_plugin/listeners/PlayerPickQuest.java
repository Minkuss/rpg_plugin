package me.minkuss.rpg_plugin.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerPickQuest implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent event) {
        if(event.getRightClicked() instanceof Villager villager) {

            if(!villager.getCustomName().equals(ChatColor.GOLD + "?")) {
                event.getPlayer().sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Получен квест");
                villager.setCustomName(ChatColor.GOLD + "?");
                villager.setCustomNameVisible(true);
            }

            event.setCancelled(true);
        }
    }

}
