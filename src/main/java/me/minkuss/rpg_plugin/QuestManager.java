package me.minkuss.rpg_plugin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import java.util.List;
import java.util.UUID;

public class QuestManager {

    private final Rpg_plugin _plugin;

    public QuestManager(Rpg_plugin plugin) {
        _plugin = plugin;
    }

    public boolean setQuest(UUID id) {

        FileConfiguration config = _plugin.getConfig();

        if(!config.contains("players." + id + ".quest.objective")) {
            List<String> params = List.of("kill", EntityType.ZOMBIE.toString());

            config.set("players." + id + ".quest.objective", params);
            config.set("players." + id + ".quest.goal", 3);
            config.set("players." + id + ".quest.progress", 0);
            _plugin.saveConfig();

            return true;
        }

        return false;
    }

}
