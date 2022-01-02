package me.minkuss.rpg_plugin.commands;

import me.minkuss.rpg_plugin.Rpg_plugin;
import org.bukkit.command.CommandSender;

public abstract class AbstractCommand {
    private final String _name;

    public AbstractCommand(String name) {_name = name;}

    public String getName() {return _name;}
    abstract public void execute(Rpg_plugin plugin, CommandSender sender, String[] args);
}
