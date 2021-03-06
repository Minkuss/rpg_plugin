package me.minkuss.rpg_plugin;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.UUID;

public class RoleManager {

    private final Rpg_plugin _plugin;
    private final FileConfiguration _config;

    public RoleManager(Rpg_plugin plugin) {
        _plugin = plugin;
        _config = plugin.getConfig();
    }

    public boolean hasRole(String player_name, String role_name) {
        if(!_plugin.getConfig().contains("players." + player_name + ".class.name"))
            return false;
        else
            return _plugin.getConfig().getString("players." + player_name + ".class.name").equals(role_name);
    }

    public void setScout(String player_name) {
        String class_path = "players." + player_name + ".class";
        int level = _config.getInt("players." + player_name + ".level");

        _config.set(class_path, null);
        _config.set(class_path + ".name", "разведчик");

        _config.set(class_path + ".skills.sneaky-crouch.opened", level >= 5);

        _config.set(class_path + ".skills.invisibility.opened", level >= 10);
        _config.set(class_path + ".skills.invisibility.cd", 60);
        _config.set(class_path + ".skills.invisibility.time-left", 0);

        _config.set(class_path + ".skills.rat.opened", level >= 15);
        _config.set(class_path + ".skills.rat.cd", 60);
        _config.set(class_path + ".skills.rat.time-left", 0);

        _plugin.saveConfig();
    }

    public void setKnight(String player_name) {
        String class_path = "players." + player_name + ".class";
        int level = _config.getInt("players." + player_name + ".level");

        _config.set(class_path, null);
        _config.set(class_path + ".name", "рыцарь");

        _config.set(class_path + ".skills.lifesteal.opened", level >= 5);

        _config.set(class_path + ".skills.berserk.opened", level >= 10);

        _config.set(class_path + ".skills.kto-prochital-tot-loh.opened", level >= 15);

        _plugin.saveConfig();
    }


}
