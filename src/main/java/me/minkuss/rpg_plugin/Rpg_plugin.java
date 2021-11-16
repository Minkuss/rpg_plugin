package me.minkuss.rpg_plugin;

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

    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        Server srv = getServer();
        saveConfig();
        srv.getPluginCommand("setrole").setExecutor(new SetClassCommand(this));
        srv.getPluginCommand("giverole").setExecutor(new GiveClassCommand(this));
        pm.registerEvents(new PlayersKillListener(this), this);
        pm.registerEvents(new JoinPlayerListener(this), this);
        pm.registerEvents(new PlayerBrakeBlockListener(this), this);
        pm.registerEvents(new ScoutEventListener(this), this);

        srv.getPluginCommand("showexp").setExecutor(new ShowExpCommand(this));
        srv.getPluginCommand("showlevel").setExecutor(new ShowLevelCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
