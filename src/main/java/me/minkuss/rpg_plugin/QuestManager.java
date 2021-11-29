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

    public boolean trySetQuest(UUID id) {
        FileConfiguration config = _plugin.getConfig();

        if(!config.contains("players." + id + ".quest")) {
            List<String> params = List.of("kill", EntityType.ZOMBIE.toString());

            config.set("players." + id + ".quest.objective", params);
            config.set("players." + id + ".quest.goal", (int)(Math.floor(Math.random() * 10) + 5));
            config.set("players." + id + ".quest.progress", 0);
            _plugin.saveConfig();

            return true;
        }

        return false;
    }

}
