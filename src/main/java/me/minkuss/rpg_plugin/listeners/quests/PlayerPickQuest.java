package me.minkuss.rpg_plugin.listeners.quests;

import me.minkuss.rpg_plugin.QuestManager;
import me.minkuss.rpg_plugin.Rpg_plugin;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
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

            if(new QuestManager(_plugin).trySetQuest(player.getUniqueId()) && villager.getProfession() == Villager.Profession.NONE) {
                villager.playEffect(EntityEffect.VILLAGER_HAPPY);
                villager.setCustomName(ChatColor.GOLD + "БИГ БОБ");
                villager.setCustomNameVisible(true);

                player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Полученно задание");
            }
            else {
                player.sendMessage(ChatColor.YELLOW + "[Warning] " + ChatColor.GOLD + "У тебя уже есть задание");
                villager.playEffect(EntityEffect.VILLAGER_ANGRY);
            }

            event.setCancelled(true);
        }
    }

}
