package me.minkuss.rpg_plugin.listeners;

import me.minkuss.rpg_plugin.Rpg_plugin;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class PlayerBrakeBlockListener implements Listener {
    private final Rpg_plugin _plugin;

    public PlayerBrakeBlockListener(Rpg_plugin plugin) {_plugin = plugin;}

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {

        Block block = event.getBlock();
        int exp = GetBlockExp(block);

        if(exp != 0 ) {
            FileConfiguration config = _plugin.getConfig();
            Player player = event.getPlayer();

            int level = config.getInt("players." + player.getUniqueId() + ".level");
            int start_value = config.getInt("exp-info.start-value");
            int level_scale = config.getInt("exp-info.level-scale");
            int newLevelBarrier = start_value * (level_scale * level);

            exp += config.getInt("players." + player.getUniqueId() + ".exp");

            if(newLevelBarrier <= exp) {
                exp -= newLevelBarrier;
                
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 1, 1);
                level++;
                player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "New level " + level);
            }

            newLevelBarrier = start_value * (level_scale * level);
            player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Your experience: " + exp + "/" + newLevelBarrier);

            config.set("players." + player.getUniqueId() + ".level", level);
            config.set("players." + player.getUniqueId() + ".exp", exp);
            _plugin.saveConfig();
        }
    }

    private int GetBlockExp(Block block) {
        FileConfiguration config = _plugin.getConfig();
        switch(block.getType()) {
            case DIAMOND_ORE:
                return config.getInt("exp-info.ores.diamond");
            case IRON_ORE:
            case GOLD_ORE:
            case COPPER_ORE:
                return config.getInt("exp-info.ores.others");
            case COAL_ORE:
                return config.getInt("exp-info.ores.coal");
            default:
                return 0;
        }
    }

}
