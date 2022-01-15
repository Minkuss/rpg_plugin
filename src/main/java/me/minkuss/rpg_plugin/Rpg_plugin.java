package me.minkuss.rpg_plugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import me.minkuss.rpg_plugin.listeners.*;
import me.minkuss.rpg_plugin.listeners.clans.AsyncPlayerChatEventListener;
import me.minkuss.rpg_plugin.listeners.clans.InviteEventListener;
import me.minkuss.rpg_plugin.listeners.portals.PortalEventListener;
import me.minkuss.rpg_plugin.listeners.quests.ClanQuestCompleteListener;
import me.minkuss.rpg_plugin.listeners.quests.EntityDeathListener;
import me.minkuss.rpg_plugin.listeners.quests.PlayerInteractEntityListener;
import me.minkuss.rpg_plugin.listeners.quests.QuestCompleteListener;
import me.minkuss.rpg_plugin.tab_completers.*;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Rpg_plugin extends JavaPlugin {

    private final PluginManager _plugin_manager = getServer().getPluginManager();
    private final Server _server = getServer();
    public WorldGuardPlugin worldGuardPlugin;

    @Override
    public void onEnable() {
        worldGuardPlugin = getWorldGuard();
        RegisterEvents();
        SetCommands();
    }

    private WorldGuardPlugin getWorldGuard() {
        Plugin plugin = this.getServer().getPluginManager().getPlugin("WorldGuard");
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }
        return (WorldGuardPlugin) plugin;
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    private void RegisterEvents() {
        _plugin_manager.registerEvents(new PlayerInteractEntityListener(this), this);
        _plugin_manager.registerEvents(new PlayerBrakeBlockListener(this), this);
        _plugin_manager.registerEvents(new QuestCompleteListener(this), this);
        _plugin_manager.registerEvents(new KnightEventListener(this), this);
        _plugin_manager.registerEvents(new PlayersKillListener(this), this);
        _plugin_manager.registerEvents(new EntityDeathListener(this), this);
        _plugin_manager.registerEvents(new JoinPlayerListener(this), this);
        _plugin_manager.registerEvents(new ScoutEventListener(this), this);
        _plugin_manager.registerEvents(new GainedExpListener(this), this);
        _plugin_manager.registerEvents(new LevelUpListener(this), this);
        _plugin_manager.registerEvents(new InviteEventListener(this), this);
        _plugin_manager.registerEvents(new AsyncPlayerChatEventListener(this), this);
        _plugin_manager.registerEvents(new PortalEventListener(this), this);
        _plugin_manager.registerEvents(new ClanQuestCompleteListener(this), this);
    }

    private void SetCommands() {
        String[] commands_names = {
            "role", "quest",
            "exp", "level",
            "clan", "req",
        };

        for(String command_name : commands_names) {
            _server.getPluginCommand(command_name).setExecutor(new CommandsManager(this));
        }

        _server.getPluginCommand("role").setTabCompleter(new RoleTabCompleter(this));
        _server.getPluginCommand("quest").setTabCompleter(new QuestTabCompleter());
        _server.getPluginCommand("exp").setTabCompleter(new ExpTabCompleter());
        _server.getPluginCommand("level").setTabCompleter(new LevelTabCompleter());
        _server.getPluginCommand("clan").setTabCompleter(new ClanTabCompleter());
    }

}
