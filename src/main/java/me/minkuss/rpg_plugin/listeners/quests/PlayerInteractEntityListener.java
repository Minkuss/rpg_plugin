package me.minkuss.rpg_plugin.listeners.quests;

import me.minkuss.rpg_plugin.QuestManager;
import me.minkuss.rpg_plugin.Rpg_plugin;
import me.minkuss.rpg_plugin.events.GainedExpEvent;
import me.minkuss.rpg_plugin.events.quests.QuestCompleteEvent;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;
import java.util.List;

public class PlayerInteractEntityListener implements Listener {

    private final Rpg_plugin _plugin;

    public PlayerInteractEntityListener(Rpg_plugin plugin) {
        _plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent event) {
        if(event.getRightClicked() instanceof Villager villager) {
            Player player = event.getPlayer();
            FileConfiguration config = _plugin.getConfig();

            boolean hasBringQuest = false;
            List<String> params = null;

            if(config.contains("players." + player.getName() + ".quest"))
                params = config.getStringList("players." + player.getName() + ".quest.objective");

            if(params != null)
                hasBringQuest = params.get(0).equals("bring");

            if(hasBringQuest) {
                int goal = config.getInt("players." + player.getName() + ".quest.goal");
                PlayerInventory player_inventory = player.getInventory();

                if(player_inventory.getItemInMainHand().getType().toString().equals(params.get(1))) {
                    ItemStack item = player_inventory.getItemInMainHand();
                    player_inventory.removeItem(item);
                    item.setAmount(item.getAmount() - goal);
                    player_inventory.setItemInMainHand(item);
                    _plugin.getServer().getPluginManager().callEvent(new QuestCompleteEvent(player, 5 * goal));
                }

            }
            else if(new QuestManager(_plugin).trySetQuest(player.getName()) && villager.getProfession() == Villager.Profession.NONE) {
                    villager.playEffect(EntityEffect.VILLAGER_HAPPY);
            }
            else {
                    villager.playEffect(EntityEffect.VILLAGER_ANGRY);
            }


            event.setCancelled(true);
        }
    }

}
