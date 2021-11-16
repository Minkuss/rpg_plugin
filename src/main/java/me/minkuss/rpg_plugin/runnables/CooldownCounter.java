package me.minkuss.rpg_plugin.runnables;

import me.minkuss.rpg_plugin.Rpg_plugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CooldownCounter extends BukkitRunnable {

    private final Rpg_plugin _plugin;
    private final Player _player;

    private final String _skill;

    public CooldownCounter(Player player, Rpg_plugin plugin, String skill) {
        _player = player;
        _plugin = plugin;
        _skill = skill;

        Long cd =plugin.getConfig().getLong("players." + _player.getUniqueId() + ".class." + _skill + ".kd");
        plugin.getConfig().set("players." + _player.getUniqueId() + ".class." + _skill + "time-left", cd);
    }

    @Override
    public void run() {
        FileConfiguration config = _plugin.getConfig();
        Long left_time = config.getLong("players." + _player.getUniqueId() + ".class." + _skill + ".time-left");

        if(left_time > 0) {
            left_time--;
            config.set("players." + _player.getUniqueId() + ".class." + _skill + ".time-left", left_time);
        }
        else {
            cancel();
        }
    }

}
