package me.minkuss.rpg_plugin.listeners;

import me.minkuss.rpg_plugin.Rpg_plugin;
import me.minkuss.rpg_plugin.events.GainedExpEvent;
import org.bukkit.Material;
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
        int exp = GetBlockExp(event.getBlock().getType());
        _plugin.getServer().getPluginManager().callEvent(new GainedExpEvent(event.getPlayer(), exp));
    }

    private int GetBlockExp(Material block) {
        FileConfiguration config = _plugin.getConfig();
        return switch (block) {
            case DIAMOND_ORE -> config.getInt("exp-info.ores.diamond");
            case IRON_ORE, GOLD_ORE, COPPER_ORE -> config.getInt("exp-info.ores.others");
            case COAL_ORE -> config.getInt("exp-info.ores.coal");
            default -> 0;
        };
    }

}
