package me.minkuss.rpg_plugin;

import jdk.vm.ci.code.Register;
import me.minkuss.rpg_plugin.commands.GiveClassCommand;
import me.minkuss.rpg_plugin.commands.SetClassCommand;
import me.minkuss.rpg_plugin.commands.ShowExpCommand;
import me.minkuss.rpg_plugin.commands.ShowLevelCommand;
import me.minkuss.rpg_plugin.listeners.JoinPlayerListener;
import me.minkuss.rpg_plugin.listeners.PlayerBrakeBlockListener;
import me.minkuss.rpg_plugin.listeners.PlayersKillListener;
import me.minkuss.rpg_plugin.listeners.ScoutEventListener;
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
    }

    private void SetCommands() {
        _server.getPluginCommand("setrole").setExecutor(new SetClassCommand(this));
        _server.getPluginCommand("giverole").setExecutor(new GiveClassCommand(this));
        _server.getPluginCommand("showexp").setExecutor(new ShowExpCommand(this));
        _server.getPluginCommand("showlevel").setExecutor(new ShowLevelCommand(this));
    }

}
