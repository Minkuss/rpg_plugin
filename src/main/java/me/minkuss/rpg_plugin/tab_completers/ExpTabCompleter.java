package me.minkuss.rpg_plugin.tab_completers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class ExpTabCompleter implements TabCompleter {

    public ExpTabCompleter() {}

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String str, String[] args) {

        if(args.length == 1) {
            return List.of("show");
        }

        return null;
    }
}
