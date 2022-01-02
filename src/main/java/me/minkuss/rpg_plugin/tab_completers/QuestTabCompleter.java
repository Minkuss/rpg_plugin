package me.minkuss.rpg_plugin.tab_completers;

import me.minkuss.rpg_plugin.Rpg_plugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class QuestTabCompleter implements TabCompleter {

    public QuestTabCompleter() {}

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String str, String[] args) {

        if(args.length == 1) {
            return List.of("cancel", "show");
        }

        return null;
    }

}
