package me.minkuss.rpg_plugin.tab_completers;

import me.minkuss.rpg_plugin.Rpg_plugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class GiveRoleTabCompleter implements TabCompleter {

    private final Rpg_plugin _plugin;

    public GiveRoleTabCompleter(Rpg_plugin plugin) {
        _plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if(args.length == 1) {
            return _plugin.getConfig().getStringList("role-names");
        }

        return null;
    }
}
