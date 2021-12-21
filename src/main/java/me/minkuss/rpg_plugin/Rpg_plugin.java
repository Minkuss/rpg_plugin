package me.minkuss.rpg_plugin;

import me.minkuss.rpg_plugin.commands.*;
import me.minkuss.rpg_plugin.listeners.*;
import me.minkuss.rpg_plugin.listeners.quests.EntityDeathListener;
import me.minkuss.rpg_plugin.listeners.quests.PlayerInteractEntityListener;
import me.minkuss.rpg_plugin.listeners.quests.QuestCompleteListener;
import me.minkuss.rpg_plugin.tab_completers.GiveRoleTabCompleter;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Rpg_plugin extends JavaPlugin {

    private final PluginManager _plugin_manager = getServer().getPluginManager();
    private final Server _server = getServer();

    @Override
    public void onEnable() {
        RegisterEvents();
        SetCommands();
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    private void RegisterEvents() {
        _plugin_manager.registerEvents(new PlayersKillListener(this), this);
        _plugin_manager.registerEvents(new JoinPlayerListener(this), this);
        _plugin_manager.registerEvents(new PlayerBrakeBlockListener(this), this);
        _plugin_manager.registerEvents(new ScoutEventListener(this), this);
        _plugin_manager.registerEvents(new KnightEventListener(this), this);
        _plugin_manager.registerEvents(new GainedExpListener(this), this);
        _plugin_manager.registerEvents(new LevelUpListener(this), this);
        _plugin_manager.registerEvents(new PlayerInteractEntityListener(this), this);

        _plugin_manager.registerEvents(new EntityDeathListener(this), this);
        _plugin_manager.registerEvents(new QuestCompleteListener(this), this);
    }

    private void SetCommands() {
        _server.getPluginCommand("setrole").setExecutor(new SetRoleCommand(this));

        _server.getPluginCommand("giverole").setExecutor(new GiveRoleCommand(this));
        _server.getPluginCommand("giverole").setTabCompleter(new GiveRoleTabCompleter(this));

        _server.getPluginCommand("showexp").setExecutor(new ShowExpCommand(this));
        _server.getPluginCommand("showlevel").setExecutor(new ShowLevelCommand(this));
        _server.getPluginCommand("showrole").setExecutor(new ShowRoleCommand(this));
        _server.getPluginCommand("showquest").setExecutor(new ShowQuestCommand(this));

        _server.getPluginCommand("cancelquest").setExecutor(new CancelQuestCommand(this));
    }

}
