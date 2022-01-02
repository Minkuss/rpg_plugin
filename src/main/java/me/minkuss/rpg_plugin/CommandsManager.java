package me.minkuss.rpg_plugin;

import me.minkuss.rpg_plugin.commands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandsManager implements CommandExecutor {
    private final AbstractCommand[] commands;
    private final Rpg_plugin _plugin;

    public CommandsManager(Rpg_plugin plugin) {
        commands = new AbstractCommand[] {
                new QuestCommand("quest"),
                new LevelCommand("level"),
                new ExpCommand("exp"),
                new RoleCommand("role"),
                new ClanCommand("clan"),
                new ReqCommand("req"),
        };

        _plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        for(AbstractCommand command : commands) {
            if(str.equals(command.getName())) {
                command.execute(_plugin, sender, args);
                return true;
            }
        }

        return false;
    }
}
