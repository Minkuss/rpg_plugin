package me.minkuss.rpg_plugin.runnables;

import me.minkuss.rpg_plugin.Rpg_plugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CooldownCounter extends BukkitRunnable {
    private final FileConfiguration _config;

    private final Rpg_plugin _plugin;
    private final String _player_name;

    private final String _skill;

    public CooldownCounter(String player_name, Rpg_plugin plugin, String skill) {
        _player_name = player_name;
        _plugin = plugin;
        _skill = skill;
        _config = plugin.getConfig();

        long time_left = _config.getLong("players." + _player_name + ".class.skills." + _skill + ".time-left");
        long cd;

        if(time_left > 0)
            cd = time_left;
        else
            cd = plugin.getConfig().getLong("players." + _player_name + ".class.skills." + _skill + ".cd");

        _config.set("players." + _player_name + ".class.skills." + _skill + ".time-left", cd);
    }

    @Override
    public void run() {
        long time_left = _config.getLong("players." + _player_name + ".class.skills." + _skill + ".time-left");

        if(time_left > 0 && _plugin.getServer().getPlayer(_player_name) != null) {
            time_left--;
            _config.set("players." + _player_name + ".class.skills." + _skill + ".time-left", time_left);
            _plugin.saveConfig();
        }
        else {
            cancel();
        }
    }

}
