package me.minkuss.rpg_plugin.listeners;

import me.minkuss.rpg_plugin.QuestManager;
import me.minkuss.rpg_plugin.Rpg_plugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerPickQuest implements Listener {

    private final Rpg_plugin _plugin;

    public PlayerPickQuest(Rpg_plugin plugin) {
        _plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent event) {
        if(event.getRightClicked() instanceof Villager villager) {
            Player player = event.getPlayer();

            if(new QuestManager(_plugin).setQuest(player.getUniqueId())) {
                villager.setCustomName(ChatColor.GOLD + "БИГ БОБ");
                villager.setCustomNameVisible(true);

                player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Получен квест");
            }

            event.setCancelled(true);
        }
    }

}
